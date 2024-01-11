package com.example.logintest.useless;

public class User_Info {
    private String UserName;
    private String PassWord;

    public User_Info(String usename, String password){
        UserName=usename;
        PassWord=password;
    }
    String getUserName(){
        return UserName;
    }
    String getPassWord(){
        return PassWord;
    }
}
