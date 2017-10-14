package com.example.bulletinboard;

/**
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
