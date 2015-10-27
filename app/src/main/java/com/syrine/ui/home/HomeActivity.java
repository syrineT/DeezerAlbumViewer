package com.syrine.ui.home;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.syrine.AlbumsApplication;
import com.syrine.BuildConfig;
import com.syrine.R;
import com.syrine.cache.Cache;
import com.syrine.image.ImageManager;
import com.syrine.ws.parser.AlbumsResponseParser;
import com.syrine.ws.response.AlbumsResponse;
import com.syrine.helper.DownloadHelper;

import java.io.InputStream;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);

        initToolbar();
        initRecyclerView();
        //Make request
        new AlbumsAsyncTask().execute();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, getColumnCount(getResources().getConfiguration()));
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLayoutManager.setSpanCount(getColumnCount(newConfig));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear) {
            Cache cache = AlbumsApplication.get(this).getCache();
            cache.clear();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getColumnCount(Configuration config) {
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            return getPortraitColumnCount();
        }
        return getLandscapeColumnCount();

    }

    private int getPortraitColumnCount() {
        return getResources().getInteger(R.integer.portrait_columns_count);
    }

    private int getLandscapeColumnCount() {
        return getResources().getInteger(R.integer.landscape_columns_count);
    }

    class AlbumsAsyncTask extends AsyncTask<Void, Void, AlbumsResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected AlbumsResponse doInBackground(Void... params) {
            InputStream inputStream = DownloadHelper.downloadData(BuildConfig.API_URL);
            return AlbumsResponseParser.readAlbumsResponse(inputStream);
        }

        @Override
        protected void onPostExecute(AlbumsResponse albumsResponse) {
            super.onPostExecute(albumsResponse);
            mProgressbar.setVisibility(View.GONE);

            ImageManager imageManager = AlbumsApplication.get(HomeActivity.this).getImageManager();
            RecyclerView.Adapter adapter = new AlbumsAdapter(imageManager, albumsResponse.getData());
            mRecyclerView.setAdapter(adapter);
        }
    }
}
