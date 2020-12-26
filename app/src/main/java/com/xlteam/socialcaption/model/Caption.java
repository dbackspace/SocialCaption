package com.xlteam.socialcaption.model;

import java.util.ArrayList;

public class Caption {
    private String id;
    private String content;
    private String pathImage;
    private ArrayList<Integer> category;
    private String userName;
    private String userId;

    public Caption() {
    }

    public Caption(String content, String pathImage) {
        this.content = content;
        this.pathImage = pathImage;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<Integer> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Integer> category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Caption{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", pathImage='" + pathImage + '\'' +
                ", category=" + category +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
