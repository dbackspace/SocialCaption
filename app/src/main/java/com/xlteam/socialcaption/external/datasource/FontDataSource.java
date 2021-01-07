package com.xlteam.socialcaption.external.datasource;

import com.xlteam.socialcaption.model.Font;

import java.util.ArrayList;
import java.util.Collections;

public class FontDataSource {
    private ArrayList<Font> fontsDialogText = new ArrayList<>();

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
        fontsDialogText.add(new Font("Dancing Script Bold", "dancingscript_bold.ttf"));
        fontsDialogText.add(new Font("Quicksand Medium", "quicksand_medium.ttf"));
        fontsDialogText.add(new Font("Varela Regular", "varela_regular.ttf"));
        fontsDialogText.add(new Font("UTM Azuki", "utm_azuki.ttf"));
        fontsDialogText.add(new Font("Arima Madurai Thin", "ArimaMadurai-Thin.ttf"));
        fontsDialogText.add(new Font("Athiti Regular", "Athiti-Regular.ttf"));
        fontsDialogText.add(new Font("Comfortaa Regular", "Comfortaa-Regular.ttf"));
        fontsDialogText.add(new Font("David Libre Regular", "DavidLibre-Regular.ttf"));
        fontsDialogText.add(new Font("Farsan Regular", "Farsan-Regular.ttf"));
        fontsDialogText.add(new Font("Judson Bold", "Judson-Bold.ttf"));
        fontsDialogText.add(new Font("Jura Regular", "Jura-Regular.ttf"));
        fontsDialogText.add(new Font("Kanit Light", "Kanit-Light.ttf"));
        fontsDialogText.add(new Font("Maitree Medium", "Maitree-Medium.ttf"));
        fontsDialogText.add(new Font("Montserrat Extra Light", "Montserrat-ExtraLight.ttf"));
        fontsDialogText.add(new Font("Old Standard Regular", "OldStandard-Regular.ttf"));
        fontsDialogText.add(new Font("Oswald Extra Light", "Oswald-ExtraLight.ttf"));
        fontsDialogText.add(new Font("Patrick Hand SC Regular", "PatrickHandSC-Regular.ttf"));
        fontsDialogText.add(new Font("Philosopher Regular", "Philosopher-Regular.ttf"));
        fontsDialogText.add(new Font("Podkova Regular", "Podkova-Regular.ttf"));
        fontsDialogText.add(new Font("Prata Regular", "Prata-Regular.ttf"));
        fontsDialogText.add(new Font("Pridi Extra Light", "Pridi-ExtraLight.ttf"));
        fontsDialogText.add(new Font("Taviraj Italic", "Taviraj-Italic.ttf"));
        fontsDialogText.add(new Font("Yeseva One Regular", "YesevaOne-Regular.ttf"));
        fontsDialogText.add(new Font("Gill Sans MT", "gill_sans_mt.ttf"));
        Collections.sort(fontsDialogText);
    }

    public ArrayList<Font> getAllFonts() {
        return fontsDialogText;
    }
}
