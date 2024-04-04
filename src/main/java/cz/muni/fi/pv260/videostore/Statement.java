package cz.muni.fi.pv260.videostore;

import java.util.stream.Collectors;

public class Statement {
    private final Customer customer;

    public Statement(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns total price for all rentals in this statement
     * @return Total price for all rentals
     */
    protected double getTotalRentalPrice() {
        return customer.getRentals().mapToDouble(Rental::getRentalPrice).sum();
    }

    /**
     * Returns total amount of frequent renter points in this statement
     * @return Total amount of frequent renter points
     */
    protected int getTotalFrequenterPoints() {
        return customer.getRentals().mapToInt(Rental::getFrequenterPoints).sum();
    }

    protected Customer getCustomer(){
        return customer;
    }

    public String format () {
        return "Rental Record for " + getCustomer().getName() + "\n" +
                getCustomer().getRentals()
                        .map(rental ->
                                "\t" + rental.getMovie().getTitle() + "\t" + rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "You owed " + getTotalRentalPrice() + "\n" +
                "You earned " + getTotalFrequenterPoints() + " frequent renter points\n";
    }

    /**
     * Information about customer rentals, their price, total price and frequenter points in html format
     * @return formatted html string
     */
    public String htmlStatement () {
        return "<h1>Rentals for <em>" + getCustomer().getName() + "</em></h1>\n" +
                "<table>\n" +
                "<thead>\n" +
                "  <tr> <th> Movie <th> Price\n" +
                "<tbody>\n" +
                getCustomer().getRentals()
                        .map(rental ->
                                "  <tr> <td> " + rental.getMovie().getTitle() + " <td> "+ rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "  <tr> <th> You owe <td> " + getTotalRentalPrice() + "\n" +
                "</table>\n" +
                "<p>On this rental you earned <strong>" + getTotalFrequenterPoints() + "</strong> frequent renter\n" +
                "points</p>";
    }
}
