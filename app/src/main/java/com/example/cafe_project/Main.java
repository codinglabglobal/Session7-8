package com.example.cafe_project;

/**
 * Created by lenovo on 5/11/2017.
 */

public class Main {


    private String item;
    private String image;


    public Main(){

    }

    public Main(String item, String image) {
        this.item = item;
        this.image = image;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
