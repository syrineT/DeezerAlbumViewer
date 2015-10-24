package com.syrine.cache;

import android.content.Context;
import android.graphics.Bitmap;

public class CacheImpl implements Cache {
    private static final String TAG = CacheImpl.class.getSimpleName();

    private FileCache mFileCache;
    private TempCache mTempCache;

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
        if (mTempCache.getBitmap(url) != null) {
            return mTempCache.getBitmap(url);
        } else if (mFileCache.getBitmap(url) != null) {
            Bitmap bitmap = mFileCache.getBitmap(url);
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
