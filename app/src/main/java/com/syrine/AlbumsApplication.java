package com.syrine;

import android.app.Application;

import com.syrine.cache.Cache;
import com.syrine.cache.CacheImpl;

public class AlbumsApplication extends Application {
   private Cache mCache;

    @Override
    public void onCreate() {
        super.onCreate();
        mCache = new CacheImpl(this);
    }

    public Cache getCache(){
        return  mCache;
    }

}
