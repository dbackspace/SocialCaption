package com.xlteam.socialcaption.external.utility;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.xlteam.socialcaption.external.utility.logger.Log;

import java.io.File;

import static com.xlteam.socialcaption.external.utility.Constant.CATEGORIES;
import static com.xlteam.socialcaption.external.utility.Constant.COLORS;
import static com.xlteam.socialcaption.external.utility.Constant.FONTS;
import static com.xlteam.socialcaption.external.utility.Constant.MUSICS;
import static com.xlteam.socialcaption.external.utility.Constant.PHOTOS;

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

    public static class PhotoLoader {
        private static final SparseArray<Bitmap> photoLoaderList = new SparseArray<>();

        public static void setPhotoLoaded(int position, Bitmap bitmap) {
            photoLoaderList.append(position, bitmap);
        }

        public static Bitmap getPhotoLoaded(int position) {
            return photoLoaderList.get(position);
        }
    }

    public static class StoragePathUtils {
        public static final String DATA_FOLDER = "/SocialCaption";
        public static final String DATA_INTERNAL_PATH = getInternalStoragePath() + DATA_FOLDER;
        public static final String CACHE_INTERNAL_PATH = "/Android/data/com.xlteam.socialcaption/cache";
        public static final String CACHE_FOLDER = getInternalStoragePath() + CACHE_INTERNAL_PATH;
        public static final String EDITOR_PHOTOS = "/Editor Photos";
        public static final String EDITOR_FONTS = "/Editor Fonts";
        public static final String EDITOR_COLORS = "/Editor Colors";
        public static final String EDITOR_MUSICS = "/Editor Musics";
        public static final String EDITOR_CATEGORIES = "/Editor Categories";

        public static String getInternalStoragePath() {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        public static String getEditorFolderPath(int type) {
            String folderPath = "";
            switch (type) {
                case PHOTOS:
                    folderPath += EDITOR_PHOTOS;
                    break;
                case FONTS:
                    folderPath += EDITOR_FONTS;
                    break;
                case COLORS:
                    folderPath += EDITOR_COLORS;
                    break;
                case MUSICS:
                    folderPath += EDITOR_MUSICS;
                    break;
                case CATEGORIES:
                    folderPath += EDITOR_CATEGORIES;
                    break;
            }
            return folderPath;
        }

        public static String getCacheFolderPath(Context context) {
            // Điều kiện này dành cho trường hợp R OS sắp phát hành.
            // Trên R OS, quyền truy cập file vào thư mục Android/data bị hạn chế.
            // Solution: sử dụng một đường dẫn cache khác
            if (Build.VERSION.SDK_INT > 29) {
                return context.getCacheDir().getAbsolutePath();
            }
            return CACHE_FOLDER;
        }

        public static String getDataInternalPath() {
            return DATA_INTERNAL_PATH;
        }
    }

    public static File findExistingFolderSaveImage() {
        String rootPath = StoragePathUtils.getInternalStoragePath();
        File root = FileWrapper.createFile(rootPath);
        if (root.exists()) {
            String internalRootPath = StoragePathUtils.getDataInternalPath();
            File internalRoot = FileWrapper.createFile(internalRootPath);
            if (!internalRoot.exists()) {
                return (internalRoot.mkdirs()) ? internalRoot : null;
            } else return internalRoot;
        }
        return null;
    }

    public static File findExistingFolderRoot(Context context, String folderPath) {
        String rootPath = StoragePathUtils.getInternalStoragePath();
        File root = FileWrapper.createFile(rootPath);
        if (root.exists()) {
            String internalRootPath = StoragePathUtils.getCacheFolderPath(context);
            File internalRoot = FileWrapper.createFile(internalRootPath);
            if (internalRoot.exists()) {
                File file = FileWrapper.createFile(folderPath);
                if (file.exists()) {
                    return file;
                }
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static File getListFileIfFolderExist(Context context, String folderPath) {
        File folder = findExistingFolderRoot(context, folderPath);
        if (folder != null && folder.list() != null) {
            if (folder.list().length > 0) {
                return new File(folder.list()[0]);
            }
        }
        return null;
    }

    public static String getFileNameFromUri(Context context, Uri uri) {
        String fileName = "";
        if ("file".equals(uri.getScheme())) {
            fileName = uri.getLastPathSegment();
        } else {
            try (Cursor cursor = context.getContentResolver().query(uri, new String[]{
                    MediaStore.Images.ImageColumns.DISPLAY_NAME
            }, null, null, null)) {
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                    Log.d("duc.nh3", "name is " + fileName);
                }
            }
        }
        return fileName;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String realPathUri = "";
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                realPathUri = cursor.getString(column_index);
            }
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return realPathUri;
    }
}
