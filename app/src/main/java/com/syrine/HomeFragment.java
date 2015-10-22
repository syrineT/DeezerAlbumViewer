package com.syrine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syrine.ws.ResponseParser;
import com.syrine.ws.response.AlbumsResponse;
import com.syrine.ws.utils.DownloadUtils;

import java.io.InputStream;

public class HomeFragment extends Fragment {
    private static final int COLOUMN_COUNT = 4;
    public static String API_URL = "http://api.deezer.com/2.0/user/2529/albums";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), COLOUMN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Make request
        new AlbumsAsyncTask().execute();
    }

    private class AlbumsAsyncTask extends AsyncTask<Void, Void, AlbumsResponse> {
        @Override
        protected AlbumsResponse doInBackground(Void... params) {
            InputStream inputStream = DownloadUtils.downloadData(API_URL);
            return ResponseParser.readAlbumsResponse(inputStream);


        }

        @Override
        protected void onPostExecute(AlbumsResponse albumsResponse) {
            super.onPostExecute(albumsResponse);
            mAdapter = new AlbumsAdapter(albumsResponse.getData());
            mRecyclerView.setAdapter(mAdapter);


        }
    }
}
