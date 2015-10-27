package com.syrine.executor;

import java.util.concurrent.Callable;

abstract public class ParamsRunnable<Params, Result> implements Callable<Result> {
    Params[] mParams;

    public ParamsRunnable(Params... params) {
        mParams = params;
    }
}
