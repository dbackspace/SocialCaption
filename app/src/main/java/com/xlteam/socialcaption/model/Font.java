package com.xlteam.socialcaption.model;

public class Font implements Comparable<Font> {
    private String fontName;
    private String font;

    public Font(String fontName, String font) {
        this.fontName = fontName;
        this.font = font;
    }

    public String getFontName() {
        return fontName;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    @Override
    public int compareTo(Font font) {
        return getFontName().compareToIgnoreCase(font.getFontName());
    }
}
