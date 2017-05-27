package com.example.cafe_project;

/**
 * Created by lenovo on 5/15/2017.
 */

public class Drinks {

    private String coffee;
    //private int imageResourceId;
    private String image;

    public Drinks(){

    }

   /* public static final Drinks[] drinks = {
            new Drinks ("Cappuccino", R.drawable.cappuccino),
            new Drinks ("Latte", R.drawable.latte)
    };

    public Drinks(String coffee, int imageResourceId ) {
        this.coffee = coffee;
        this.imageResourceId = imageResourceId;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String coffee) {
        this.coffee = coffee;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    } */


    //Drinks for getting from Firebase
    public Drinks(String coffee, String image ) {
        this.coffee = coffee;
        this.image = image;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String coffee) {
        this.coffee = coffee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
