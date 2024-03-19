package cz.muni.fi.pv260.videostore.movie;

public abstract class Movie {

    private final String title;

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract double getRentalPrice(int daysRented);

    public abstract boolean countsToFrequentPoints();
}
