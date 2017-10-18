package com.example.bulletinboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class CreateNewPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
    }

    public void createPost(View view) {
        String title = ((EditText) findViewById(R.id.title)).getText().toString().toLowerCase();
        String description = ((EditText) findViewById(R.id.description)).getText().toString().toLowerCase();
        Post thisPost = new Post(title, description);

        Posts posts = Posts.getInstance();
        posts.addPost(thisPost);
        // send fields to
    }
}

