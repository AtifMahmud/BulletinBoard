package com.example.bulletinboard;

import android.util.Log;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

/**
 * Created by alexandre on 29/10/17.
 */

public class User {
    private final String name;
    private final String firstName;
    private final String email;
    private final String phone;

    public User(String name, String firstName, String email, String phone){
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
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

        return new User("","","","");
    }
}
