package com.xlteam.socialcaption.external.utility.logger;

import java.util.Locale;

public class Log {

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    private static final String USER_ACTION_TAG = "UA";

    private static final int MAX_INDEX = 9999;

    private static Log sInstance;

    private static int sLogIndex = 0;

    private final LogWriter mLogWriter;

    private static final String SLUGGISH_TAG = "VerificationLog";

    private static final String TRACE_APP_LOG = "APP_";

    public enum SluggishType {
        OnCreate,
        OnResume,
        Execute
    }

    public static void init(LogWriter logWriter) {
        sInstance = new Log(logWriter);
    }

    private Log(LogWriter logWriter) {
        mLogWriter = logWriter;
    }

    private static void log(int logLevel, String msg) {
        if (sInstance.mLogWriter == null) {
            throw new IllegalStateException("Log class is not initialized");
        }
        switch (logLevel) {
            case VERBOSE:
                sInstance.mLogWriter.v(msg);
                break;
            case DEBUG:
                sInstance.mLogWriter.d(msg);
                break;
            case INFO:
                sInstance.mLogWriter.i(msg);
                break;
            case WARN:
                sInstance.mLogWriter.w(msg);
                break;
            case ERROR:
                sInstance.mLogWriter.e(msg);
                break;
        }
    }

    public static void v(String tag, String msg) {
        log(VERBOSE, getMsg(tag, msg));
    }

    public static void v(Object o, String msg) {
        log(VERBOSE, getMsg(o.getClass().getSimpleName(), msg));
    }

    public static void d(String tag, String msg) {
        log(DEBUG, getMsg(tag, msg));
    }

    public static void d(Object o, String msg) {
        log(DEBUG, getMsg(o.getClass().getSimpleName(), msg));
    }

    public static void w(String tag, String msg) {
        log(WARN, getMsg(tag, msg));
    }

    public static void w(Object o, String msg) {
        log(WARN, getMsg(o.getClass().getSimpleName(), msg));
    }

    public static void i(String tag, String msg) {
        log(INFO, getMsg(tag, msg));
    }

    public static void i(Object o, String msg) {
        log(INFO, getMsg(o.getClass().getSimpleName(), msg));
    }

    public static void e(String tag, String msg) {
        log(ERROR, getMsg(tag, msg));
    }

    public static void e(Object o, String msg) {
        log(ERROR, getMsg(o.getClass().getSimpleName(), msg));
    }

    public static void a(Object o, String msg) {
        log(INFO, getMsg(o.getClass().getSimpleName(), msg, true));
    }

    public static void beginSection(String methodName) {
        sInstance.mLogWriter.beginSection(methodName);
    }

    public static void beginSectionAppLog(String methodName) {
        sInstance.mLogWriter.beginSection(TRACE_APP_LOG + methodName);
    }

    public static void endSection() {
        sInstance.mLogWriter.endSection();
    }

    private static String getMsg(String tag, String msg) {
        StringBuilder sb = new StringBuilder();
        if (tag != null) {
            sb.append(String.format(Locale.ENGLISH, "[%04d/%-20s] ", getLogIndex(), tag));
        } else {
            sb.append(String.format(Locale.ENGLISH, "[%04d] ", getLogIndex()));
        }
        sb.append(msg);
        return sb.toString();
    }

    private static int getLogIndex() {
        sLogIndex++;
        if (sLogIndex > MAX_INDEX) {
            sLogIndex = 0;
        }
        return sLogIndex;
    }

    private static String getMsg(String tag, String msg, boolean isUserAction) {
        StringBuilder sb = new StringBuilder();
        if (tag != null) {
            if (isUserAction) {
                sb.append(String.format(Locale.getDefault(), "[%04d/%-20s/%s] ", getLogIndex(), tag, USER_ACTION_TAG));
            } else {
                sb.append(String.format(Locale.getDefault(), "[%04d/%-20s] ", getLogIndex(), tag));
            }
        } else {
            sb.append(String.format(Locale.getDefault(), "[%04d] ", getLogIndex()));
        }
        sb.append(msg);
        return sb.toString();
    }

    public static void sluggish(SluggishType type) {
        String msg = null;
        switch (type) {
            case OnCreate:
                msg = "onCreate";
                break;
            case OnResume:
                msg = "onResume";
                break;
            case Execute:
                msg = "Executed";
                break;
        }
        try {
            android.util.Log.i(SLUGGISH_TAG, msg);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }
    }
}
