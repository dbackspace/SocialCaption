package com.xlteam.socialcaption.model;

public class Caption {
    private final String content;
    private final String pathImage;

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
}
