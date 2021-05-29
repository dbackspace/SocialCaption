package com.xlteam.textonpicture.external.utility.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import timber.log.Timber;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static class FileWrapper extends File {

        public static FileWrapper createFile(@NonNull String path) {
            return new FileWrapper(path);
        }

        public FileWrapper(@NonNull String pathname) {
            super(pathname);
        }
    }

    public static class StoragePathUtils {
        public static final String DATA_FOLDER = "/TextOnPicture";
        public static String ROOT_PATH = "";

        public static void setRootPath(String path) {
            ROOT_PATH = path;
        }

        public static String getRootPath() {
            return ROOT_PATH;
        }
    }

    public static File findExistingFolderSaveImage() {
        String rootPath = StoragePathUtils.getRootPath() + StoragePathUtils.DATA_FOLDER;
        File root = FileWrapper.createFile(rootPath);
        return root.exists() ? root : root.mkdirs() ? root : null;
    }

    public static List<String> getListPathsIfFolderExist() {
        File folder = findExistingFolderSaveImage();
        Timber.e("getListPathsIfFolderExist: %s", folder.getPath());
        if (folder != null) {
            File[] listFilePaths = folder.listFiles();
            if (listFilePaths != null && listFilePaths.length > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return Arrays.stream(listFilePaths)
                            .map(File::getPath)
                            .collect(Collectors.toList());
                }
            }
        }
        return new ArrayList<>();
    }

    public static void deleteMultiImage(List<String> listPath, Context context) {
        // TODO: Apply Thread pool for delete multi image
        for (String path : listPath) {
            deleteSingleImage(path, context);
        }
    }

    public static void deleteSingleImage(String localPath, Context context) {
        if (!TextUtils.isEmpty(localPath)) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = context.getContentResolver();
            String url = MediaStore.Images.Media.DATA + "=?";
            int deleteRows = contentResolver.delete(uri, url, new String[]{localPath});
            Timber.v("path = " + localPath);
            Timber.v("deleteRows = " + deleteRows);
            if (deleteRows == 0) {
                /**
                 * When the image is generated without notification (inserted into) the media database,
                 * the image is not visible in the gallery, and the contentResolver.delete method will return 0.
                 * In this case, use the file.delete method. Delete Files
                 */
                File file = new File(localPath);
                if (file.exists()) {
                    boolean isDeleted = file.delete();
                    Timber.v("file local delete = " + isDeleted);
                }
            }
        }
    }
}
