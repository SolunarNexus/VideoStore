package cz.muni.fi.pv260.videostore;

public record Rental(Movie movie, int daysRented) {

    /**
     * Method calculates price for this rental. Price depends on movie type and rental period.
     *
     * @return rental price.
     */
    public double getRentalPrice() {
        return movie.getPriceOf(daysRented);
    }

    /**
     * Method calculates frequent renter points for this rental.
     *
     * @return Amount of frequent renter points.
     */
    public int getFrequenterPoints() {
        return movie.getFrequenterPoints(daysRented);
    }
}
