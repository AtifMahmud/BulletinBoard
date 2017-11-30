package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.os.ConditionVariable;
import android.service.notification.Condition;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.RequestFuture;
import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.GetSynchJSONObjectRequest;
import com.example.bulletinboard.network.PostJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    public static User empty() {
        return new User("", "", "", "", "");
    }

    public User(String firstName, String lastName, String email, String phone, String password, Double rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.rating = 0.0;
        this.ratingCount = 0;
        this.totalRating = 0;
    }

    public User(String firstName, String lastName, String email, String phone, String password) {
        this(firstName, lastName, email, phone, password, 0.0);
    }

    public static void updateRating(float newRating, String raterUserId, String rateeUserId, Context context) {
        PostJSONObjectRequest request = PostJSONObjectRequest.post(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("RATING POSTED", "HeY");
                CharSequence text = "Your rating has been added";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent showPost = new Intent(context, ShowPost.class);
                context.startActivity(showPost);
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
                CharSequence text = "Rating not added";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }, raterUserId, rateeUserId, newRating);

        request.send();
    }


    public static User getUserById(String id) {

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        GetSynchJSONObjectRequest request = GetSynchJSONObjectRequest.getUser(id, future);

        JSONObject response = request.sendAndGet();
        try {
            if (response.getString("success").equals("true")) {
                JSONObject data = response.getJSONObject("user");
                return new User(
                        data.getString("first_name"),
                        data.getString("last_name"),
                        data.getString("email"),
                        "0000000", //TODO CHANGED
                        "",
                        data.getDouble("rating")
                );
            } else {
                Log.d("USER FAILED", "Couldn't get user by ID, Server issue");
                return User.empty();
            }
        }
        catch (org.json.JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPassword() {
        return this.password;
    }

    public double getRating() {
        return this.rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getTotalRating() {
        return totalRating;
    }

}
