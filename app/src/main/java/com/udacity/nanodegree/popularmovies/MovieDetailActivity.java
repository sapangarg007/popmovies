package com.udacity.nanodegree.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailActivity extends AppCompatActivity {
    private final static String TAG = "MovieDetailActivity";
    private final static String MOVIES_POSTER_URL = "http://image.tmdb.org/t/p/w342/";
    private final static String MOVIE_TRAILERS_URL = "http://api.themoviedb.org/3/movie/{id}/videos";
    private final static String MOVIE_REVIEWS_URL = "http://api.themoviedb.org/3/movie/{id}/reviews";
    private String MOVIE_TRAILERS_URL_EXPANDED ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Long movieId = intent.getLongExtra(MainActivity.EXTRA_MSG6, 1);
        MOVIE_TRAILERS_URL_EXPANDED = MOVIE_TRAILERS_URL.replace("{id}", movieId.toString());
        new getMovieTrailers().execute();
    }

    private void init(Intent intent, String youtube_link)
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
        TextView tv_movie_trailer = (TextView)findViewById(R.id.movie_trailer);
        tv_movie_trailer.setText(Html.fromHtml("<a href=\"" + youtube_link + "\">" + "Watch Trailer" + "</a>"));
        tv_movie_trailer.setMovementMethod(LinkMovementMethod.getInstance());
        TextView tv_movie_plot = (TextView)findViewById(R.id.movie_plot);
        tv_movie_plot.setText(intent.getStringExtra(MainActivity.EXTRA_MSG3));

    }

    private class getMovieTrailers extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... args) {
            Map<String,String> parameters = new HashMap<>();
            parameters.put("api_key", getString(R.string.api_key));
            GenericRequestHandler jsonParser = new GenericRequestHandler();
            String jsonResponse = jsonParser.makeGenericRequest(MOVIE_TRAILERS_URL_EXPANDED, GenericRequestHandler.GET, parameters);
            String youtube_trailer_link = "";
            if(jsonResponse != null)
            {
                Log.i(TAG, "List of Trailers : " + jsonResponse);
                try {
                    JSONObject json = new JSONObject(jsonResponse);
                    JSONArray jsonArr = json.getJSONArray("results");
                    JSONObject jsonObj = (JSONObject) jsonArr.get(0);
                    youtube_trailer_link = "https://www.youtube.com/watch?v=" + jsonObj.getString("key");
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException : ", e);
                }
            }
            return youtube_trailer_link;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(TAG, result);
            Intent intent = getIntent();
            init(intent, result);
        }

    }

}
