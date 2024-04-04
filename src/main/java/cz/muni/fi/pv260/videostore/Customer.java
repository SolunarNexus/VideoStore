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

    /**
     * Information about customer rentals, their price, total price and frequenter points in html format
     * @return formatted html string
     */
    public String htmlStatement () {
        var statement = new Statement(this);
        return "<h1>Rentals for <em>" + name + "</em></h1>\n" +
                "<table>\n" +
                "<thead>\n" +
                "  <tr> <th> Movie <th> Price\n" +
                "<tbody>\n" +
                rentals.stream()
                        .map(rental ->
                                "  <tr> <td> " + rental.getMovie().getTitle() + " <td> "+ rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "  <tr> <th> You owe <td> " + statement.getTotalRentalPrice() + "\n" +
                "</table>\n" +
                "<p>On this rental you earned <strong>" + statement.getTotalFrequenterPoints() + "</strong> frequent renter\n" +
                "points</p>";
    }

    private String name;
    private final Vector<Rental> rentals = new Vector<>();
}
