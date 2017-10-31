package com.example.bulletinboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private String date;
    private boolean showPhone;
    private boolean showEmail;
    // Don't think we need this
    private String id;
    // Fields to get from user class
    private String user_id;
    private String email;
    private String phone;

    public Post(){}

    public Post(String title, String description, boolean showPhone, boolean showEmail){
        //TODO: Fix this
        /*
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());
         */
        this(title, description, showPhone, showEmail, date);

    }

    public Post(String title, String description, boolean showPhone, boolean showEmail, String date){
        this.title = title;
        this.description = description;
        this.showPhone = showPhone;
        this.showEmail = showEmail;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        this.date = df.format(c.getTime());
    }

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

    public String getDate() { return this.date; }

    public boolean getShowPhone() { return this.showPhone; }

    public boolean getShowEmail() { return this.showEmail; }

    public String getId() { return this.id; }

    public String getUserId() { return this.user_id; }

    public String getEmail() { return this.email; }

    public String getPhone() { return this.phone; }

}
