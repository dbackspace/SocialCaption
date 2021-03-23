package com.xlteam.textonpicture.model;

public class Color {
    private String color;
    private String textColor;

    public Color(String color, String textColor) {
        this.color = color;
        this.textColor = textColor;
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
