package cz.muni.fi.pv260.videostore;

import java.util.ArrayList;
import java.util.List;

public class Statement {

    private String customerName;
    private List<RentalRecord> rentals;

    private Statement(String customerName, List<RentalRecord> rentals) {
        this.customerName = customerName;
        this.rentals = rentals;
    }

    double getTotalRentalPrice(){
        double total = 0;

        for (RentalRecord rental : this.rentals) {
            total += rental.rentalPrice;
        }

        return total;
    }

    public int getTotalFrequenterPoints() {
        int points = 0;
        for (RentalRecord rental : this.rentals) {
            points += rental.frequenterPoints;
        }
        return points;
    }

    private record RentalRecord(String movieName, double rentalPrice, int frequenterPoints){}

    public static class StatementBuilder{
        private String customerName;
        private final List<RentalRecord> rentals = new ArrayList<>();

        public StatementBuilder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public StatementBuilder addRental(String name, double price, int frequenterPoints){
            rentals.add(new RentalRecord(name, price, frequenterPoints));
            return this;
        }

        public StatementBuilder addRental(Rental rental){
            return addRental(rental.getMovie().getTitle(),
                    rental.getMovie().getPriceOf(rental.getDaysRented()),
                    rental.getMovie().getFrequenterPoints(rental.getDaysRented()));
        }

        public Statement build(){
            return new Statement(customerName, rentals);
        }

    }
}
