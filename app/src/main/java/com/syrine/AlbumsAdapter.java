package com.syrine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.syrine.manager.ImageManager;
import com.syrine.ws.model.Album;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
    private static final int HIGH_PRIORITY = 1;
    private static final int LOW_PRIORITY = 0;
    private final List<Album> mAlbums;
    private final ImageManager mImageManager;

    public AlbumsAdapter(ImageManager imageManager, List<Album> albums) {
        mAlbums = albums;
        mImageManager = imageManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_cover_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mImageManager.displayImage(mAlbums.get(position).getCoverBig(), holder.mIVCover, LOW_PRIORITY);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageManager.displayImage(mAlbums.get(position).getArtist().getPictureBig(), holder.mIVCover, HIGH_PRIORITY);

            }
        });
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
}
