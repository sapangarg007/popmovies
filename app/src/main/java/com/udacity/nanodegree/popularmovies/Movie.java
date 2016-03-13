package com.udacity.nanodegree.popularmovies;

import java.util.Comparator;

/**
 * Created by sgarg on 3/12/2016.
 */
public class Movie {
    Long id;
    String originalTitle;
    String posterUrl;
    String plot;
    Double userRating;
    String releaseDate;
    Double popularity;

    public Movie(Long id, String originalTitle, String posterUrl, String plot, Double userRating, String releaseDate, Double popularity) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterUrl = posterUrl;
        this.plot = plot;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /*Comparator for sorting the list by popularity*/
    public static Comparator<Movie> moviePopularity = new Comparator<Movie>() {

        public int compare(Movie m1, Movie m2) {
            Double m1Popularity = m1.getPopularity();
            Double m2Popularity = m2.getPopularity();

            //descending order
            return Double.compare(m2Popularity, m1Popularity);
        }};

    /*Comparator for sorting the list by rating*/
    public static Comparator<Movie> movieRating = new Comparator<Movie>() {

        public int compare(Movie m1, Movie m2) {
            Double m1Rating = m1.getUserRating();
            Double m2Rating = m2.getUserRating();

            //descending order
            return Double.compare(m2Rating, m1Rating);

        }};
}
