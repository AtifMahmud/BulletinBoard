package com.example.bulletinboard.Network;

/**
 * Created by alexandre on 18/10/17.
 */

public interface VolleyCallback<T> {
    void onSuccess(T response);
    void onFailure();
}
