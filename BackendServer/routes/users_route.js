var express = require("express");
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
            resp.json({success:true, user:user});
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

    Users.updateFields(id, "", updates, function (err) {
        if(err) resp.json({success:false});
        else{
            resp.json({success:true});
        }
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
