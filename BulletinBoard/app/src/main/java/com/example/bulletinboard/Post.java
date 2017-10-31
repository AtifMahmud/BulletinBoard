package com.example.bulletinboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        this(title, description, showPhone, showEmail, getCurrentDateFormated());

    }

    public Post(String title, String description, boolean showPhone, boolean showEmail, String date){
        this.title = title;
        this.description = description;
        this.showPhone = showPhone;
        this.showEmail = showEmail;
        this.date = formatDate(date);

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

    /**
     * Helper method to get the current date formated correctly for the constructor
     * @return
     */
    private static String getCurrentDateFormated(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c.getTime());
    }

    private static String formatDate(String date){
        SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat clientFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date d = serverFormat.parse(date);
            return clientFormat.format(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}
