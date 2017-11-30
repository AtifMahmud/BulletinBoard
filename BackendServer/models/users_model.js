var mongoose = require("mongoose");
var bcrypt = require("bcrypt");
var nodemailer = require("nodemailer");
var email_config = require("../config/emailconfig");
var Posts = require("../models/posts_model");

const TESTING = true;
const SEND_EMAIL = false;

var schema = mongoose.Schema({

    first_name:{
        required:true,
        type:String
    },
    last_name:{
        required:true,
        type:String
    },
    email:{
        required:true,
        type:String
    },
    phone:{
        type:String,
        default:"No phone number provided."
    },
    hash:{
        required:true,
        type:String
    },
    token:{
        type:String,
        default:"uninitialized"
    },
    token_date:{
        type:Date,
        default:Date.now()
    },
    registration:{
        type:Date,
        default: Date.now()
    },
    favourites:{
        type:Object,
        default:{}
    },
    // posts:{
    //     type:Array,
    //     default:[]
    // },
    rating:{
        type:Number,
        default:0
    },
    num_ratings:{
        type:Number,
        default:0
    }
});

module.exports = mongoose.model("users", schema);
const Users = module.exports;

const WANTED_FIELDS = "_id first_name last_name email phone registration rating num_ratings favourites";

/**
 * Sends a registration email to the email provided during registration
 * @param user - User object containing all registration info
 */
module.exports.sendRegistrationEmail = function (user) {
    if(!SEND_EMAIL){
        return;
    } 

    const mailOptions = {
        from: "BulletinBoard Team <mailer@bulletinboardteam321.com>", // sender address
        to:  user.email,
        subject: "Thanks for joining BulletinBoard!",
        text: "Hey, " + user.first_name + ","
        + "\n\nThank you for joining BulletinBoard!"
        + "\n\nConfirm your email here: TODO"
        + "\n"
        + "\n"
        + "\n - BulletinBoard Team"
        + "\n\n\n"
    };
    const transporter = nodemailer.createTransport({
        service: "gmail",
        auth: {
            user: email_config.email_account.email,
            pass: email_config.email_account.pass
        }
    });

    // send mail with defined transport object
    transporter.sendMail(mailOptions, function(error, info){
        if (error) {
            console.log(error);
        }
    });
};

/**
 * Registers a new user
 * @param user - user object with all registration info
 * @param cb - Callback function
 */
module.exports.registerUser = function (user, cb) {

    if (!user.password) {
        cb("No password provided", null);
    }else {
        Users.findOne({email:user.email}, function (err, res) {
            if (err || !res) {
                var salt = bcrypt.genSaltSync(10);
                var hash = bcrypt.hashSync(user.password, salt);
                user.hash = hash;
                Users.create(user, function (err, user) {
                    user.favourites = [];
                    cb(err, user);

                });
            } else {
                cb("Email taken", null);
            }
        })
    }
};

/**
 * Checks authentication for ID and password combination
 * @param id - user ID
 * @param pass - plain text password
 * @param cb - callback function
 */
module.exports.authenticateByID = function (id, pass, cb) {

    Users.findOne({_id:id}, function (err, user) {
        if(err || !user) {
            cb(err, false);
        }else{
            // Use bcrypt library to compare to salted/hashed password
            bcrypt.compare(pass, user.hash, cb);
        }
    });

};

/**
 * Checks authentication for ID and password combination
 * @param email - user's email
 * @param pass - plain text password
 * @param cb - callback function
 */
module.exports.authenticateByEmail = function (email, pass, cb) {

    Users.findOne({email:email}, function (err, user) {
        if(err  || !user) {
            cb(err, false, -1);
        }else {
            bcrypt.compare(pass, user.hash, function (err, status) {
                cb(err, status, user._id);
            });
        }
    });

};

/**
 * Updates a user's info
 * @param id - User's ID (this does not change)
 * @param token - Provided token
 * @param updates - updates
 * @param cb - callback function
 */
module.exports.updateFields = function (id, token, updates, cb){
    if (updates.token || updates.token_date || updates.hash || updates.registration) cb("Nice try");
    else
        Users.updateOne(
            TESTING ? {_id:id} : {_id:id, token:token},
            { $set: updates },
            cb
        );

};

// Get ALL users
module.exports.getUsers = function (cb, limit) {
    Users.find(
        {},
        WANTED_FIELDS,
        cb
    ).limit(limit);
};

module.exports.getUserById = function (id, cb) {

    Users.findById(
        id,
        WANTED_FIELDS,
        function (err, user) {
            if (err || !user) cb("User not found", null);
            else cb(null, user);
        }
    );

};
module.exports.findUsersByName = function (name, cb) {

    Users.find(
        {first_name:name},
        WANTED_FIELDS,
        cb);

};

module.exports.favouritePost = function(user_id, post_id, cb) {
    Users.getUserById(user_id, function (err, user) {
        if (err || !user) {
            cb("User not found");
        } else {
            console.log(user);
            var favs = user.favourites;
            console.log(favs);
            favs[post_id] = true;
            Users.updateFields(user_id, "", {favourites:favs}, cb);
        }
    });
};

module.exports.getUserFavourites = function (uid, cb) {

    Users.getUserById(uid, function (err, user) {

        if (err || !user) cb("User not found.", null);
        else {
            var fav_ids = Object.keys(user.favourites);
            Posts.find({
                _id : { $in: fav_ids}
            }, cb);
        }
    });
};

module.exports.addRating = function (id, token, rating, cb) {

    rating = Math.floor(rating);
    if (rating < 0 || rating > 5) cb("Invalid rating.", -1);
    else
        module.exports.getUserById(id, function (err, user) {
            if (err || !user) cb("User not found", -1);
            else {
                var old_rating  = user.rating;
                var num_ratings = user.num_ratings;

                var new_rating = (old_rating*num_ratings + rating)/(num_ratings+1);
                Users.updateFields(
                    id,
                    token,
                    {rating:new_rating, num_ratings:num_ratings+1},
                    function (err) {
                        if (err) cb(err, -1);
                        else     cb(err, new_rating);
                    });
            }
        });
};

module.exports.addPosting = function (post, cb) {

    Users.getUserById(post.user_id, function (err, user) {
        if (err) cb(err, null);
        else {
            post.user_first_name = user.first_name;
            post.user_last_name = user.last_name;
            post.email = user.email;
            post.phone = user.phone;
            Posts.create(post, cb);
        }
    });

};

module.exports.clearAll =  function (cb){

    Users.remove({},cb);
};

