package com.example.bulletinboard;

/**
 * Class: Post
 * Purpose: The class holding all of the data for the Post
 * Methods: getTitle() - returns the title
 *          getDescription() - returns the description
 * Created by Logan on 2017-10-13.
 */

public class Post {
    private String title;
    private String description;

    public Post(){}

    public Post(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

}
