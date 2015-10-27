package com.syrine.executor;

import android.os.AsyncTask;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class PrioritySerialExecutor implements Executor {

    final PriorityBlockingQueue<Runnable> mTasks = new PriorityBlockingQueue<>(128, new PriorityComparator());
    private Runnable mActive;
    private Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;

    public synchronized void execute(final Runnable r) {
        mTasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });

        if (mActive == null) {
            scheduleNext();
        }

    }

    protected synchronized void scheduleNext() {
        if ((mActive = mTasks.poll()) != null) {
            mExecutor.execute(mActive);
        }
    }

}
