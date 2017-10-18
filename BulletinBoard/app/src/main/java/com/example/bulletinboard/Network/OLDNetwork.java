package com.example.bulletinboard.Network;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.bulletinboard.Posts;

import org.json.JSONObject;

/**
 * OLDNetwork controller
 */

public class OLDNetwork extends Application {

    private interface VolleyCallback<T> {
        void onSuccess(T response);
    }

    private RequestQueue queue;

    private static OLDNetwork instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized OLDNetwork getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
        }

        return queue;
    }

    private void requestJSON(String url, final VolleyCallback<JSONObject> callback) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        getRequestQueue().add(jsonObjReq);
    }

    public void updatePosts(Posts posts){
        final Posts p = posts;
        String url = "http://104.197.33.114:8000/api/posts/all";
        requestJSON(url, new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                p.addPosts(response);
            }
        });
    }

    /*private JSONObject jsonError(){
        JSONObject jsonError = new JSONObject();
        try {
            jsonError = new JSONObject("'success' : false");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonError;
    }*/

}
