package com.xlteam.socialcaption.external.utility;

public class Constant {
    public static final String LINK_GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=";
    public static final String LINK_MARKET_APP = "market://details?id=";
    public static final String LINK_MARKET_DEVELOPER = "market://search?q=pub:XL+team";
    public static final String LINK_GOOGLE_PLAY_DEVELOPER = "http://play.google.com/store/apps/developer?id=XL+team";
    public static final String EMAIL_TEAM = "developer.xlteam@gmail.com";

    public static class LoaderTaskType {
        private LoaderTaskType() {
        }

        public static final int LOAD_ALL = 0;
        public static final int LOAD_BY_CATEGORY_TYPE = 1;
        public static final int LOAD_BY_CATEGORY_TYPE_AND_SAVED = 2;
        public static final int SEARCH_BY_CONTENT = 3;
    }
}
