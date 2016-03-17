package com.udacity.nanodegree.popularmovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    public final static String EXTRA_MSG1 = TAG + ".originalTitle";
    public final static String EXTRA_MSG2 = TAG + ".posterUrl";
    public final static String EXTRA_MSG3 = TAG + ".plot";
    public final static String EXTRA_MSG4 = TAG + ".userRating";
    public final static String EXTRA_MSG5 = TAG + ".releaseDate";
    private final static String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/popular";
    private ProgressDialog pDialog;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new getPopularMovies().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSortPopular) {
            Collections.sort(movies, Movie.moviePopularity);
            return setPopularMoviesGridView(movies);
        }
        else if(id == R.id.menuSortRating) {
            Collections.sort(movies, Movie.movieRating);
            return setPopularMoviesGridView(movies);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean setPopularMoviesGridView(final ArrayList<Movie> movies){
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        GridView gridview = (GridView) findViewById(R.id.movies_gridView);
        gridview.setAdapter(movieAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra(EXTRA_MSG1, movies.get(position).getOriginalTitle());
                intent.putExtra(EXTRA_MSG2, movies.get(position).getPosterUrl());
                intent.putExtra(EXTRA_MSG3, movies.get(position).getPlot());
                intent.putExtra(EXTRA_MSG4, movies.get(position).getUserRating().toString());
                intent.putExtra(EXTRA_MSG5, movies.get(position).getReleaseDate());
                startActivity(intent);
            }
        });
        return true;
    }

    private class getPopularMovies extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching Movies..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... args) {
            Map<String,String> parameters = new HashMap<>();
            parameters.put("api_key", getString(R.string.api_key));
            GenericRequestHandler jsonParser = new GenericRequestHandler();
            String jsonResponse = jsonParser.makeGenericRequest(POPULAR_MOVIES_URL, GenericRequestHandler.GET, parameters);
            if(jsonResponse != null)
            {
                Log.i(TAG, "List of Popular movies : " + jsonResponse);
                try {
                    JSONObject json = new JSONObject(jsonResponse);
                    JSONArray jsonArr = json.getJSONArray("results");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = (JSONObject) jsonArr.get(i);
                        Movie movie = new Movie(jsonObj.getLong("id"),jsonObj.getString("original_title"),jsonObj.getString("poster_path"),jsonObj.getString("overview"),jsonObj.getDouble("vote_average"),jsonObj.getString("release_date"),jsonObj.getDouble("popularity"));
                        movies.add(movie);
                        Log.i(TAG, "Movie : " + jsonObj.getString("title"));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException : ", e);
                }
            }
            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            setPopularMoviesGridView(result);
        }

    }
}
