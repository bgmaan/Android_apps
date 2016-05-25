package com.example.bgm.geekchat;

/**
 * Created by bgm on 14.05.16.
 */
public class User {

    private String name;
    private String email;
    private int friendsNumber;
    private String imgSrc;

    public User(String email, String name, int friendsNumber, String imgSrc) {
        this.email = email;
        this.name = name;
        this.friendsNumber = friendsNumber;
        this.imgSrc = imgSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getFriendsNumber() {
        return friendsNumber;
    }

    public void setFriendsNumber(int friendsNumber) {
        this.friendsNumber = friendsNumber;
    }
}
