package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bulletinboard.network.Connection;
import com.example.bulletinboard.network.PostJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;
import com.example.bulletinboard.LoginBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginUser extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user2);
        context = this;
    }

    public void loginUser(View view){
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString().toLowerCase();
        String password =  ((EditText) findViewById(R.id.password)).getText().toString();
        LoginBody loginBody = new LoginBody(email, password);
        authenticate(loginBody, context);
    }

    public void authenticate(LoginBody loginBody, Context context){
        PostJSONObjectRequest request = PostJSONObjectRequest.post(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {


                Context context = getApplicationContext();
                try {
                    if (response.getString("success").equals("true")) {
                        Log.d("LOGIN SUCCESSFUL",response.toString());

                        CharSequence text = "Login Success";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Connection.get().setUserId(response.getString("id"));
                        Log.d("ID", response.getString("id"));
                        Intent intent = new Intent(context, ShowPost.class);
                        startActivity(intent);
                    }
                     else {
                        Log.d("LOGIN ERROR", response.toString());
                        CharSequence text = "Login Failed";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {
                Log.d("You messed up", "Failure");
                CharSequence text = "Sorry, we've had a problem with the server. Please try again!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent intent = new Intent(context, LoginUser.class);
                startActivity(intent);
            }
        }, loginBody);

        request.send();
    }

}
