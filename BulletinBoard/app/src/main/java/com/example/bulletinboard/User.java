package com.example.bulletinboard;

import android.util.Log;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

/**
 * Created by alexandre on 29/10/17.
 */

public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private String password;
    private double rating;
    private int totalRating;
    private int ratingCount;

    public User(String firstName, String lastName, String email, String phone, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.rating = 0;
        this.ratingCount = 0;
        this.totalRating = 0;
    }

    public void updateRating(int newRating){
        this.ratingCount++;
        this.totalRating += newRating;
        if (ratingCount != 0)
            this.rating = totalRating/ratingCount;
    }


    public User getUserById(String id){

        GetJSONObjectRequest request = GetJSONObjectRequest.getUser(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("GET SUCCEEDED","yeah");
            }
            @Override
                public void onFailure() {
                Log.d("failed","Failure");
            }
        }, id);

        request.send();

        return new User("","","","","");
    }

    public String getFirstName() { return this.firstName;}
    public String getLastName() { return  this.lastName;}
    public String getEmail() { return this.email;}
    public String getPhone () { return this.phone;}
    public String getPassword () { return this.password;}
    public double getRating () { return  this.rating; }
    public int getRatingCount () { return  ratingCount; }
    public int getTotalRating () { return  totalRating; }

}
