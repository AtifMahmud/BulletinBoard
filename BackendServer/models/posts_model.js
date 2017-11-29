var mongoose = require("mongoose");
var Users = require("../models/users_model");


const TESTING = true;

// Post schema 
// Schema is the structure of the data
// This is the post schema in json format (?)

var schema = mongoose.Schema({

    user_id:{
        required:true,
        type:String
    },
    user_first_name:{
        required:true,
        type:String
    },
    user_last_name:{
        required: true,
        type: String
    },
    email:{
        required:true,
        type: String
    },
    phone:{
        type:String,
        default:""
    },
    title:{
        required:true,
        type:String
    },
    description:{
        required:true,
        type:String
    },
    showEmail:{
        type:Boolean,
        default:false       // default value for the showEmail field
    },
    showPhone:{
        type:Boolean,
        default:false
    },
    date:{
        type:Date,
        default:Date.now()  // today's date
    }
});

module.exports = mongoose.model("posts", schema);

const Posts = module.exports;


module.exports.addPosting = function (post, cb) {

    Users.getUserById(post.user_id, function (err, user) {
        post.user_first_name = user.first_name;
        post.user_last_name  = user.last_name;
        post.email = user.email;
        post.phone = user.phone;
        Posts.create(post, cb);
    });

};

module.exports.getAllPosts = function (cb) {

    Posts.find({}, cb);

};

module.exports.getUserPosts = function (uid, cb) {
    Posts.find({user_id:uid}, cb);
};

module.exports.updateFields = function (id, token, updates, cb){

    Posts.updateOne(
        {_id:id},
        { $set: updates },
        cb
    );

};

/**
 * Gets a specific post
 * @param id - post's ID
 * @param cb - callback function
 */
module.exports.findById = function (id, cb) {

    Posts.findOne(
        {_id:id},
        cb
    );
};

/**
 * Clears all posts's from the database (NOT FOR PRODUCTION)
 * @param cb
 */
module.exports.clearAll = function (cb) {

    Posts.remove({}, cb);
};
