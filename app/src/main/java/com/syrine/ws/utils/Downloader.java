package com.syrine.ws.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.ArrayDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Downloader {
    private final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
    private Runnable mActive;

    public Bitmap Download(final String url, Boolean priority) {
        FutureTask<Bitmap> fTask = buildFutureTask(url);
        Download(fTask, priority);
        try {
                return fTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized void Download(FutureTask<Bitmap> fTask, Boolean priority) {
        if (priority)
            mTasks.addLast(fTask);
        else
            mTasks.offer(fTask);

        if (mActive == null) {
            scheduleNext();
        }
    }

    private FutureTask<Bitmap> buildFutureTask(final String url) {
        Callable<Bitmap> callable = new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return DownloadHelper.downloadImage(url);
            }
        };
        FutureTask<Bitmap> fTask = new FutureTask<Bitmap>(callable) {
            @Override
            public void run() {
                try {
                    super.run();
                } finally {
                    scheduleNext();
                }
            }
        };

        return fTask;
    }

    private synchronized void scheduleNext() {
        if ((mActive = mTasks.poll()) != null) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(mActive);
        }
    }
}
