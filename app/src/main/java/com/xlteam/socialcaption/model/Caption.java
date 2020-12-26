package com.xlteam.socialcaption.model;

public class Caption {
    private String content;
    private String pathImage;
    private int topic;
    private User user;

    public Caption(String content, String pathImage) {
        this.content = content;
        this.pathImage = pathImage;
    }

    public String getContent() {
        return content;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
