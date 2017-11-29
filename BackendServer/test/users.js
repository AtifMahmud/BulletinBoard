
process.env.NODE_ENV = 'test';

let mongoose = require("mongoose");
let Users = require('../models/users_model');

let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../app');
let should = chai.should();

chai.use(chaiHttp);

let test_user_id = "59e2ec7950a9746df27e05f4";

describe('Users Tests', function() {

    // Clear the test database
    before((done) => {
        Users.clearAll( () => { done(); } );
    });

    describe('/GET users', function() {

        it('should GET all users', (done) => {
            chai.request(server)
                .get('/api/users/all')
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.users.should.be.a('array');
                    res.body.users.length.should.be.eql(0);
                    done();
                    //console.log(res.body);
                });
        });
    });

    describe('/POST post', () => {
        it('it should NOT POST without password field', (done) => {
            let user = {
                first_name: "Oops, i forgot to add an password field.",
                last_name: "Should fail",
                email:"fakeemail@fakeemail.edu"
            };
            chai.request(server)
                .post('/api/users/')
                .send(user)
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
            let user = {
                _id: test_user_id,
                first_name: "John",
                last_name: "Appleseed",
                email: "fakeemail@fakepath.edu",
                password:"password123"
            };
            chai.request(server)
                .post('/api/users/')
                .send(user)
                .end((err, res) => {
                    //console.log(res.body);
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.should.have.property('success');
                    res.body.should.have.property('_id');
                    res.body.should.have.property('token');
                    res.body.success.should.eql(true);
                    done();
                });
        });

    });

    describe('/GET/:id user', () => {
        it('it should GET a user with the given id', (done) => {
            chai.request(server)
                .get('/api/users/id=' + test_user_id)
                .end((err, res) => {
                    //console.log(res.body);
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.success.should.eql(true);
                    res.body.user.should.have.property('first_name');
                    res.body.user.should.have.property('last_name');
                    res.body.user.should.have.property('email');
                    res.body.user.should.have.property('_id').eql(test_user_id);
                    done();
                });
        });
    });

    describe('/POST/update user', () => {
        let change_string = "NEWEMAIL@NEWEMAIL.EDU";
        it("it should SET an existing post's fields with the given id", (done) => {
            let updates = {email: change_string};
            chai.request(server)
                .post('/api/users/update/id=' + test_user_id)
                .send(updates)
                .end((err, res) => {
                    //console.log(res.body);
                    res.body.success.should.eql(true);
                    done();
                });
        });

        it("the update should be in effect", (done) => {
            chai.request(server)
                .get('/api/users/id=' + test_user_id)
                .end((err, res) => {
                    //console.log(res.body);
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.success.should.eql(true);
                    res.body.user.should.have.property('first_name');
                    res.body.user.should.have.property('last_name');
                    res.body.user.should.have.property('_id').eql(test_user_id);
                    res.body.user.should.have.property('email').eql(change_string);
                    done();
                });
        })
    });


});
