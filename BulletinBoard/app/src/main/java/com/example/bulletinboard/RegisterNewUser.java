package com.example.bulletinboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * Created by atifm on 11/12/2017.
 */

public class RegisterNewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
    }


    public void registerUser(View view){
        String name = ((EditText) findViewById(R.id.name)).getText().toString().toLowerCase();
        String firstName = ((EditText) findViewById(R.id.FirstName)).getText().toString().toLowerCase();
        String email = ((EditText) findViewById(R.id.email)).getText().toString().toLowerCase();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString().toLowerCase();
        User thisUser = new User(name, firstName, email, phone);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

