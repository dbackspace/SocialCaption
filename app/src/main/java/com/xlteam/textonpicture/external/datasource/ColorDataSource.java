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
        colors.add("#FF000000"); // black
        colors.add("#FFFFFFFF"); // white
        colors.add("#FF0000FF"); // blue
        colors.add("#FF00FFFF"); // cyan, aqua
        colors.add("#FF00FF00"); // green
        colors.add("#FFFFFF00"); // yellow
        colors.add("#FFFF0000"); // red
        colors.add("#FFFF6633"); // orange
        colors.add("#FF800080"); // purple
        colors.add("#FFFF00FF"); // magenta
    }

    public ArrayList<String> getAllData() {
        return colors;
    }

}
