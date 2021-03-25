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
        colors.add("0000FF"); // blue
        colors.add("00FFFF"); // cyan, aqua
        colors.add("00FF00"); // green
        colors.add("FFFF00"); // yellow
        colors.add("FF0000"); // red
        colors.add("FF6633"); // orange
        colors.add("800080"); // purple
        colors.add("FF00FF"); // magenta
    }

    public ArrayList<String> getAllData() {
        return colors;
    }

}
