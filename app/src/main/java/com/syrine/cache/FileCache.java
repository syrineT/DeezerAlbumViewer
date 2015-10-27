package com.syrine.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileCache implements Cache {

    private static final String TAG = FileCache.class.getSimpleName();
    private static final String COVERS_DIR = "coversDir";
    private final Context mContext;

    public FileCache(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void addBitmap(Bitmap bitmap, String url) {
        File directory = mContext.getDir(COVERS_DIR, Context.MODE_PRIVATE);
        File filePath = new File(directory, getFileName(url));
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        File file = new File(mContext.getDir(COVERS_DIR, Context.MODE_PRIVATE), getFileName(url));
        try {
            if (file.exists())
                return BitmapFactory.decodeStream(new FileInputStream(file));
            else return null;
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public void removeBitmap(String url) {
        mContext.deleteFile(getFileName(url));
    }

    @Override
    public void clear() {
        File filesDir = mContext.getDir(COVERS_DIR, Context.MODE_PRIVATE);
        for (File file : filesDir.listFiles()) {
            file.delete();
        }
        filesDir.delete();
    }

    //TODO method helper
    private String getFileName(String url) {
        List<String> pathSegments = Uri.parse(url).getPathSegments();
        return pathSegments.get(pathSegments.size() - 2);

    }
}
