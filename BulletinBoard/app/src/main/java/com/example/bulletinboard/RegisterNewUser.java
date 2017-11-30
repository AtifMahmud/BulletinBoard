package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bulletinboard.network.PostJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

/**
 * Created by atifm on 11/12/2017.
 */

public class RegisterNewUser extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        context = this;
    }


    public void registerUser(View view){
        String firstName = ((EditText) findViewById(R.id.FirstName)).getText().toString().toLowerCase();
        String lastName = ((EditText) findViewById(R.id.LastName)).getText().toString().toLowerCase();
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString().toLowerCase();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString().toLowerCase();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        User thisUser = new User(firstName, lastName, email, phone, password);
        addUser(thisUser, context);
    }

    public void addUser(User user, Context context){
        // postList.add(post);
        PostJSONObjectRequest request = PostJSONObjectRequest.post(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("USER POSTED",response.toString());

                Context context = getApplicationContext();
                CharSequence text = "Account created";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent intent = new Intent(context, LoginUser.class);
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Log.d("You messed up","Failure");
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        }, user);

        request.send();
    }
}

