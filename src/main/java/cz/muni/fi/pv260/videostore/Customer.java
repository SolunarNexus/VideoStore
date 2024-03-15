package cz.muni.fi.pv260.videostore;

import javafx.util.Pair;

import java.util.List;
import java.util.Vector;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

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

    public int getFrequenterPoints(Rental rental) {
        return (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1) ? 2 : 1;
    }

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
        double price;

        switch (movie.getPriceCode()) {
            case Movie.REGULAR:
                price = 2;
                if (daysRented > 2)
                    price += (daysRented - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                price = daysRented * 3;
                break;
            case Movie.CHILDRENS:
                price = 1.5;
                if (daysRented > 3)
                    price += (daysRented - 3) * 1.5;
                break;
            default:
                return 0;
        }
        return price;
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

    private String name;
    private Vector<Rental> rentals = new Vector<>();
}
