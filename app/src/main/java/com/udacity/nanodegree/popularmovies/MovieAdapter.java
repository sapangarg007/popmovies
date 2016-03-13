package com.udacity.nanodegree.popularmovies;

/**
 * Created by sgarg on 3/12/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;
    private final static String POSTER_MOVIES_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get cellView from inflater
        View moviesGridView = inflater.inflate(R.layout.content_main_grid, parent, false);

        // 3. Get the text and image views from the cellView
        ImageView moviesGridImage = (ImageView) moviesGridView.findViewById(R.id.movies_gridImage);

        // 4. Set the text and image
        Picasso.with(context).load(POSTER_MOVIES_URL + movies.get(position).getPosterUrl()).into(moviesGridImage);

        // 5. return rowView
        return moviesGridView;
    }
}

