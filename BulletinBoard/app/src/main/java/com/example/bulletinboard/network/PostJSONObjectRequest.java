package com.example.bulletinboard.network;

import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bulletinboard.LoginUser;
import com.example.bulletinboard.Post;
import com.example.bulletinboard.User;
import org.json.JSONObject;
import com.example.bulletinboard.LoginBody;
import java.util.HashMap;

/**
 * Created by alexandre on 18/10/17.
 */

public class PostJSONObjectRequest implements Request {

    private Status status = Status.UNINITIALISED;
    private Connection connection;
    private final VolleyCallback<JSONObject> callback;
    private final String url;
    private final JSONObject params;

    private PostJSONObjectRequest(String url, VolleyCallback<JSONObject> callback, JSONObject params){
        connection = Connection.get();
        this.url = url;
        this.callback = callback;
        this.status = Status.INITIALISED;
        this.params = params;
    }

    @Override
    public void send() {
        JsonObjectRequest req = new JsonObjectRequest(url, params,
                response -> {
                    status = Status.SUCCESS;
                    callback.onSuccess(response);
                },
                e -> {
                    status = Status.ERROR;
                    Log.d("ERROR WITH POST",e.toString());
                    callback.onFailure();
                });

        connection.getRequestQueue().add(req);
        status = Status.SENT;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    public static PostJSONObjectRequest post(VolleyCallback<JSONObject> callback, Post p){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user_id","temp user");
        params.put("title", p.getTitle());
        params.put("date",p.getDate());
        params.put("showEmail", Boolean.toString(p.getShowEmail()));
        params.put("showPost", Boolean.toString(p.getShowPhone()));
        params.put("description", p.getDescription());

        return new PostJSONObjectRequest("http://104.197.33.114:8000/api/posts/",callback,new JSONObject(params));
    }



    public static PostJSONObjectRequest post(VolleyCallback<JSONObject> callback, User u){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("first_name", u.getFirstName());
        params.put("last_name",u.getLastName());
        params.put("email", u.getEmail());
        params.put("phone", u.getPhone());
        params.put("password", u.getPassword());

        return new PostJSONObjectRequest("http://104.197.33.114:8000/api/users/",callback,new JSONObject(params));
    }

    public static PostJSONObjectRequest post(VolleyCallback<JSONObject> callback, LoginBody loginBody){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", loginBody.getEmail());
        params.put("password", loginBody.getPassword());

        return new PostJSONObjectRequest("http://104.197.33.114:8000/api/users/authenticate/email",callback,new JSONObject(params));
    }


}
