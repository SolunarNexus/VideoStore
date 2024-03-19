package cz.muni.fi.pv260.videostore.movie;

import cz.muni.fi.pv260.videostore.movie.Movie;

public class NewReleaseMovie extends Movie {

    private static final double PRICE_PER_DAY = 3;

    public NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public double getRentalPrice(int daysRented) {
        return daysRented * PRICE_PER_DAY;
    }

    @Override
    public boolean countsToFrequentPoints() {
        return true;
    }
}
