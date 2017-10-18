package com.example.bulletinboard;

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

    public Post getPost(int index){
        return postList.get(index);
    }

    public ArrayList<Post> getPosts(){
        return postList;
    }
}
