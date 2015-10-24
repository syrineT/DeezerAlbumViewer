package com.syrine.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileCache implements Cache {

    private static final String TAG = FileCache.class.getSimpleName();
    private Context mContext;

    public FileCache(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void addBitmap(Bitmap bitmap, String url) {
        File directory = mContext.getDir("coversDir", Context.MODE_PRIVATE);
        File filePath = new File(directory, getFileName(url));
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        File file = new File(mContext.getDir("coversDir", Context.MODE_PRIVATE), getFileName(url));
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void removeBitmap(String url) {
        mContext.deleteFile(getFileName(url));
    }

    @Override
    public void clear() {
        for (File file : mContext.getFilesDir().listFiles()) {
            mContext.deleteFile(file.getName());
        }
    }

    //TODO method helper

    private String getFileName(String url) {
        List<String> pathSegments = Uri.parse(url).getPathSegments();
        String fileName = pathSegments.get(pathSegments.size() - 2);
        return fileName;

    }
}
