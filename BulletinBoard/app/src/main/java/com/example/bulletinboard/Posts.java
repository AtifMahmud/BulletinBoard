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
import com.android.volley.toolbox.Volley;

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
        //addPostToServer(post);
    }

    public Post getPost(int index){
        return postList.get(index);
    }

    public ArrayList<Post> getPosts(){
        return postList;
    }
}
