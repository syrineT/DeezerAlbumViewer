package com.syrine.executor;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

abstract public class PriorityAsyncTask<Params, Result> {
    private static final String TAG = PriorityAsyncTask.class.getSimpleName();

    private PriorityFutureTask mFutureTask;
    private boolean mCancelled;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    public static final Executor PRIORITY_SERIAL_EXECUTOR = new PrioritySerialExecutor();

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "PriorityAsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new PriorityBlockingQueue<>(128,new PriorityComparator());

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);


    public PriorityAsyncTask(int priority, Params... params) {
        ParamsRunnable<Params, Result> paramsRunnable = new ParamsRunnable<Params, Result>(params) {

            @Override
            public Result call() throws Exception {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                Result result = doInBackground(mParams);
                postResult(result);
                Binder.flushPendingCommands();
                return result;
            }
        };
        mFutureTask = new PriorityFutureTask(paramsRunnable, priority);
    }

    public void postResult(Result result) {
        Message message = getHandler().obtainMessage(0, new AsyncTaskResult<>(this, result));
        message.sendToTarget();
    }

    private InternalHandler getHandler() {
        return new InternalHandler();
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;
            result.mTask.finish(result.mData);

        }
    }

    private static class AsyncTaskResult<Result> {
        final PriorityAsyncTask mTask;
        final Result mData;

        AsyncTaskResult(PriorityAsyncTask task, Result result) {
            mTask = task;
            mData = result;
        }
    }

    private void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
    }


    protected void onCancelled(Result result) {
        //TODO not implemented
    }

    public boolean isCancelled() {
        return mCancelled;
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        mCancelled = true;
        return mFutureTask.cancel(mayInterruptIfRunning);
    }

    public PriorityAsyncTask<Params, Result> executeOnExecutor(Executor exec) {
        exec.execute(mFutureTask);
        return this;
    }

    abstract protected void onPostExecute(Result result);

    protected abstract Result doInBackground(Params... params);
}
