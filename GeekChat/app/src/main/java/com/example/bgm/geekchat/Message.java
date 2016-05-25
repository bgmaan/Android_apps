package com.example.bgm.geekchat;

/**
 * Created by bgm on 15.05.16.
 */
public class Message {

    private User user;
    private long timeMs;
    private String content;

    public Message(User user, long timeMs, String content) {
        this.user = user;
        this.timeMs = timeMs;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
