package cz.muni.fi.pv260.videostore;

import java.util.Vector;
import java.util.stream.Collectors;

public class Customer
{
    private final String name;
    private final Vector<Rental> rentals = new Vector<>();

    public Customer (String name) {
        this.name = name;
    }

    public void addRental (Rental rental) {
        rentals.addElement (rental);
    }

    public String getName () {
        return name;
    }

    public Statement getStatement() {
        var builder = new Statement.StatementBuilder();
        rentals.forEach(builder::addRental);
        return builder.build();
    }

    public String statement () {
        var statement = getStatement();

        return "Rental Record for " + getName() + "\n" +
                statement.getRentalRecords()
                        .map(record ->
                                "\t" + record.movieName() + "\t" + record.rentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "You owed " + statement.getTotalRentalPrice() + "\n" +
                "You earned " + statement.getTotalFrequenterPoints() + " frequent renter points\n";
    }

    /**
     * Information about customer rentals, their price, total price and frequenter points in html format
     * @return formatted html string
     */
    public String htmlStatement () {
        var statement = getStatement();

        return "<h1>Rentals for <em>" + name + "</em></h1>\n" +
                "<table>\n" +
                "<thead>\n" +
                "  <tr> <th> Movie <th> Price\n" +
                "<tbody>\n" +
                statement.getRentalRecords()
                        .map(record ->
                                "  <tr> <td> " + record.movieName() + " <td> "+ record.rentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "  <tr> <th> You owe <td> " + statement.getTotalRentalPrice() + "\n" +
                "</table>\n" +
                "<p>On this rental you earned <strong>" + statement.getTotalFrequenterPoints() + "</strong> frequent renter\n" +
                "points</p>";
    }
}
