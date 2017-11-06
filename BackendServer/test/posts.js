
process.env.NODE_ENV = 'test';

let mongoose = require("mongoose");
let Posts = require('../models/posts_model');

let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../app');
let should = chai.should();

chai.use(chaiHttp);

let test_post_id = "59e2ec7950a9746df27e05f3";

describe('Posts Tests', function() {

    // Clear the test database
    before((done) => {
        Posts.clearAll( () => { done(); } );
    });

    describe('/GET posts', function() {

        it('should GET all posts', (done) => {
            chai.request(server)
                .get('/api/posts/all')
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.posts.should.be.a('array');
                    res.body.posts.length.should.be.eql(0);
                    done();
                    //console.log(res.body);
                });
        });
    });

    describe('/POST post', () => {
        it('it should NOT POST without user_id field', (done) => {
            let post = {
                title: "Oops, i forgot to add a user_id field.",
                description: "Should fail",
                showPhone: true,
            };
            chai.request(server)
                .post('/api/posts/')
                .send(post)
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.should.have.property('success');
                    res.body.success.should.eql(false);
                    done();
                    //console.log(res.body);
                });
        });

        it('it should POST with all field present', (done) => {
            let post = {
                _id: test_post_id,
                user_id: 12345,
                title: "I'll teach you piano",
                description: "I'll teach you piano for free an hour a week!!!!! srs",
                showPhone: true,
            };
            chai.request(server)
                .post('/api/posts/')
                .send(post)
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.should.have.property('success');
                    res.body.success.should.eql(true);
                    res.body.post_id.should.be.a('string');
                    done();
                    //console.log(res.body);
                });
        });

    });

    describe('/GET/:id post', () => {
        it('it should GET a post with the given id', (done) => {
            chai.request(server)
                .get('/api/posts/id=' + test_post_id)
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.success.should.eql(true);
                    res.body.post.should.have.property('title');
                    res.body.post.should.have.property('description');
                    res.body.post.should.have.property('user_id');
                    res.body.post.should.have.property('_id').eql(test_post_id);
                    done();
                });
        });
    });

    describe('/POST/update post', () => {
        let change_string = "I CHANGED THIS";
        it("it should SET an existing post's fields with the given id", (done) => {
            let updates = {description: change_string};
            chai.request(server)
                .post('/api/posts/update/id=' + test_post_id)
                .send(updates)
                .end((err, res) => {
                    res.body.success.should.eql(true);
                    done();
                });
        });

        it("the update should be in effect", (done) => {
            chai.request(server)
                .get('/api/posts/id=' + test_post_id)
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.success.should.eql(true);
                    res.body.post.should.have.property('title');
                    res.body.post.should.have.property('user_id');
                    res.body.post.should.have.property('_id').eql(test_post_id);
                    res.body.post.should.have.property('description').eql(change_string);
                    done();
                });
        })
    });


});
