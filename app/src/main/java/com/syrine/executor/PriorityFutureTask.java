package com.syrine.executor;

import android.os.Process;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PriorityFutureTask<R> extends FutureTask<R> implements Priority {
    private final int mPriority;


    public PriorityFutureTask(Callable<R> callable, int priority) {
        super(callable);
        mPriority = priority;
    }

    @Override
    public int getPriority() {
        return mPriority;
    }


}
