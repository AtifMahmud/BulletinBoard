package com.example.bulletinboard;

import android.os.ConditionVariable;
import android.service.notification.Condition;
import android.util.Log;

import com.android.volley.toolbox.RequestFuture;
import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.GetSynchJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by alexandre on 29/10/17.
 */

public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private final String password;
    private double rating;
    private int totalRating;
    private int ratingCount;

    public User empty(){
        return new User("","","","","");
    }

    public User(String firstName, String lastName, String email, String phone, String password, Double rating){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.rating = 0;
        this.ratingCount = 0;
        this.totalRating = 0;
    }

    public User(String firstName, String lastName, String email, String phone, String password){
        this(firstName, lastName, email, phone, password, 0.0);
    }

    public void updateRating(int newRating){
        this.ratingCount++;
        this.totalRating += newRating;
        if (ratingCount != 0)
            this.rating = totalRating/ratingCount;
    }


    public User getUserById(String id){

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        GetSynchJSONObjectRequest request = GetSynchJSONObjectRequest.getUser(id, future);

        request.send();
        try {
            JSONObject response = future.get();
            if(response.getString("success").equals("true")){
                JSONObject data = response.getJSONObject("user");
                return new User(
                    data.getString("first_name"),
                    data.getString("last_name"),
                    data.getString("email"),
                    data.getString("phone"),
                        "",
                    data.getDouble("rating")
                );
            }
            else{
                Log.d("USER FAILED","Couldn't get user by ID, Server issue");
                return empty();
            }
        } catch (Exception e) {
            Log.d("USER FAILED","Couldn't get user by ID, Exception");
            e.printStackTrace();
            return empty();
        }
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
