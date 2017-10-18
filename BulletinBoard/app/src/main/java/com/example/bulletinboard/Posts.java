package com.example.bulletinboard;

import android.util.Log;

import com.example.bulletinboard.Network.GetJSONObjectRequest;
import com.example.bulletinboard.Network.Status;
import com.example.bulletinboard.Network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
        //addPostToServer(post);
    }

    public void addPosts(JSONObject json){
        try {
            if (json.get("success") == "true") {

                JSONArray data = json.getJSONArray("posts");

                for (int i = 0; i < data.length(); i++) {
                    Post p;
                    p = new Post(data.getJSONObject(i).getString("title"), data.getJSONObject(i).getString("description"));
                    postList.add(p);

                }
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public Post getPost(int index){
        return postList.get(index);
    }

    public ArrayList<Post> getPosts(){
       GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                addPosts(response);
            }

            @Override
            public void onFailure() {

            }
        });

        request.send();

        while (request.getStatus() != Status.SUCCESS || request.getStatus() != Status.ERROR);
        return postList;
    }
}
