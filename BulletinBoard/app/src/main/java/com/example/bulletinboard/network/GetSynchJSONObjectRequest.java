package com.example.bulletinboard.network;

import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.bulletinboard.User;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

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

    public JSONObject sendAndGet(){
        send();

        try {
            JSONObject fail = new JSONObject();
            fail.put("success","false");

            try {
                return future.get(1, TimeUnit.SECONDS);
            }
            catch(java.util.concurrent.TimeoutException e){
                Log.d("USER FAILED", "Couldn't get user by ID, Timeout");
                e.printStackTrace();
                return fail;
            }
            catch(java.lang.InterruptedException e){
                Log.d("USER FAILED", "Couldn't get user by ID, Timeout");
                e.printStackTrace();
                return fail;
            }
            catch(java.util.concurrent.ExecutionException e){
                Log.d("USER FAILED", "Couldn't get user by ID, Error with concurrency");
                e.printStackTrace();
                return fail;
            }
        }
        catch (org.json.JSONException e){
            e.printStackTrace();
            return null;
        }

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
