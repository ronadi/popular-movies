package com.example.android.movies.utilities;
/*
 * Utility functions to handle Movie JSON data.
 */

import com.example.android.movies.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenMovieJsonUtils {


    public static MovieModel[] getMovieFromJson(String movieJsonStr) throws JSONException{

        final String OWM_LIST = "results";
        final String URL_IMAGE = "http://image.tmdb.org/t/p/w185/";

        MovieModel[] parsedMovieData;
        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(OWM_LIST);

        parsedMovieData = new MovieModel[movieArray.length()];
        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            String url = URL_IMAGE+movie.getString("poster_path");
            parsedMovieData[i] = new MovieModel(movie.getString("original_title"), url, movie.getString("overview"),movie.getString("release_date"),movie.getDouble("vote_average"));
        }
        return parsedMovieData;
    }
}
