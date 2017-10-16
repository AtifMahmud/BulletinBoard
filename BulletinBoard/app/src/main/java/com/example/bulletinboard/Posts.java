package com.example.bulletinboard;

import android.net.Network;
import android.support.constraint.solver.Cache;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import java.io.File;
import java.util.ArrayList;
import java.lang.Object;
import org.json.*;
import java.util.HashMap;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


/**
 * Class: Posts
 * Public Methods: getInstance() - gets the posts object (which is made up of Array
 *                               List of Post
 *                 addPost() - adds a post to the array
 *                 getPost(index) - returns the post at an index
 * Description: Class to hold an ArrayList of posts for use on the show post page
 * Created by Logan on 2017-10-14.
 */

public class Posts {
    private static Posts posts = new Posts();
    private ArrayList<Post> postList;

    private Posts(){
        postList = new ArrayList<>();
    }

    public static Posts getInstance(){
        return posts;
    }

    public void addPost(Post post){

        postList.add(post);
        addPostToServer(post);
    }

    public Post getPost(int index){
        return postList.get(index);
    }

    public ArrayList<Post> getPosts(){
        return postList;
    }

    public void addPostToServer(Post p){

        RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

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

        // Add the request to the RequestQueue.
        mRequestQueue.add(req);
    }

    public void updatePostList(){

        RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        String url = "http://104.197.33.114:8000/api/posts/all/";

        //Makes the actual request
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("success") == "true") {
                                populateArray(response.getJSONArray("posts"));
                            }
                        }
                        catch(org.json.JSONException e){
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
        mRequestQueue.add(req);

    }

    private void populateArray(JSONArray data){
        postList = new ArrayList<>();
        for(int i = 0; i < data.length(); i++){
            Post p;
            try {
                p = new Post(data.getJSONObject(i).getString("title"), data.getJSONObject(i).getString("description"));
            }
            catch (org.json.JSONException e){
                e.printStackTrace();
            }
            postList.add(p);
        }
    }
}
