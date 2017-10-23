package com.example.bulletinboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bulletinboard.network.Connection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connection.get(this);
    }

    public void createPostScreen(View view){
        Intent intent = new Intent(this, CreateNewPost.class);
        startActivity(intent);
    }

    public void showPostScreen(View view){
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }
}
