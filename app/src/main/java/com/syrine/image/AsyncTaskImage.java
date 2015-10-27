package com.syrine.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.syrine.cache.Cache;
import com.syrine.executor.PriorityAsyncTask;
import com.syrine.manager.ImageManager;
import com.syrine.ws.utils.AnimationHelper;
import com.syrine.ws.utils.DownloadHelper;
import com.syrine.ws.utils.Downloader;

import java.lang.ref.WeakReference;

public class AsyncTaskImage extends PriorityAsyncTask<String, Bitmap> {
    private final WeakReference<ImageView> mImageViewReference;
    private Cache mCache;
    private String mUrl;
    private final Downloader mDownloader;
    private int mPriority;

    public String getUrl() {
        return mUrl;
    }


    public AsyncTaskImage(Cache cache, Downloader downloader, ImageView imageView, int priority, String url) {
        super(priority, url);
        mDownloader = downloader;
        mPriority = priority;
        mCache = cache;
        mImageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        if (isCancelled()) {
            return null;
        }
        mUrl = params[0];
        Bitmap bitmap = mCache.getBitmap(mUrl);
        if (isCancelled()) {
            return null;
        }
        if (bitmap == null) {
            bitmap = DownloadHelper.downloadImage(mUrl);
            //bitmap = mDownloader.Download(mUrl, mPriority);
            mCache.addBitmap(bitmap, mUrl);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            return;
        }
        if (bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            final AsyncTaskImage bitmapWorkerTask = ImageManager.getCurrentTask(imageView);
            if (this == bitmapWorkerTask) {
                AnimationHelper.FadeInAnimation(imageView);
                imageView.setImageBitmap(bitmap);
            }
        }
    }


}
