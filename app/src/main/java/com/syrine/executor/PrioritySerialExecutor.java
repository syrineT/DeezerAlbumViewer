package com.syrine.executor;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;

class PrioritySerialExecutor implements Executor {

    private final PriorityBlockingQueue<Runnable> mTasks = new PriorityBlockingQueue<>(128, new PriorityComparator());
    private Runnable mActive;
    private final Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;

    public synchronized void execute(@NonNull final Runnable r) {
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

    private synchronized void scheduleNext() {
        if ((mActive = mTasks.poll()) != null) {
            mExecutor.execute(mActive);
        }
    }

}
