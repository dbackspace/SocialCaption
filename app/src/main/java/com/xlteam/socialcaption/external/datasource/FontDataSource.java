package com.xlteam.socialcaption.external.datasource;


import com.xlteam.socialcaption.model.Font;

import java.util.ArrayList;
import java.util.Collections;

public class FontDataSource {
    private final ArrayList<Font> fonts = new ArrayList<>();

    private static class SingletonHelper {
        private static final FontDataSource INSTANCE = new FontDataSource();
    }

    public static FontDataSource getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private FontDataSource() {
        createDataForDialogText();
    }

    private void createDataForDialogText() {
        // loại các font: 5,6,7,8,11,16,20,23,24,25,36
        // templete test font: ÀÁẠẢÃ ĂẮẰẲẶẴ ÂẦẤẬẨẪ Đ ÊẾỀỂỆỄ ÌÍỈỊĨ \n ÒÓỌỎÕ ÔỔỒỐỖỘ ƠỚỜỞỢỠ ƯỪỨỬỰỮ \n àáạảã ẳắằặẵ ẩậầấẫ đ êềếệễể \n ịìíỉĩ òóọỏõ ộồốỗổ ợờớởỡ ựừứửữ
        fonts.add(new Font("Dancing Script Bold", "dancingscript_bold.ttf"));
        fonts.add(new Font("Quicksand Medium", "quicksand_medium.ttf"));
        fonts.add(new Font("Varela Regular", "varela_regular.ttf"));
        fonts.add(new Font("UTM Azuki", "utm_azuki.ttf"));
        fonts.add(new Font("Arima Madurai Thin", "ArimaMadurai-Thin.ttf"));
        fonts.add(new Font("Athiti Regular", "Athiti-Regular.ttf"));
        fonts.add(new Font("Comfortaa Regular", "Comfortaa-Regular.ttf"));
        fonts.add(new Font("David Libre Regular", "DavidLibre-Regular.ttf"));
        fonts.add(new Font("Farsan Regular", "Farsan-Regular.ttf"));
        fonts.add(new Font("Judson Bold", "Judson-Bold.ttf"));
        fonts.add(new Font("Jura Regular", "Jura-Regular.ttf"));
        fonts.add(new Font("Kanit Light", "Kanit-Light.ttf"));
        fonts.add(new Font("Maitree Medium", "Maitree-Medium.ttf"));
        fonts.add(new Font("Montserrat Extra Light", "Montserrat-ExtraLight.ttf"));
        fonts.add(new Font("Old Standard Regular", "OldStandard-Regular.ttf"));
        fonts.add(new Font("Oswald Extra Light", "Oswald-ExtraLight.ttf"));
        fonts.add(new Font("Patrick Hand SC Regular", "PatrickHandSC-Regular.ttf"));
        fonts.add(new Font("Philosopher Regular", "Philosopher-Regular.ttf"));
        fonts.add(new Font("Podkova Regular", "Podkova-Regular.ttf"));
        fonts.add(new Font("Prata Regular", "Prata-Regular.ttf"));
        fonts.add(new Font("Pridi Extra Light", "Pridi-ExtraLight.ttf"));
        fonts.add(new Font("Taviraj Italic", "Taviraj-Italic.ttf"));
        fonts.add(new Font("Yeseva One Regular", "YesevaOne-Regular.ttf"));
        fonts.add(new Font("Gill Sans MT", "gill_sans_mt.ttf"));
        Collections.sort(fonts);
    }

    public ArrayList<Font> getAllFonts() {
        return fonts;
    }
}
