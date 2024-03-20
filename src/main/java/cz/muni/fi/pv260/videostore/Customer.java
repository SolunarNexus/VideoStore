package cz.muni.fi.pv260.videostore;

import cz.muni.fi.pv260.videostore.movie.Movie;
import javafx.util.Pair;

import java.util.*;

public class Customer {
    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public String getName() {
        return name;
    }

    @Deprecated
    public int getFrequenterPoints(Rental rental) {
        return (rental.getMovie().countsToFrequentPoints() && rental.getDaysRented() > 1) ? 2 : 1;
    }

    @Deprecated
    public int getTotalFrequenterPoints() {
        int points = 0;

        for (Rental rental : this.rentals) {
            points += getFrequenterPoints(rental);
        }
        return points;
    }

    public double getTotalRentalPrice() {
        double total = 0;

        for (Rental rental : this.rentals) {
            total += getRentalPriceOf(rental.getMovie(), rental.getDaysRented());
        }
        return total;
    }

    public double getRentalPriceOf(Movie movie, int daysRented) {
        if (movie == null) {
            return 0;
        }
        return movie.getRentalPrice(daysRented);
    }

    public String statement() {
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\n");
        List<Pair<String, Double>> moviePrices = this.rentals
                .stream()
                .map(rental -> {
                    return new Pair<>(
                            rental.getMovie().getTitle(),
                            getRentalPriceOf(rental.getMovie(), rental.getDaysRented())
                    );
                })
                .toList();

        for (Pair<String, Double> moviePricePair : moviePrices) {
            result.append("\t")
                    .append(moviePricePair.getKey())
                    .append("\t")
                    .append(moviePricePair.getValue())
                    .append("\n");
        }

        result.append("You owed ")
                .append(getTotalRentalPrice())
                .append("\n");
        result.append("You earned ")
                .append(getTotalFrequenterPoints())
                .append(" frequent renter points\n");

        return result.toString();
    }

    public List<Rental> getRentals() {
        return rentals.stream().toList();
    }

    private String name;
    private Vector<Rental> rentals = new Vector<>();
}
