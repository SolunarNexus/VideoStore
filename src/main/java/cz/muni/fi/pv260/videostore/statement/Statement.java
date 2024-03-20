package cz.muni.fi.pv260.videostore.statement;

import cz.muni.fi.pv260.videostore.Customer;
import cz.muni.fi.pv260.videostore.Rental;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Class holding information needed for output about user rentals
 */
public class Statement {
    private final String customerName;
    //private final List<Pair<String, Double>> moviePrices;
    private final List<Rental> rentals;

    public Statement(Customer customer) {
        customerName = customer.getName();
        rentals = customer.getRentals();
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<AbstractMap.SimpleImmutableEntry<String, Double>> getMoviePrices() {
        return rentals.stream()
                .map(rental -> new AbstractMap.SimpleImmutableEntry<String, Double>(
                        rental.getMovie().getTitle(),
                        rental.getMovie().getRentalPrice(rental.getDaysRented())
                ))
                .toList();
    }

    public double getTotalRentalPrice(){
        return getMoviePrices().stream().mapToDouble(AbstractMap.SimpleImmutableEntry::getValue).sum();
    }

    private int getRentalFrequenterPoints(Rental rental) {
        return (rental.getMovie().countsToFrequentPoints() && rental.getDaysRented() > 1) ? 2 : 1;
    }

    public int getFrequenterPoints() {
        int points = 0;

        for (Rental rental : this.rentals) {
            points += getRentalFrequenterPoints(rental);
        }
        return points;
    }
}
