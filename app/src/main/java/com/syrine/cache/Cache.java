package com.syrine.cache;

import android.graphics.Bitmap;

public interface Cache {

    void addBitmap(Bitmap bitmap, String url);

    Bitmap getBitmap(String url);

    void removeBitmap(String url);

    void clear();

}
