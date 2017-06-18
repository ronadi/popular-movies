package com.example.android.movies.utilities;
/*
 * Created by ronadix@gmail.com on 6/18/17.
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String URL = "https://api.themoviedb.org/3/movie/";

    //add your api key movie
    private static final String API_KEY = "<<API_KEY>>";

    public static final String POPULAR = "popular";
    public static final String TOP_RATE = "top_rated";
    private static final String PARAMS = "api_key";

    public static java.net.URL buildUrl(String query) {
        String base = URL+query;
        Uri builtUri = Uri.parse(base).buildUpon()
            .appendQueryParameter(PARAMS, API_KEY)
            .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(java.net.URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
