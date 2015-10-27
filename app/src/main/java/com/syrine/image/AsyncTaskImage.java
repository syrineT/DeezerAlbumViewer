package com.syrine.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.syrine.cache.Cache;
import com.syrine.executor.PriorityAsyncTask;
import com.syrine.helper.AnimationHelper;
import com.syrine.helper.DownloadHelper;

import java.lang.ref.WeakReference;

public class AsyncTaskImage extends PriorityAsyncTask<String, Bitmap> {
    private final WeakReference<ImageView> mImageViewReference;
    private Cache mCache;
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }


    public AsyncTaskImage(Cache cache, ImageView imageView, int priority, String url) {
        super(priority, url);
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
