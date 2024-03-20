package cz.muni.fi.pv260.videostore;

import javafx.util.Pair;

import java.util.List;

/**
 * Class holding information needed for output about user rentals
 */
public class Statement {
    private final String customerName;
    //private final List<Pair<String, Double>> moviePrices;
    private final List<Rental> rentals;
    private final int frequenterPoints;

    public Statement(Customer customer) {
        customerName = customer.getName();
        rentals = customer.getRentals();
        frequenterPoints = customer.getTotalFrequenterPoints();
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Pair<String, Double>> getMoviePrices() {
        return rentals.stream()
                .map(rental -> new Pair<>(
                        rental.getMovie().getTitle(),
                        rental.getMovie().getRentalPrice(rental.getDaysRented())
                ))
                .toList();
    }

    public int getFrequenterPoints() {
        return frequenterPoints;
    }
}
