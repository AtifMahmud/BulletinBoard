const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

// Allows cross origin requests
const cors = require('cors');

var app = express();
app.use(bodyParser.json());
app.use(cors());

// Routes
var users = require('./routes/users_route');
var posts = require('./routes/posts_route');

// Use the routes
app.use('/api/users', users);
app.use('/api/posts', posts);

// Connect to the database
mongoose.connect('mongodb://localhost/database');
const db = mongoose.connection;

app.get('/api/docs', function(req,resp) {
    resp.sendFile('html/documentation.html', {root: __dirname});
});

var port = 8000;
app.listen(port);
console.log("Running on port " + port);

// Export the app
module.exports = app;
