package com.syrine;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.syrine.ws.model.Album;
import com.syrine.ws.utils.DownloadHelper;

import java.lang.ref.WeakReference;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
    private List<Album> mAlbums;
    private Context mContext;

    public AlbumsAdapter(List<Album> albums) {
        mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.album_cover_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (((AlbumsApplication) mContext.getApplicationContext()).getCache().getBitmap(mAlbums.get(position).getCoverBig()) != null) {
            holder.mIVCover.setImageBitmap(((AlbumsApplication) mContext.getApplicationContext()).getCache().getBitmap(mAlbums.get(position).getCoverBig()));
        } else {
            //make request to bind mIVCover
            new AsyncTaskImage(holder.mIVCover).execute(mAlbums.get(position).getCoverBig());
        }

    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIVCover;

        public ViewHolder(View itemView) {
            super(itemView);
            mIVCover = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

    class AsyncTaskImage extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> mImageViewReference;

        public AsyncTaskImage(ImageView imageView) {
            mImageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = DownloadHelper.downloadImage(params[0]);
            ((AlbumsApplication) mContext.getApplicationContext()).getCache().addBitmap(bitmap, params[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                final ImageView imageView = mImageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

    }
}
