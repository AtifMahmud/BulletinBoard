package com.example.bulletinboard.Network;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * OLDNetwork Connection
 * Contains the useful variables for requests
 *
 * Connection is a Singleton class
 */

public final class Connection extends Application {
    private final RequestQueue requestQueue;
    private static Connection instance;

    private Connection(){

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
    }

    public synchronized static Connection get() {
        if (instance == null) {
            instance = new Connection();
        }

        return instance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
