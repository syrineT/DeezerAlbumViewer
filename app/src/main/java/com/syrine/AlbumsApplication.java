package com.syrine;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.syrine.cache.Cache;
import com.syrine.cache.CacheImpl;
import com.syrine.image.ImageManager;

public class AlbumsApplication extends Application {
    private Cache mCache;
    private ImageManager mImageManager;


    public static AlbumsApplication get(Context context) {
        if (context instanceof Activity) {
            return (AlbumsApplication) ((Activity) context).getApplication();
        }
        return (AlbumsApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCache = new CacheImpl(this);
        mImageManager = new ImageManager(this, mCache);
    }

    public ImageManager getImageManager() {
        return mImageManager;
    }

    public Cache getCache() {
        return mCache;
    }

}
