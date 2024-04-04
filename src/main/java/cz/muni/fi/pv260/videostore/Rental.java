package cz.muni.fi.pv260.videostore;

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

    /**
     * Method calculates price for this rental. Price depends on movie type and rental period.
     * @return rental price.
     */
    public double getRentalPrice() {
        return movie.getPriceOf(daysRented);
    }

    private Movie movie;
    private int daysRented;
}
