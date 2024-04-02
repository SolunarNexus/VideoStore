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

    public String toASCIIFormat () {
        double totalAmount = getTotalRentalPrice();
        int frequentRenterPoints = getTotalFrequenterPoints();
        String result = "Rental Record for " + customerName + "\n";

        for (var currentRental : rentals){
            double  thisAmount = currentRental.rentalPrice;

            result += "\t" + currentRental.movieName + "\t" + thisAmount + "\n";
        }

        result += "You owed " + totalAmount + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points\n";

        return result;
    }

    public String toHtmlFormat () {
        String result = String.format("<h1>Rentals for <em>%s</em></h1>\n", customerName);
        result += "<table>\n" + "<thead>\n" + "  <tr> <th> Movie <th> Price\n" + "<tbody>\n";

        for (var currentRental : rentals){
            result += String.format("  <tr> <td> %s <td> %.1f\n", currentRental.movieName, currentRental.rentalPrice);
        }

        result += String.format("<tr> <th> You owe <td> %.1f\n" + "</table>", getTotalRentalPrice());
        result += String.format("<p>On this rental you earned <strong>%d</strong> frequent renter" +
                "points</p>\n", getTotalFrequenterPoints());

        return result;
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
