package com.xlteam.socialcaption.external.datasource;

import java.util.ArrayList;

public class ImageUrlDataSource {
    private final ArrayList<String> urls = new ArrayList<>();

    private static class SingletonHelper {
        private static final ImageUrlDataSource INSTANCE = new ImageUrlDataSource();
    }

    public static ImageUrlDataSource getInstance() {
        return ImageUrlDataSource.SingletonHelper.INSTANCE;
    }

    private ImageUrlDataSource() {
        createDataForDialogText();
    }

    private void createDataForDialogText() {
        urls.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");
        urls.add("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg");
        urls.add("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg");
        urls.add("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg");
        urls.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");
        urls.add("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg");
        urls.add("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg");
        urls.add("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg");
        urls.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");
        urls.add("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg");
        urls.add("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg");
        urls.add("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg");
        urls.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");
        urls.add("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg");
        urls.add("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg");
        urls.add("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg");
        urls.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");
        urls.add("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg");
        urls.add("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg");
        urls.add("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg");
        urls.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");
        urls.add("https://imagevars.gulfnews.com/2020/03/11/Reader-picture_170c9c693f4_original-ratio.jpg");
        urls.add("https://image.shutterstock.com/image-photo/this-picture-taken-nami-islandsouth-260nw-347309699.jpg");
        urls.add("https://www.publicdomainpictures.net/pictures/270000/velka/sunset-picture.jpg");
    }

    public ArrayList<String> getAllImageUrl() {
        return urls;
    }
}
