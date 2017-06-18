package com.example.android.movies;
/*
 * Created by ronadix@gmail.com on 6/18/17.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private MovieModel[] mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;
    MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    interface MovieAdapterOnClickHandler {
        void onClick(MovieModel movie);
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        MovieModel movie = mMovieData[position];
        Uri uri = Uri.parse(movie.poster_path);
        Context context = holder.mIVPoster.getContext();
        Picasso.with(context)
            .load(uri)
            .into(holder.mIVPoster);

    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mIVPoster;
        MovieAdapterViewHolder(View view) {
            super(view);
            mIVPoster = view.findViewById(R.id.iv_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieModel mMovieSingleData = mMovieData[adapterPosition];
            mClickHandler.onClick(mMovieSingleData);
        }
    }

    void setMovieData(MovieModel[] movieData){
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    MovieModel[] getMovieData(){
        return mMovieData;
    }
}
