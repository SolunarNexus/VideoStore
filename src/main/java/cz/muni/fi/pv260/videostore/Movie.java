package cz.muni.fi.pv260.videostore;

public abstract class Movie {
    private final String title;

    public Movie (String title) {
        this.title = title;
    }

    /**
     * Method calculates price for renting movie for specific number of days
     * @param daysRented int number of days movie was rented for
     * @return price for the rent period
     */
    abstract double getPriceOf(int daysRented);

    int getFrequenterPoints(int daysRented) {
        return 1;
    }

    public String getTitle () {
        return title;
    }
}
