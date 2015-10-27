package com.syrine.cache;

import android.content.Context;
import android.graphics.Bitmap;

public class CacheImpl implements Cache {
    private static final String TAG = CacheImpl.class.getSimpleName();

    private final FileCache mFileCache;
    private final TempCache mTempCache;

    public CacheImpl(Context context) {
        mFileCache = new FileCache(context);
        mTempCache = new TempCache();
    }

    @Override
    public void addBitmap(Bitmap bitmap, String url) {
        mTempCache.addBitmap(bitmap, url);
        mFileCache.addBitmap(bitmap, url);
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = mTempCache.getBitmap(url);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = mFileCache.getBitmap(url);
        if (bitmap != null) {
            mTempCache.addBitmap(bitmap, url);
            return bitmap;
        }
        return null;
    }

    @Override
    public void removeBitmap(String url) {
        mTempCache.removeBitmap(url);
        mFileCache.removeBitmap(url);
    }

    @Override
    public void clear() {
        mTempCache.clear();
        mFileCache.clear();
    }
}
