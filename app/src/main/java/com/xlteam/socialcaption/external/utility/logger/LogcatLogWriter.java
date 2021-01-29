package com.xlteam.socialcaption.external.utility.logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LogcatLogWriter implements LogWriter {

    private static final String SOCIAL_CAPTION_TAG = "SocialCaption";

    @Override
    public void v(String msg) {
        android.util.Log.v(SOCIAL_CAPTION_TAG, msg);
    }

    @Override
    public void d(String msg) {
        android.util.Log.i(SOCIAL_CAPTION_TAG, msg);
    }

    @Override
    public void i(String msg) {
        android.util.Log.i(SOCIAL_CAPTION_TAG, msg);
    }

    @Override
    public void w(String msg) {
        android.util.Log.w(SOCIAL_CAPTION_TAG, msg);
    }

    @Override
    public void e(String msg) {
        android.util.Log.e(SOCIAL_CAPTION_TAG, msg);
    }

    @Override
    public void beginSection(String methodName) {
        TraceReflector.getInstance().beginSection(methodName); // SE_MIGRATION use reflection
    }

    @Override
    public void beginSectionAppLog(String methodName) {
        TraceReflector.getInstance().beginSection(methodName); // SE_MIGRATION use reflection
    }

    @Override
    public void endSection() {
        TraceReflector.getInstance().endSection(); // SE_MIGRATION use reflection
    }

    public static class TraceReflector {

        private static final long TRACE_TAG_ALWAYS = 1L;

        private static final String TRACE_CLASS_NAME = "android.os.Trace";

        private static volatile TraceReflector sInstance;

        private static Method sMethodTraceBegin = MethodReflector.getMethod(TRACE_CLASS_NAME,
                "traceBegin", long.class, String.class);

        private static Method sMethodTraceEnd = MethodReflector.getMethod(TRACE_CLASS_NAME,
                "traceEnd", long.class);

        public static TraceReflector getInstance() {
            if (sInstance == null) {
                synchronized (TraceReflector.class) {
                    if (sInstance == null) {
                        sInstance = new TraceReflector();
                    }
                }
            }
            return sInstance;
        }

        public void beginSection(String methodName) {
            try {
                sMethodTraceBegin.invoke(null, TRACE_TAG_ALWAYS, methodName);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        public void endSection() {
            try {
                sMethodTraceEnd.invoke(null, TRACE_TAG_ALWAYS);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
