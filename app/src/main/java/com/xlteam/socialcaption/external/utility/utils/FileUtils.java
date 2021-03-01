package com.xlteam.socialcaption.external.utility.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutorWithPool;

import java.io.File;
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
        public static final String DATA_FOLDER = "/SocialCaption";
        public static final String DATA_INTERNAL_PATH = getInternalStoragePath() + DATA_FOLDER;
        public static final String CACHE_INTERNAL_PATH = "/Android/data/com.xlteam.socialcaption/cache";
        public static final String CACHE_FOLDER = getInternalStoragePath() + CACHE_INTERNAL_PATH;

        public static String getInternalStoragePath() {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
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

    public static List<String> getListPathsIfFolderExist() {
        File folder = findExistingFolderSaveImage();
        if (folder != null) {
            String[] listFilePaths = folder.list();
            if (listFilePaths != null && listFilePaths.length > 0) {
                return Arrays.asList(listFilePaths);
            }
        }
        return null;
    }

    public static List<File> getListFilesIfFolderExist() {
        File folder = findExistingFolderSaveImage();
        if (folder != null) {
            String[] listFilePaths = folder.list();
            if (listFilePaths != null && listFilePaths.length > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return Arrays.stream(listFilePaths)
                            .filter(path -> !TextUtils.isEmpty(path))
                            .map(path -> new File(path))
                            .collect(Collectors.toList());
                }
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
                    Log.d(TAG, "name is " + fileName);
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
