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
        if (name == null){ name = "N/A";}
        return name;
    }

    public void setName(String fullName) {
        if (fullName == null){ fullName = "N/A";}
        this.name = fullName;
    }

    public String getAddress() {
        if (address == null) {address = "N/A";}
        return address;
    }

    public void setAddress(String address) {
        if (address == null) {address = "N/A";}
        this.address = address;
    }


    public String getHobby() {

        if (hobby == null){ hobby = "N/A";}
        return hobby;
    }

    public void setHobby(String hobby) {
        if (hobby == null){ hobby = "N/A";}
        this.hobby = hobby;
    }

}
