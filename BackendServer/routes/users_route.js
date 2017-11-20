var express = require("express");
var crypto = require("crypto");
var router = express.Router();
var Users = require("../models/users_model");

router.get("/all/", function (req, resp) {
    Users.getUsers(function (err, users) {
        if(err) {
            resp.writeHead(404, {"Content-Type": "application/json"});
            throw err;
        }
        else{
            resp.json({success:true, users:users});
            resp.end();
        }
    });
});

router.post("/", function (req, resp) {

    var user = req.body;
    Users.registerUser(user, function (err, user) {
        if(err) resp.json({success:false, error:err});
        else {
            var token = "uninitialized";
            crypto.randomBytes(48, function(err, buffer) {
                token = buffer.toString('hex');
                Users.update(
                    { _id:user._id },
                    {
                        $set: { "token":token, "token_date":Date.now()}
                    },
                    function () {}
                );
                resp.json({
                    success: true,
                    _id: user._id,
                    token: token
                });
            });
            Users.sendRegistrationEmail(user);
        }
    });

});

router.get("/id=:_id", function (req, resp) {

    var id = req.params._id;

    Users.getUserById(id, function (err, user) {
        if(err){
            resp.json({success:false, error:err});
        }
        else resp.json({success:true, user:user});
    });

});

router.get("/name=:name", function (req, resp) {

    var name = req.params.name;

    Users.findUsersByName(name, function (err, user) {

        if(err) throw err;

        resp.json(user);
    });

});

router.post("/update/id=:id", function (req, resp) {

    var id = req.params.id;

    var updates = req.body;

    Users.updateFields(id, "token", updates, function (err) {
        if(err) resp.json({success:false});
        else{
            resp.json({success:true});
        }
    });

});

router.get("/add_rating/id=:id&rating=:rating", function (req, resp) {
    var id = req.params.id;
    var rating = req.params.rating;

    Users.addRating(id, "token" , rating, function (err, new_rating) {
        if (err) resp.json({success:false, err:err});
        else     resp.json({success:true , new_rating:new_rating});
    })
});


// Check authentication by ID/password combination
router.post('/authenticate/id', function (req, resp) {

    var user = req.body;
    var _id = user.id;
    var pass = user.password;


    Users.authenticateByID(_id, pass, function (err, status) {

        if(!err && status === true){
            // Generate a token and include it in the response
            var token = "uninitialized";
            crypto.randomBytes(48, function(err, buffer) {
                token = buffer.toString('hex');
                Users.update(
                    { _id:_id },
                    {
                        $set: { "token":token, "token_date":Date.now()}
                    },
                    function () {}
                );
                resp.json({success:true, token:token});
            });
        }
        else
            resp.json({success:false});

    });
});

// Check authentication by email/password combination
router.post('/authenticate/email', function (req, resp) {

    var user = req.body;
    var email = user.email;
    var pass = user.password;


    Users.authenticateByEmail(email, pass, function (err, status, id) {

        if(!err && status === true){
            // Generate a and include it in the response
            var token = "uninitialized";
            crypto.randomBytes(48, function(err, buffer) {
                token = buffer.toString('hex');
                Users.update({ email:email}, {$set: { "token":token, "token_date":Date.now()}},
                    function () {}
                );
                resp.json({success:true, token:token, id:id});
            });
        }
        else
            resp.json({success:false})

    });
});



// FOR TESTING. REMOVE LATER
router.get("/clear", function (req, resp) {
    Users.clearAll(function (err) {
        if(err) throw err;
    });
    resp.json({status:"Cleared all users"});
});

module.exports = router;
