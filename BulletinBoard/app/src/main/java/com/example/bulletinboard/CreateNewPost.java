package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import static android.support.v4.content.ContextCompat.startActivity;


public class CreateNewPost extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
        context = this;
    }

    public void createPost(View view) {
        String title = ((EditText) findViewById(R.id.title)).getText().toString().toLowerCase();
        String description = ((EditText) findViewById(R.id.description)).getText().toString().toLowerCase();
        ToggleButton showPhone = (ToggleButton) findViewById(R.id.showPhoneButton);
        ToggleButton showEmail = (ToggleButton) findViewById(R.id.showEmailButton);
        boolean showPhoneState = showPhone.isChecked();
        boolean showEmailState = showEmail.isChecked();
        Post thisPost = new Post(title, description, showPhoneState, showEmailState);

        Posts posts = Posts.getInstance();
        posts.addPost(thisPost, context);
        // send fields to
    }

    public void switchToPosts(){
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }

    public void showPostScreen(){
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }
}

