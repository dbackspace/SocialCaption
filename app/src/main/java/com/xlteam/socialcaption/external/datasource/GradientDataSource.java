package com.xlteam.socialcaption.external.datasource;

import java.util.ArrayList;

public class GradientDataSource {
    private static final String TAG = "ColorDataSource";
    private ArrayList<int[]> colors = new ArrayList<>();

    private static class SingletonHelper {
        private static final GradientDataSource INSTANCE = new GradientDataSource();
    }

    public static GradientDataSource getInstance() {
        return GradientDataSource.SingletonHelper.INSTANCE;
    }

    private GradientDataSource() {
        createData();
    }

    private void createData() {
        colors.add(new int[]{0xFFC6FFDD, 0xFFFBD786, 0xFFF7797D});
        colors.add(new int[]{0xFFEE9CA7, 0xFFffdde1});
        colors.add(new int[]{0xFF2193B0, 0xFF6dd5ed});
        colors.add(new int[]{0xFF2980B9, 0xFF6DD5FA, 0xFFFFFFFF});
        colors.add(new int[]{0xFF7F7FD5, 0xFF86A8E7, 0xFF91EAE4});
        colors.add(new int[]{0xFF654eA3, 0xFFeAAfc8});
        colors.add(new int[]{0xFFA8ff78, 0xFF78ffd6});
        colors.add(new int[]{0xFFFDC830, 0xFFFDC830});
        colors.add(new int[]{0xFF00B4DB, 0xFF00B4DB});
        colors.add(new int[]{0xFFFFEFBA, 0xFFFFFFFF});
    }

    public ArrayList<int[]> getAllData() {
        return colors;
    }
}
