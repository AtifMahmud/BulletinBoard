var mongoose = require("mongoose");
var bcrypt = require("bcrypt");
var nodemailer = require("nodemailer");
var email_config = require("../config/emailconfig");


const TESTING = true;
const SEND_EMAIL = true;

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
    registration:{
        type:Date,
        default: Date.now()
    },
    posts:{
        type:Array,
        default:[]
    },
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

const WANTED_FIELDS = "_id first_name last_name email registration posts rating";

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
         //   return console.log(error); // for debugging
        }
    });
};

/**
 * Registers a new user
 * @param user - user object with all registration info
 * @param cb - Callback function
 */
module.exports.registerUser = function (user, cb) {

    var salt = bcrypt.genSaltSync(10);
    var hash = bcrypt.hashSync(user.password, salt);
    user.hash = hash;
    Users.create(user, function (err, user) {

        cb(err, user);

    });
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
            cb(err, false);
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
        cb
    );

};
module.exports.findUsersByName = function (name, cb) {

    Users.find(
        {first_name:name},
        WANTED_FIELDS,
        cb);

};

module.exports.clearAll =  function (cb){

    Users.remove({},cb);
};

