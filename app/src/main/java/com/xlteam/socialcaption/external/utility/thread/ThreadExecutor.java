package com.xlteam.socialcaption.external.utility.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;

public class ThreadExecutor {

    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    /**
     * Runs the transferred task in the UI thread after the currently running UI task is finished.
     * It means, the specified action is posted to the event queue of the UI thread.
     * Replace the usability below before refactoring.
     * <p>
     * - new Handler(Looper.getMainLooper()).post(new Runnable())
     *
     * @param action the action to run on the UI thread
     */
    public static void runOnMainThread(Runnable action) {
        runOnMainThread(action, false);
    }

    /**
     * Runs the specified action on the UI thread. If the current thread is the UI
     * thread, then the action is executed immediately. If the current thread is
     * not the UI thread, the action is posted to the event queue of the UI thread.
     * <p>
     * Same with below method.
     * - Activity.runOnUiThread(Runnable action)
     *- Những tác vụ cần thực thi ngay lập tức
     * @param action             the action to run on the UI thread
     * @param executeImmediately the factor that determines whether to execute the delivered task immediately
     */
    public static void runOnMainThread(Runnable action, boolean executeImmediately) {
        if (executeImmediately) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                action.run();
            } else {
                sMainHandler.post(action);
            }
        } else {
            sMainHandler.post(action);
        }
    }

    /**
     * Các tác vụ trực tiếp main thread như refresh list file, migration, update usage, check app updates,...
     * @param r
     * @param delay
     */
    public static void runOnMainThread(Runnable r, long delay) {
        sMainHandler.postDelayed(r, delay);
    }

    public static void runOnDatabaseThread(Runnable r) {
        DatabaseThreadHandler.getInstance().run(r);
    }

    /**
     * Các tác vụ thực hiện request trên database
     * @param r
     * @param <T>
     * @return
     */
    public static <T> T runOnDatabaseThread(Callable<T> r) {
        return DatabaseThreadHandler.getInstance().run(r);
    }

    public static <T> T runOnDatabaseThread(Callable<T> r, T defaultValue) {
        return DatabaseThreadHandler.getInstance().run(r, defaultValue);
    }

    /**
     * Các tác vụ xử lý cập nhật đồng thời
     * Ví dụ: file observer, update directly.
     * @param r
     */
    public static void runOnWorkThread(Runnable r) {
        new Thread(r).start();
    }
}
