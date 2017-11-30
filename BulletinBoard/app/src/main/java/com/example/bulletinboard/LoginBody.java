package com.example.bulletinboard;

/**
 * Created by atifm on 11/29/2017.
 */

public class LoginBody {
    private String email;
    private  String password;

    public LoginBody(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){ return this.email;}
    public String getPassword(){ return this.password;}
}
