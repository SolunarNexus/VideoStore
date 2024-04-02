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

    public String statement () {
        var statement = getStatement();
        double      totalAmount             = statement.getTotalRentalPrice();
        int         frequentRenterPoints    = statement.getTotalFrequenterPoints();
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
