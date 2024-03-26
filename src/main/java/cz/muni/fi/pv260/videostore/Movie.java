package cz.muni.fi.pv260.videostore;

public abstract class Movie
{
    public static final int CHILDRENS   = 2;
    public static final int REGULAR     = 0;
    public static final int NEW_RELEASE = 1;

    public Movie (String title, int priceCode) {
        this.title      = title;
        this.priceCode  = priceCode;
    }

    public abstract double getPriceOf(int daysRented);

    int getFrequenterPoints(int daysRented) {
        return 1;
    }

    public int getPriceCode () {
        return priceCode;
    }

    public String getTitle () {
        return title;
    }

    private String title;
    private int priceCode;
}
