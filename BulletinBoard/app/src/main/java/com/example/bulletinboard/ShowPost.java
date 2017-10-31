package com.example.bulletinboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

public class ShowPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        updatePosts();
    }

    private void updatePosts(){
        Posts posts = Posts.getInstance();

        GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                posts.addPosts(response);
                displayPosts(posts);
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        });

        request.send();
    }

    private void displayPosts(Posts posts){
        PostsAdapter adapter = new PostsAdapter(this, posts.getPosts());

        ListView listView = (ListView) findViewById(R.id.post_list);
        listView.setAdapter(adapter);
    }


}
