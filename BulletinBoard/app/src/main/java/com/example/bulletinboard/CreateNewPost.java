package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
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
        CheckBox showPhone = (CheckBox) findViewById(R.id.showPhoneButton);
        CheckBox showEmail = (CheckBox) findViewById(R.id.showEmailButton);

        Context context = getApplicationContext();
        CharSequence text = "Values : " + showPhone.isChecked() + " " + showEmail.isChecked();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        boolean showPhoneState = showPhone.isChecked();
        boolean showEmailState = showEmail.isChecked();
        Post thisPost = new Post(title, description, "tempId", "tempUser", showPhoneState, showEmailState); //TODO CHANGE THAT

        Posts.addPost(thisPost, context);
    }

    public void switchToPosts() {
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }

    public void showPostScreen() {
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }
}

