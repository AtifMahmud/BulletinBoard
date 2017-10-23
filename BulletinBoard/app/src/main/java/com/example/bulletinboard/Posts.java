package com.example.bulletinboard;

import android.util.Log;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.PostJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

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
        PostJSONObjectRequest request = PostJSONObjectRequest.post(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("posted",response.toString());
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        }, post);

        request.send();
    }


    public void addPosts(JSONObject json){

        try {
            if (json.getString("success").equals("true")) {
                Log.d("Add Posts", json.toString());
                JSONArray data = json.getJSONArray("posts");

                postList = new ArrayList<>();

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
        if(index < postList.size()) {
            return postList.get(index);
        }
        else{
            return new Post("Network Error","Go back and try again");
        }
    }

    public ArrayList<Post> getPosts(){

       GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                addPosts(response);
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        });

        request.send();

        //while (request.getStatus() != Status.SUCCESS || request.getStatus() != Status.ERROR);
        return postList;
    }
}
