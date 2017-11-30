package com.example.bulletinboard.network;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

/**
 * GetSynchJSONObjectRequest
 * handles a request sending back a json object
 * @author Alexandre Krattinger
 */
public class GetSynchJSONObjectRequest implements Request {
    private Status status = Status.UNINITIALISED;
    private Connection connection;
    private final String url;
    private final RequestFuture<JSONObject> future;

    private GetSynchJSONObjectRequest(String url, RequestFuture<JSONObject> future) {
        connection = Connection.get();
        this.url = connection.getBaseUrl() + url;
        this.status = Status.INITIALISED;
        this.future = future;
    }

    public void send(){
        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
        connection.getRequestQueue().add(request);
    }

    public Status getStatus(){
        return status;
    }


    public static GetSynchJSONObjectRequest getUser(String id, RequestFuture<JSONObject> future){
        return new GetSynchJSONObjectRequest("/api/users/id="+id, future);
    }
}
