package cz.muni.fi.pv260.videostore;

import java.util.Vector;
import java.util.Enumeration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Customer
{
    public Customer (String name) {
        this.name = name;
    }

    public void addRental (Rental rental) {
        rentals.addElement (rental);
    }

    public String getName () {
        return name;
    }

    Stream<Rental> getRentals(){
        return rentals.stream();
    }

    private String name;
    private final Vector<Rental> rentals = new Vector<>();
}
