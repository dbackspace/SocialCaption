package com.xlteam.socialcaption.external.datasource;

import java.util.ArrayList;

public class ColorDataSource {
    private ArrayList<String> colorsMini = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();

    private static class SingletonHelper {
        private static final ColorDataSource INSTANCE = new ColorDataSource();
    }

    public static ColorDataSource getInstance() {
        return ColorDataSource.SingletonHelper.INSTANCE;
    }

    private ColorDataSource() {
        createDataMini();
        createData();
    }

    private void createDataMini() {
        colorsMini.add("#FF000000"); // black
        colorsMini.add("#FFFFFFFF"); // white
        colorsMini.add("#FF0000FF"); // blue
        colorsMini.add("#FF00FFFF"); // cyan, aqua
        colorsMini.add("#FF00FF00"); // green
        colorsMini.add("#FFFFFF00"); // yellow
        colorsMini.add("#FFFF0000"); // red
        colorsMini.add("#FFFF6633"); // orange
        colorsMini.add("#FF800080"); // purple
        colorsMini.add("#FFFF00FF"); // magenta
    }

    private void createData() {
        colors.add("#5500FFFF"); // cyan, aqua
        colors.add("#7700FFFF"); // cyan, aqua
        colors.add("#9900FFFF"); // cyan, aqua
        colors.add("#BB00FFFF"); // cyan, aqua
        colors.add("#DD00FFFF"); // cyan, aqua
        colors.add("#FF00FFFF"); // cyan, aqua => màu nền mặc định
        colors.add("#5500FF00"); // green
        colors.add("#7700FF00"); // green
        colors.add("#9900FF00"); // green
        colors.add("#BB00FF00"); // green
        colors.add("#DD00FF00"); // green
        colors.add("#FF00FF00"); // green
        colors.add("#55FFFF00"); // yellow
        colors.add("#77FFFF00"); // yellow
        colors.add("#99FFFF00"); // yellow => màu sóng mặc định
        colors.add("#BBFFFF00"); // yellow
        colors.add("#DDFFFF00"); // yellow
        colors.add("#FFFFFF00"); // yellow
        colors.add("#55FF0000"); // red
        colors.add("#77FF0000"); // red
        colors.add("#99FF0000"); // red
        colors.add("#BBFF0000"); // red
        colors.add("#DDFF0000"); // red
        colors.add("#FFFF0000"); // red => màu số mặc định
        colors.add("#55FF6633"); // orange
        colors.add("#77FF6633"); // orange
        colors.add("#99FF6633"); // orange
        colors.add("#BBFF6633"); // orange
        colors.add("#DDFF6633"); // orange
        colors.add("#FFFF6633"); // orange
        colors.add("#55FF00FF"); // magenta
        colors.add("#77FF00FF"); // magenta
        colors.add("#99FF00FF"); // magenta
        colors.add("#BBFF00FF"); // magenta
        colors.add("#DDFF00FF"); // magenta
        colors.add("#FFFF00FF"); // magenta
        colors.add("#55800080"); // purple
        colors.add("#77800080"); // purple
        colors.add("#99800080"); // purple
        colors.add("#BB800080"); // purple
        colors.add("#DD800080"); // purple
        colors.add("#FF800080"); // purple
        colors.add("#550000FF"); // blue
        colors.add("#770000FF"); // blue
        colors.add("#990000FF"); // blue
        colors.add("#BB0000FF"); // blue
        colors.add("#DD0000FF"); // blue
        colors.add("#FF0000FF"); // blue => màu chữ mặc định
        colors.add("#55996633"); // brown
        colors.add("#77996633"); // brown
        colors.add("#99996633"); // brown
        colors.add("#BB996633"); // brown
        colors.add("#DD996633"); // brown
        colors.add("#FF996633"); // brown
        colors.add("#55000000"); //black
        colors.add("#77000000"); //black
        colors.add("#99000000"); //black
        colors.add("#BB000000"); //black
        colors.add("#DD000000"); //black
        colors.add("#FF000000"); //black
        colors.add("#55F5F5F5"); //white
        colors.add("#77F5F5F5"); //white
        colors.add("#99F5F5F5"); //white
        colors.add("#BBF5F5F5"); //white
        colors.add("#DDF5F5F5"); //white
        colors.add("#FFF5F5F5"); //white

    }

    public ArrayList<String> getAllDataMini() {
        return colorsMini;
    }

    public ArrayList<String> getAllData() {
        return colors;
    }
}
