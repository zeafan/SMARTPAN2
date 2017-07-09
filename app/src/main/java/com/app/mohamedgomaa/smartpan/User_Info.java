package com.app.mohamedgomaa.smartpan;
import android.app.Application;
public class User_Info extends Application {
private String name,user_name,user_pass,Email,mobile;
    private int gender;

    public void setName(String name) {
        this.name = name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobile() {
        return mobile;
    }

    public int getGender() {
        return gender;
    }
}
