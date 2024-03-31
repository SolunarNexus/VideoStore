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

    private String name;
    private final Vector<Rental> rentals = new Vector<>();
}
