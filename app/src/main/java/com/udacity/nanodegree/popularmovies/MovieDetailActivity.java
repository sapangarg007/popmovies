package com.udacity.nanodegree.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private final static String TAG = "MovieDetailActivity";
    private final static String MOVIES_POSTER_URL = "http://image.tmdb.org/t/p/w342/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        init(intent);
    }

    private void init(Intent intent)
    {
        Context context = getApplicationContext();
        TextView tv_movie_title = (TextView)findViewById(R.id.movie_heading);
        tv_movie_title.setText(intent.getStringExtra(MainActivity.EXTRA_MSG1));
        ImageView movie_poster = (ImageView)findViewById(R.id.movie_poster);
        Picasso.with(context).load(MOVIES_POSTER_URL + intent.getStringExtra(MainActivity.EXTRA_MSG2)).into(movie_poster);
        TextView tv_movie_release_date = (TextView)findViewById(R.id.movie_release_date);
        tv_movie_release_date.setText(intent.getStringExtra(MainActivity.EXTRA_MSG5));
        TextView tv_movie_rating = (TextView)findViewById(R.id.movie_rating);
        tv_movie_rating.setText(intent.getStringExtra(MainActivity.EXTRA_MSG4) + " / 10");
        TextView tv_movie_plot = (TextView)findViewById(R.id.movie_plot);
        tv_movie_plot.setText(intent.getStringExtra(MainActivity.EXTRA_MSG3));

    }

}
