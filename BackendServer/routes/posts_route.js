var express = require('express');
var router = express.Router();
var Posts = require('../models/posts_model');

// Create a new post
router.post('/', function (req, resp) {

    var post = req.body;

    Posts.addPosting(post, function (err, post) {
        if(err) {
            resp.json({success:false});
        }else
            resp.json(
                {success:true, post_id:post._id}
            );
    });
});

// Get a specific post by id
router.get('/id=:id', function (req, resp) {

    var id = req.params.id;

    Posts.findById(id, function (err, post) {
        if(err){
            resp.json({success:false});
        }else{
            resp.json({success:true, post:post});
        }

    })


});

router.post('/update/id=:id', function (req, resp) {

    var id = req.params.id;

    var updates = req.body;

    Posts.updateFields(id, updates, function (err) {
        if(err) resp.json({success:false});
        else{
            resp.json({success:true});
        }
    })

});


// Get a list of all posts
router.get('/all/', function (req, resp) {
    Posts.getAllPosts(function (err, posts) {
        if(err) {
            resp.json({success:false});
        }
        else{

            resp.json({
                success: true,
                posts: posts
            });
        }
    });
});



// Clear all posts (For development, not production)
router.get('/clear', function (req, resp) {

    Posts.clearAll(function () {

        resp.end("cleared all posts");
    });
});

module.exports = router;