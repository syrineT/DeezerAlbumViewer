package com.syrine.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class TempCache implements Cache {
    private final LruCache<String, Bitmap> mMemoryCache;

    public TempCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public void addBitmap(Bitmap bitmap, String url) {
        if (getBitmap(url) == null) {
            mMemoryCache.put(url, bitmap);
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void removeBitmap(String url) {
        mMemoryCache.remove(url);
    }

    @Override
    public void clear() {
        mMemoryCache.evictAll();
    }
}
