package com.example.bulletinboard;

import java.util.ArrayList;
import java.lang.Object;
import org.json.*


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
    }

    public Post getPost(int index){
        return postList.get(index);
    }

    public ArrayList<Post> getPosts(){
        return postList;
    }

    private void updatePostList(){

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

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        populateArray(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        mRequestQueue.add(req);

    }

    private void populateArray(JSONArray data){
        postList = new ArrayList<>();
        for(JSONObject j : data){
            Post p = Post(j.get("title"), j.get("description"));
            postList.add(p);
        }
    }
}
