package cz.muni.fi.pv260.videostore;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class holding information needed for output about user rentals
 */
public class Statement {
    private final String customerName;
    private final List<Pair<String, Double>> moviePrices;
    private final int frequenterPoints;

    public Statement(Customer customer) {
        customerName = customer.getName();
        moviePrices = customer.getRentals()
                .stream()
                .map(rental -> new Pair<>(
                        rental.getMovie().getTitle(),
                        rental.getMovie().getRentalPrice(rental.getDaysRented())
                ))
                .toList();
        frequenterPoints = customer.getTotalFrequenterPoints();
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Pair<String, Double>> getMoviePrices() {
        return new ArrayList<>(moviePrices);
    }

    public int getFrequenterPoints() {
        return frequenterPoints;
    }
}
