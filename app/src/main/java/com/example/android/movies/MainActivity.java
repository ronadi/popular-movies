package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.utilities.NetworkUtils;
import com.example.android.movies.utilities.OpenMovieJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        int NumberGrid = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NumberGrid);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(adapter);
        if(savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.movieListSave))) {
            if(isOnlineConnected()){
                loadMovieData(1);
            }else {
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
                showErrorMessage();
            }

        }else {
            MovieModel[] movieList = (MovieModel[]) savedInstanceState.getParcelableArray(getString(R.string.movieListSave));
            adapter.setMovieData(movieList);
        }

     }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(getString(R.string.movieListSave), adapter.getMovieData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(MovieModel movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.title),movie.original_title);
        intent.putExtra(getString(R.string.poster),movie.poster_path);
        intent.putExtra(getString(R.string.date),movie.release_date);
        intent.putExtra(getString(R.string.overview),movie.overview);
        intent.putExtra(getString(R.string.vote),movie.vote_average);
        startActivity(intent);
    }

    private void loadMovieData(int flag) {
        showMovieDataView();
        String params = (flag == 1) ?  NetworkUtils.POPULAR : NetworkUtils.TOP_RATE;
        new FetchMoviesTask().execute(params);
    }

    private void showMovieDataView(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, MovieModel[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieModel[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String type = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(type);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                return OpenMovieJsonUtils.getMovieFromJson(jsonMovieResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieModel[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();

                adapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    private boolean isOnlineConnected() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_by_popular) {
            if(isOnlineConnected()){
                loadMovieData(1);
            }else {
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
                //showErrorMessage();
            }
            return true;
        }else if (id == R.id.action_sort_by_top_rate) {
            if(isOnlineConnected()){
                loadMovieData(2);
            }else {
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
                //showErrorMessage();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
