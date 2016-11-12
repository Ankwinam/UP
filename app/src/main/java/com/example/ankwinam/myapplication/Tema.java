package com.example.ankwinam.myapplication;

import android.graphics.Bitmap;

/**
 * Created by axx42 on 2016-09-09.
 */
public class Tema {
    public String name;
    public String gender;
    public String age;
    public Bitmap image;

    Tema(String name, String gender, String age, Bitmap image){
        this.image = image;
        this.name =name;
        this.gender = gender;
        this.age = age;
    }
}
