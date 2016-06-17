package com.example.bgm.geekchat;

/**
 * Created by bgm on 18.05.16.
 */
public class MyConstants {
    public static final String FIREBASE_URL = "https://glowing-fire-8710.firebaseio.com/";
    public static String idUser ="";
    public static String avatarId;
    public static String name;
    public static String email;
    public static String getAvatarId() {
        return avatarId;
    }

    public static void setAvatarId(String avatarId) {
        MyConstants.avatarId = avatarId;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        MyConstants.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MyConstants.email = email;
    }
}