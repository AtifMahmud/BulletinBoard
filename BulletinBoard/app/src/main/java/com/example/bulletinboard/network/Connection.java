package com.example.bulletinboard.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * OLDNetwork Connection
 * Contains the useful variables for requests
 *
 * Connection is a Singleton class
 */

public final class Connection{
    private final RequestQueue requestQueue;
    private static Connection instance;

    private Connection(Context context){

        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
    }

    public synchronized static Connection get(Context context) {
        if (instance == null) {
            instance = new Connection(context);
        }

        return instance;
    }

    public synchronized static Connection get() {
        if (instance == null) {
                throw new IllegalStateException();
        }

        return instance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
