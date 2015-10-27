package com.syrine;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.syrine.cache.Cache;
import com.syrine.manager.ImageManager;
import com.syrine.ws.ResponseParser;
import com.syrine.ws.response.AlbumsResponse;
import com.syrine.ws.utils.DownloadHelper;

import java.io.InputStream;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, getColumnCount());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

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
    private int getColumnCount(){
        return getResources().getInteger(R.integer.columns_count);
    }

    class AlbumsAsyncTask extends AsyncTask<Void, Void, AlbumsResponse> {

        @Override
        protected AlbumsResponse doInBackground(Void... params) {
            InputStream inputStream = DownloadHelper.downloadData(BuildConfig.API_URL);
            return ResponseParser.readAlbumsResponse(inputStream);
        }

        @Override
        protected void onPostExecute(AlbumsResponse albumsResponse) {
            super.onPostExecute(albumsResponse);
            ImageManager imageManager = AlbumsApplication.get(HomeActivity.this).getImageManager();
            RecyclerView.Adapter adapter = new AlbumsAdapter(imageManager, albumsResponse.getData());
            mRecyclerView.setAdapter(adapter);
        }
    }
}
