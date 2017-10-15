package com.example.bulletinboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        Posts posts = Posts.getInstance();
        Post thisPost = posts.getPost(0);

        PostsAdapter adapter = new PostsAdapter(this, posts.getPosts());

        ListView listView = (ListView) findViewById(R.id.post_list);
        listView.setAdapter(adapter);
    }


}
