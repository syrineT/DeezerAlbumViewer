package com.syrine.executor;

import java.util.concurrent.Callable;

abstract class ParamsRunnable<Params, Result> implements Callable<Result> {
    final Params[] mParams;

    public ParamsRunnable(Params... params) {
        mParams = params;
    }
}
