package com.xlteam.socialcaption.external.utility.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutorWithPool {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadExecutorWithPool() {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE);
    }

    public void execute(Runnable fileOperation) {
        mThreadPoolExecutor.submit(fileOperation);
    }

    private static class ThreadExecutorHolder {
        private static final ThreadExecutorWithPool INSTANCE = new ThreadExecutorWithPool();
    }

    public static ThreadExecutorWithPool getInstance() {
        return ThreadExecutorHolder.INSTANCE;
    }

}
