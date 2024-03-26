package cz.muni.fi.pv260.videostore;

public abstract class Movie {

    public Movie (String title) {
        this.title = title;
    }

    public abstract double getPriceOf(int daysRented);

    int getFrequenterPoints(int daysRented) {
        return 1;
    }

    public String getTitle () {
        return title;
    }

    private String title;
}
