package com.syrine.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.syrine.R;
import com.syrine.cache.Cache;
import com.syrine.executor.PriorityAsyncTask;
import com.syrine.ws.utils.Downloader;

public class ImageManager {

    private final Cache mCache;
    private final Downloader mDownloader;
    private Bitmap mPlaceHolder;


    public ImageManager(Context context, Cache cache, Downloader downloader) {
        mCache = cache;
        mDownloader = downloader;
        mPlaceHolder = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder_500x500);

    }

    public void displayImage(final String url, ImageView imageView, int priority) {

        if (cancelPotentialWork(url, imageView)) {
            final AsyncTaskImage asyncTaskImage = new AsyncTaskImage(mCache, mDownloader, imageView, priority, url);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(imageView.getResources(), mPlaceHolder, asyncTaskImage);
            imageView.setImageDrawable(asyncDrawable);
            asyncTaskImage.executeOnExecutor(PriorityAsyncTask.PRIORITY_SERIAL_EXECUTOR);
        }
    }

    public static AsyncTaskImage getCurrentTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getCurrentTask();
            }
        }
        return null;
    }

    public boolean cancelPotentialWork(String newUrl, ImageView imageView) {
        final AsyncTaskImage currentTask = getCurrentTask(imageView);

        if (currentTask != null) {
            final String currentUrl = currentTask.getUrl();
            // If bitmapData is not yet set or it differs from the new data
            if (currentUrl == null || !currentUrl.equals(newUrl)) {
                // Cancel previous task
                currentTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

}
