package cz.muni.fi.pv260.videostore;

import java.util.Vector;
import java.util.Enumeration;
import java.util.stream.Collectors;

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

    double getTotalRentalPrice(){
        double total = 0;

        for (Rental rental : this.rentals) {
            total += rental.getMovie().getPriceOf(rental.getDaysRented());
        }

        return total;
    }

    int getTotalFrequenterPoints() {
        int points = 0;

        for (Rental rental : this.rentals) {
            points += rental.getMovie().getFrequenterPoints(rental.getDaysRented());
        }
        return points;
    }


    public String statement () {
        double      totalAmount             = getTotalRentalPrice();
        int         frequentRenterPoints    = getTotalFrequenterPoints();
        Enumeration rentals                 = this.rentals.elements ();
        String      result                  = "Rental Record for " + getName () + "\n";

        while (rentals.hasMoreElements ()) {
            Rental  currentRental = (Rental)rentals.nextElement ();
            double  thisAmount = currentRental.getMovie().getPriceOf(currentRental.getDaysRented());

            result += "\t" + currentRental.getMovie ().getTitle () + "\t"
                    + String.valueOf (thisAmount) + "\n";
        }

        result += "You owed " + String.valueOf (totalAmount) + "\n";
        result += "You earned " + String.valueOf (frequentRenterPoints) + " frequent renter points\n";

        return result;
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
                                "  <tr> <td> " + rental.getMovie().getTitle() + " <td> "+ rental.getMovie().getPriceOf(rental.getDaysRented()) + "\n")
                        .collect(Collectors.joining()) +
                "  <tr> <th> You owe <td> " + getTotalRentalPrice() + "\n" +
                "</table>\n" +
                "<p>On this rental you earned <strong>" + getTotalFrequenterPoints() + "</strong> frequent renter\n" +
                "points</p>";
    }

    private String name;
    private final Vector<Rental> rentals = new Vector<>();
}
