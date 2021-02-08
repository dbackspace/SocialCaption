package com.xlteam.socialcaption.external.datasource;

import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.model.UrlPicture;

import java.util.ArrayList;

public class UrlPictureDataSource {
    private final ArrayList<UrlPicture> urls = new ArrayList<>();

    private static class SingletonHelper {
        private static final UrlPictureDataSource INSTANCE = new UrlPictureDataSource();
    }

    public static UrlPictureDataSource getInstance() {
        return UrlPictureDataSource.SingletonHelper.INSTANCE;
    }

    private UrlPictureDataSource() {
        createData();
    }

    private void createData() {
        urls.add(new UrlPicture("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", Constant.TYPE_TAM_TRANG));
        urls.add(new UrlPicture("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg", Constant.TYPE_TINH_YEU));
        urls.add(new UrlPicture("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", Constant.TYPE_THIEN_NHIEN));
        urls.add(new UrlPicture("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg", Constant.TYPE_DEN_TRANG));
        urls.add(new UrlPicture("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg", Constant.TYPE_DEN_TRANG));
    }

    public ArrayList<UrlPicture> getAllImageUrl() {
        return urls;
    }
}
