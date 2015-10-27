package com.syrine;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.syrine.cache.Cache;
import com.syrine.cache.CacheImpl;
import com.syrine.manager.ImageManager;
import com.syrine.ws.utils.Downloader;

public class AlbumsApplication extends Application {
    private Cache mCache;
    private ImageManager mImageManager;
    private Downloader mDownloader;


    protected static AlbumsApplication get(Context context) {
        if (context instanceof Activity) {
            return (AlbumsApplication) ((Activity) context).getApplication();
        }
        return (AlbumsApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCache = new CacheImpl(this);
        mDownloader = new Downloader();
        mImageManager = new ImageManager(this, mCache, mDownloader);
    }

    public ImageManager getImageManager() {
        return mImageManager;
    }

    public Cache getCache() {
        return mCache;
    }

}
