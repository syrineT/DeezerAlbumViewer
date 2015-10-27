package com.syrine.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

public class AsyncDrawable extends BitmapDrawable {
    private final WeakReference<AsyncTaskImage> bitmapWorkerTaskReference;


    public AsyncDrawable(Resources res, Bitmap bitmap, AsyncTaskImage bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference = new WeakReference<>(bitmapWorkerTask);
    }

    public AsyncTaskImage getCurrentTask() {
        return bitmapWorkerTaskReference.get();
    }
}
