package com.example.bulletinboard;

import android.net.Network;
import android.support.constraint.solver.Cache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

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
        addPostToServer(thisPost);
    }

    public void addPostToServer(Post p) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://104.197.33.114:8000/api/posts/";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("title", p.getTitle());
        params.put("description", p.getDescription());

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        return;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        queue.add(req);
    }

}

