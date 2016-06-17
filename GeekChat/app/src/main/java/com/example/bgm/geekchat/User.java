package com.example.bgm.geekchat;

/**
 * Created by bgm on 14.05.16.
 */
public class User {

    private String name;
    private String email;
    private String friendsNumber;
    private String imgSrc;
    private String userId;
    private String counter;

    public User(String email, String name, String friendsNumber, String imgSrc,String userId) {
        this.email = email;
        this.name = name;
        this.friendsNumber = friendsNumber;
        this.imgSrc = imgSrc;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFriendsNumber() {
        return friendsNumber;
    }

    public void setFriendsNumber(String friendsNumber) {
        this.friendsNumber = friendsNumber;
    }
}
