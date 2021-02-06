package com.xlteam.socialcaption.model;

public class Color {
    private String color;
    private String textColor;

    public Color(String color, String text) {
        this.color = color;
        this.textColor = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
