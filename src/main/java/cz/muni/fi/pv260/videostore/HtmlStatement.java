package cz.muni.fi.pv260.videostore;

import java.util.stream.Collectors;

public class HtmlStatement extends Statement {

    public HtmlStatement(Customer customer) {
        super(customer);
    }

    @Override
    public String toString() {
        return "<h1>Rentals for <em>" + getCustomer().getName() + "</em></h1>\n" +
                "<table>\n" +
                "<thead>\n" +
                "  <tr> <th> Movie <th> Price\n" +
                "<tbody>\n" +
                getCustomer().getRentals()
                        .map(rental ->
                                "  <tr> <td> " + rental.getMovie().getTitle() + " <td> " + rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "  <tr> <th> You owe <td> " + getTotalRentalPrice() + "\n" +
                "</table>\n" +
                "<p>On this rental you earned <strong>" + getTotalFrequenterPoints() + "</strong> frequent renter\n" +
                "points</p>";
    }
}
