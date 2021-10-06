package com.example.android.taskplanner;

public class User {
    public String fullName,age,email;
    public User(){
        // default constructor
    }
    public User(String fullName,String age, String email) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }
}