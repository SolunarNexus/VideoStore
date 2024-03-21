package cz.muni.fi.pv260.videostore;

import cz.muni.fi.pv260.videostore.movie.Movie;
import cz.muni.fi.pv260.videostore.statement.Statement;

import java.util.*;

public class Customer {
    private final String name;
    private final Vector<Rental> rentals = new Vector<>();

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

    public List<Rental> getRentals() {
        return rentals.stream().toList();
    }
}
