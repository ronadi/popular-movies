package com.example.android.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView iv_poster = (ImageView) findViewById(R.id.iv_poster);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_release_date = (TextView) findViewById(R.id.tv_release_date);
        TextView tv_vote_average  = (TextView) findViewById(R.id.tv_vote_average);
        TextView tv_overview = (TextView) findViewById(R.id.tv_overview);
        Intent intentStartedActivity = getIntent();
        if(intentStartedActivity.hasExtra(getString(R.string.title))){
            tv_title.setText(intentStartedActivity.getStringExtra(getString(R.string.title)));
        }
        if(intentStartedActivity.hasExtra(getString(R.string.date))){
            tv_release_date.setText(intentStartedActivity.getStringExtra(getString(R.string.date)));
        }
        if(intentStartedActivity.hasExtra(getString(R.string.overview))){
            tv_overview.setText(intentStartedActivity.getStringExtra(getString(R.string.overview)));
        }
        if(intentStartedActivity.hasExtra(getString(R.string.vote))){
            tv_vote_average.setText(String.valueOf(intentStartedActivity.getDoubleExtra(getString(R.string.vote),0)));
        }
        if(intentStartedActivity.hasExtra(getString(R.string.poster))){
            Uri uri = Uri.parse(intentStartedActivity.getStringExtra(getString(R.string.poster)));
            Picasso.with(this)
                .load(uri)
                .into(iv_poster);
        }

    }
}
