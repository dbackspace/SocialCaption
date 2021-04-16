package com.xlteam.textonpicture.external.datasource;


import com.xlteam.textonpicture.model.Font;

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
        fonts.add(new Font("Dancing Script", "dancingscript_bold.ttf"));
        fonts.add(new Font("Quicksand", "quicksand_medium.ttf"));
        fonts.add(new Font("Varela", "varela_regular.ttf"));
        fonts.add(new Font("UTM Azuki", "utm_azuki.ttf"));
        fonts.add(new Font("Arima Madurai", "ArimaMadurai-Thin.ttf"));
        fonts.add(new Font("Athiti", "Athiti-Regular.ttf"));
        fonts.add(new Font("Comfortaa", "Comfortaa-Regular.ttf"));
        fonts.add(new Font("David Libre", "DavidLibre-Regular.ttf"));
        fonts.add(new Font("Farsan", "Farsan-Regular.ttf"));
        fonts.add(new Font("Judson", "Judson-Bold.ttf"));
        fonts.add(new Font("Jura", "Jura-Regular.ttf"));
        fonts.add(new Font("Kanit", "Kanit-Light.ttf"));
        fonts.add(new Font("Maitree", "Maitree-Medium.ttf"));
        fonts.add(new Font("Montserrat", "Montserrat-ExtraLight.ttf"));
        fonts.add(new Font("Old Standard", "OldStandard-Regular.ttf"));
        fonts.add(new Font("Oswald Extra", "Oswald-ExtraLight.ttf"));
        fonts.add(new Font("Patrick Hand", "PatrickHandSC-Regular.ttf"));
        fonts.add(new Font("Philosopher", "Philosopher-Regular.ttf"));
        fonts.add(new Font("Podkova", "Podkova-Regular.ttf"));
        fonts.add(new Font("Prata", "Prata-Regular.ttf"));
        fonts.add(new Font("Pridi", "Pridi-ExtraLight.ttf"));
        fonts.add(new Font("Taviraj", "Taviraj-Italic.ttf"));
        fonts.add(new Font("Yeseva One", "YesevaOne-Regular.ttf"));
        fonts.add(new Font("Gill Sans", "gill_sans_mt.ttf"));
        
        // add new font by Đức (16/04/2021)
        fonts.add(new Font("Baloo Paaji", "Baloo_Paaji_Regular.ttf"));
        fonts.add(new Font("Fiolex", "fiolex_girl.ttf"));
        fonts.add(new Font("Buttermilk", "HLT_Buttermilk.ttf"));
        fonts.add(new Font("Smoothy Cursive", "iCiel_Smoothy_Cursive.ttf"));
        fonts.add(new Font("Lobster", "Lobster_Regular.ttf"));
        fonts.add(new Font("Open Sans", "Open_Sans_Regular.ttf"));
        fonts.add(new Font("Domaine Display", "TUV_Domaine_Display.ttf"));
        fonts.add(new Font("Noh Carbone", "TUV_Noh_Carbone.ttf"));
        fonts.add(new Font("Aquarelle", "UTM_Aquarelle.ttf"));
        fonts.add(new Font("Edwardian", "UTM_Edwardian_Bold.ttf"));
        fonts.add(new Font("Fleur", "UTM_Fleur.ttf"));
        fonts.add(new Font("French Vanilla", "UTM_French_Vanilla.ttf"));
        fonts.add(new Font("Cider Script", "UVF_Cider_Script.ttf"));
        fonts.add(new Font("Cupid de Locke", "UVF_Cupid_de_Locke.ttf"));
        fonts.add(new Font("Didot Std", "UVF_Didot_LT_Std_Italic.ttf"));
        Collections.sort(fonts);
    }

    public ArrayList<Font> getAllFonts() {
        return fonts;
    }
}
