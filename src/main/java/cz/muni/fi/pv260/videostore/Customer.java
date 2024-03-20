package cz.muni.fi.pv260.videostore;

import cz.muni.fi.pv260.videostore.movie.Movie;
import cz.muni.fi.pv260.videostore.statement.Statement;

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


    public Statement statement(){
        return new Statement(this);
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

    public List<Rental> getRentals() {
        return rentals.stream().toList();
    }

    private String name;
    private Vector<Rental> rentals = new Vector<>();
}
