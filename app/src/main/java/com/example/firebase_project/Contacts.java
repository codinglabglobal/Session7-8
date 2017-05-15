package com.example.firebase_project;

/**
 * Created by lenovo on 5/11/2017.
 */

public class Contacts {


    private String name;
    private String address;
    private String hobby;

    public Contacts(){

    }

    public Contacts( String fullName,  String address, String hobby) {

        this.name = fullName;
        this.address = address;
        this.hobby = hobby;
    }



    public String getName() {
        return name;
    }

    public void setName(String fullName) {
        this.name = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

}
