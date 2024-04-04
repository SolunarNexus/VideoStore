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

    double getTotalRentalPrice(){
        return rentals.stream().mapToDouble(Rental::getRentalPrice).sum();
    }

    int getTotalFrequenterPoints() {
        return rentals.stream().mapToInt(Rental::getFrequenterPoints).sum();
    }


    public String statement () {
        return "Rental Record for " + getName() + "\n" +
                rentals.stream()
                        .map(rental ->
                                "\t" + rental.movie().getTitle() + "\t" + rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "You owed " + getTotalRentalPrice() + "\n" +
                "You earned " + getTotalFrequenterPoints() + " frequent renter points\n";
    }

    /**
     * Information about customer rentals, their price, total price and frequenter points in html format
     * @return formatted html string
     */
    public String htmlStatement () {
        return "<h1>Rentals for <em>" + name + "</em></h1>\n" +
                "<table>\n" +
                "<thead>\n" +
                "  <tr> <th> Movie <th> Price\n" +
                "<tbody>\n" +
                rentals.stream()
                        .map(rental ->
                                "  <tr> <td> " + rental.movie().getTitle() + " <td> "+ rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "  <tr> <th> You owe <td> " + getTotalRentalPrice() + "\n" +
                "</table>\n" +
                "<p>On this rental you earned <strong>" + getTotalFrequenterPoints() + "</strong> frequent renter\n" +
                "points</p>";
    }
}
