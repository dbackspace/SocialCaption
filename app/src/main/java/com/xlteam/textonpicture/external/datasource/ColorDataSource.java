package com.xlteam.textonpicture.external.datasource;

import java.util.ArrayList;

public class ColorDataSource {
    private final ArrayList<String> colors = new ArrayList<>();

    private static class SingletonHelper {
        private static final ColorDataSource INSTANCE = new ColorDataSource();
    }

    public static ColorDataSource getInstance() {
        return ColorDataSource.SingletonHelper.INSTANCE;
    }

    private ColorDataSource() {
        createData();
    }

    private void createData() {
        colors.add("000000"); // black
        colors.add("FFFFFF"); // white
        colors.add("A9A9A9");
        colors.add("E0E0E0");
        colors.add("CD853F");
        colors.add("FF0000"); // red
        colors.add("ff4000");
        colors.add("ff8000");
        colors.add("ffbf00");
        colors.add("ffff00");
        colors.add("bfff00");
        colors.add("80ff00");
        colors.add("40ff00");
        colors.add("00ff00");
        colors.add("00ff40");
        colors.add("00ff80");
        colors.add("00ffbf");
        colors.add("00ffff");
        colors.add("00bfff");
        colors.add("0080ff");
        colors.add("0040ff");
        colors.add("0000ff");
        colors.add("4000ff");
        colors.add("8000ff");
        colors.add("bf00ff");
        colors.add("ff00ff");
        colors.add("ff00bf");
        colors.add("ff0080");
        colors.add("ff0040");
    }

    public ArrayList<String> getAllData() {
        return colors;
    }

}
