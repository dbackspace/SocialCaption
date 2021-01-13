package com.xlteam.socialcaption.external.utility;

public class Constant {
    public static final String LINK_GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=";
    public static final String LINK_MARKET_APP = "market://details?id=";
    public static final String LINK_MARKET_DEVELOPER = "market://search?q=pub:XL+team";
    public static final String LINK_GOOGLE_PLAY_DEVELOPER = "http://play.google.com/store/apps/developer?id=XL+team";
    public static final String EMAIL_TEAM = "developer.xlteam@gmail.com";

    public static final String EDITOR = "EDITOR";
    public static final String EDITOR_CAU_THOAI_SIZE = "EDITOR_CAU_THOAI_SIZE";

    public static final int TYPE_THA_THINH = 1;
    public static final int TYPE_CUOC_SONG = 3;
    public static final int TYPE_BAN_BE = 4;
    public static final int TYPE_GIA_DINH = 5;
    public static final int TYPE_KHAC = 6;

    public static class LoaderTaskType {
        private LoaderTaskType() {
        }

        public static final int LOAD_ALL = 0;
        public static final int LOAD_BY_CATEGORY_TYPE = 1;
        public static final int LOAD_BY_CATEGORY_TYPE_AND_SAVED = 2;
        public static final int SEARCH_BY_CONTENT = 3;
        public static final int LOAD_ALL_USER_CAPTION = 4;
    }

    public static class RepositoryType {
        private RepositoryType() {
        }

        public static final int COMMON_REPOSITORY = 0;
        public static final int USER_REPOSITORY = 1;
    }
}
