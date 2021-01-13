package com.xlteam.socialcaption.external.utility;

import android.content.Context;

public class SharePreUtils {

    public static final String VERSION_DATABASE = "VERSION_DATABASE";
    public static final String VERSION_DATABASE_LOCAL = "VERSION_DATABASE";

    public static int getVersionLocalDatabase(Context context) {
        return PrefUtils.getInt(context, VERSION_DATABASE, VERSION_DATABASE_LOCAL, 0);
    }

    public static void setVersionLocalDatabase(Context context, int version) {
        PrefUtils.putInt(context, VERSION_DATABASE, VERSION_DATABASE_LOCAL, version);
    }
}
