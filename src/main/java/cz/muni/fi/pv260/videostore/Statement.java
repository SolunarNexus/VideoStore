package cz.muni.fi.pv260.videostore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Statement {
    private final List<RentalRecord> rentals;

    private Statement(List<RentalRecord> rentals) {
        this.rentals = rentals;
    }

    /**
     * Returns total price for all rentals in this statement
     * @return Total price for all rentals
     */
    double getTotalRentalPrice() {
        return rentals.stream().mapToDouble(rental -> rental.rentalPrice).sum();
    }

    /**
     * Returns total amount of frequent renter points in this statement
     * @return Total amount of frequent renter points
     */
    public int getTotalFrequenterPoints() {
        return rentals.stream().mapToInt(rental -> rental.frequenterPoints).sum();
    }

    /**
     * Returns a read-only collection of rental records
     * @return A stream of rental records
     */
    public Stream<RentalRecord> getRentalRecords(){
        return rentals.stream();
    }

    /**
     * Rental data record containing necessary info for statement building.
     * @param movieName Movie name
     * @param rentalPrice Price for renting this movie
     * @param frequenterPoints Amount of frequent renter points for this rental
     */
    public record RentalRecord(String movieName, double rentalPrice, int frequenterPoints) {
    }

    public static class StatementBuilder {
        private final List<RentalRecord> rentals = new ArrayList<>();

        /**
         * Adds a rental to a statement collection of rentals.
         * @param name Movie name
         * @param price Price for renting a movie
         * @param frequenterPoints Amount of frequent renter points for this rental
         * @return Reference to this StatementBuilder
         */
        public StatementBuilder addRental(String name, double price, int frequenterPoints) {
            rentals.add(new RentalRecord(name, price, frequenterPoints));
            return this;
        }

        /**
         * Adds a rental to statement collection of rentals
         * @param rental Rental object to be added to the collection
         * @return Reference to this StatementBuilder
         */
        public StatementBuilder addRental(Rental rental) {
            return addRental(rental.movie().getTitle(),
                    rental.getRentalPrice(),
                    rental.getFrequenterPoints());
        }

        /**
         * Builds the statement from collected data
         * @return A new Statement instance
         */
        public Statement build() {
            return new Statement(rentals);
        }
    }
}
