package com.example.bulletinboard.Network;

import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * GetJSONObjectRequest
 * handles a request sending back a json object
 * @author Alexandre Krattinger
 */

public class GetJSONObjectRequest implements Request {

    private Status status;
    private Connection connection;
    private final VolleyCallback<JSONObject> callback;
    private final String url;

    private GetJSONObjectRequest(String url, VolleyCallback<JSONObject> callback) {
        connection = Connection.get();
        this.url = url;
        this.callback = callback;
        this.status = Status.INITIALISED;
    }

    public void send(){
        Log.d("SEND BEGINNING", "SENT");
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                    url, null, response -> {
                        status = Status.SUCCESS;
                        Log.d("SEND SUCCESS", response.toString());
                        callback.onSuccess(response);
                    }, response -> {
                        status = Status.ERROR;
                        Log.d("SEND FAILURE", response.toString());
                        callback.onFailure();
                    });

            connection.getRequestQueue().add(jsonObjReq);

            status = Status.SENT;
    }

    public Status getStatus(){
        return status;
    }

    public static GetJSONObjectRequest getAllPosts(VolleyCallback<JSONObject> callback){
        return new GetJSONObjectRequest("http://104.197.33.114:8000/api/posts/all",callback);
    }
}
