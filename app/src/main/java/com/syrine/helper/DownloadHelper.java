package com.syrine.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadHelper {
    private static final String TAG = DownloadHelper.class.getSimpleName();

    public static InputStream downloadData(String requestUrl) {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Exception has occurred " + e.getMessage());

        }
        return inputStream;

    }

    public static Bitmap downloadImage(String url) {
        return BitmapFactory.decodeStream(downloadData(url));
    }
}
