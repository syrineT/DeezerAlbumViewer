package com.syrine.cache;

import android.content.Context;
import android.graphics.Bitmap;

public class CacheImpl implements Cache {
    private static final String TAG = CacheImpl.class.getSimpleName();

    private final FileCache mFileCache;
    private final TempCache mTempCache;

    /**
     * Initialise cache
     * @param context of the application
     */
    public CacheImpl(Context context) {
        mFileCache = new FileCache(context);
        mTempCache = new TempCache();
    }

    /**
     * Add bitmap to cache
     * @param bitmap to cache
     * @param url of the bitmap
     */
    @Override
    public void addBitmap(Bitmap bitmap, String url) {
        mTempCache.addBitmap(bitmap, url);
        mFileCache.addBitmap(bitmap, url);
    }

    /**
     * Get the bitmap from the cache
     * @param url of the image
     * @return bitmap if it exists else return null
     */
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

    /**
     * Remove bitmap from cache
     * @param url of the image
     */
    @Override
    public void removeBitmap(String url) {
        mTempCache.removeBitmap(url);
        mFileCache.removeBitmap(url);
    }

    /**
     * Removes all bitmaps from all caches
     */
    @Override
    public void clear() {
        mTempCache.clear();
        mFileCache.clear();
    }
}
