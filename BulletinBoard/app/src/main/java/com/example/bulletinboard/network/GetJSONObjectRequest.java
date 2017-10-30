package com.example.bulletinboard.network;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * GetJSONObjectRequest
 * handles a request sending back a json object
 * @author Alexandre Krattinger
 */

public class GetJSONObjectRequest implements Request {

    private Status status = Status.UNINITIALISED;
    private Connection connection;
    private final VolleyCallback<JSONObject> callback;
    private final String url;

    private GetJSONObjectRequest(String url, VolleyCallback<JSONObject> callback) {
        connection = Connection.get();
        this.url = connection.getBaseUrl() + url;
        this.callback = callback;
        this.status = Status.INITIALISED;
    }

    public void send(){

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                    url, null, response -> {
                        status = Status.SUCCESS;
                        callback.onSuccess(response);
                    }, e -> {
                        status = Status.ERROR;
                        callback.onFailure();
                    });

            connection.getRequestQueue().add(jsonObjReq);

            status = Status.SENT;
    }

    public Status getStatus(){
        return status;
    }

    public static GetJSONObjectRequest getAllPosts(VolleyCallback<JSONObject> callback){
        return new GetJSONObjectRequest("/api/posts/all",callback);
    }

    public static GetJSONObjectRequest getUser(VolleyCallback<JSONObject> callback, String id){
        return new GetJSONObjectRequest("/api/users/id="+id,callback);
    }
}
