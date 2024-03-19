package cz.muni.fi.pv260.videostore;

import cz.muni.fi.pv260.videostore.movie.Movie;

public class Rental
{
    public Rental (Movie movie, int daysRented) {
        this.movie      = movie;
        this.daysRented = daysRented;
    }

    public int getDaysRented () {
        return daysRented;
    }

    public Movie getMovie () {
        return movie;
    }

    private Movie movie;
    private int daysRented;
}
