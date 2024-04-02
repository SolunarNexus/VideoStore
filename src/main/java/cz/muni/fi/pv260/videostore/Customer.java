package cz.muni.fi.pv260.videostore;

import java.util.Vector;
import java.util.Enumeration;

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

    public Statement getStatement(){
        var builder = new Statement.StatementBuilder()
                .setCustomerName(name);
        rentals.forEach(builder::addRental);
        return builder.build();
    }

    private String name;
    private final Vector<Rental> rentals = new Vector<>();
}
