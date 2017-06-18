package com.example.android.movies;
/*
 * Created by ronadix@gmail.com on 6/18/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {

    final String original_title;
    final String poster_path;
    final String overview;
    final String release_date;
    final Double vote_average;

    public final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel parcel) {
            return new MovieModel(parcel);
        }

        @Override
        public MovieModel[] newArray(int i) {
            return new MovieModel[i];
        }

    };
    public MovieModel(String originalTitle, String posterPath, String overView, String releaseDate, Double voteAverage)
    {
        this.original_title = originalTitle;
        this.poster_path = posterPath;
        this.overview = overView;
        this.release_date = releaseDate;
        this.vote_average = voteAverage;
    }

    private MovieModel(Parcel in){
        original_title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
    }



    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(overview);
        parcel.writeDouble(vote_average);
    }




    @Override
    public int describeContents() {
        return 0;
    }
}
