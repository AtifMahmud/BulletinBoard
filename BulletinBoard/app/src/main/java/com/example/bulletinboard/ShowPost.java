package com.example.bulletinboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        Posts posts = Posts.getInstance();
        updatePostList(posts.getPosts());
        Post thisPost = posts.getPost(0);

        PostsAdapter adapter = new PostsAdapter(this, posts.getPosts());

        ListView listView = (ListView) findViewById(R.id.post_list);
        listView.setAdapter(adapter);
    }

    public void updatePostList(ArrayList posts) {
        final ArrayList<Post> postList = new ArrayList<>();
        RequestQueue mRequestQueue;

        // Instantiate the cache
        /*Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);*/

        // Start the queue

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.start();

        String url = "http://104.197.33.114:8000/api/posts/all/";

        //Makes the actual request
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            if (response.get("success") == "true") {
                                populateArray(response.getJSONArray("posts"), postList);
                            }
                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(req);

    }

    private void populateArray(JSONArray data, ArrayList postList) {
        postList = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            Post p;
            try {
                p = new Post(data.getJSONObject(i).getString("title"), data.getJSONObject(i).getString("description"));
                postList.add(p);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
