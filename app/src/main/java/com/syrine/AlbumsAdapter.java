package com.syrine;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.syrine.ws.model.Album;
import com.syrine.ws.utils.DownloadUtils;

import java.util.List;

/**
 * Created by syrinetrabelsi on 22/10/2015.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
    private List<Album> albums;

    public AlbumsAdapter(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_cover_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //make request to bind mIVCover
        new AsyncTaskImage(holder.mIVCover, albums.get(position).getCoverBig()).execute();
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIVCover;

        public ViewHolder(View itemView) {
            super(itemView);
            mIVCover = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

    private class AsyncTaskImage extends AsyncTask<Void, Void, Bitmap> {
        String mUrl;
        ImageView mImageView;

        public AsyncTaskImage(ImageView imageView, String url) {
            mImageView = imageView;
            mUrl = url;

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return DownloadUtils.downloadImage(mUrl);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);

        }

    }
}
