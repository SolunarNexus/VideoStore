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

//    private double frequenterPoints(){
//        return 0;
//    }

//    private double totalPrice(){
//        double total = 0;
//
//        for (Rental rental : this.rentals) {
//            total = getPriceOf(rental.getMovie(), rental.getDaysRented());
//        }
//
//        return total;
//    }

    public double getPriceOf(Movie movie, int daysRented){
        if (movie == null){
            return 0;
        }
        double price;

        switch (movie.getPriceCode()){
            case Movie.REGULAR:
                price = 2;
                if (daysRented > 2)
                    price += (daysRented - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                price = daysRented * 3;
                break;
            case Movie.CHILDRENS:
                price = 1.5;
                if (daysRented > 3)
                    price += (daysRented - 3) * 1.5;
                break;
            default:
                return 0;
        }
        return price;
    }

    public String statement () {
        double      totalAmount             = 0;
        int         frequentRenterPoints    = 0;
        Enumeration rentals                 = this.rentals.elements ();
        String      result                  = "Rental Record for " + getName () + "\n";

        while (rentals.hasMoreElements ()) {
            Rental  each = (Rental)rentals.nextElement ();
            double thisAmount = getPriceOf(each.getMovie(), each.getDaysRented());

            frequentRenterPoints++;

            if (each.getMovie ().getPriceCode () == Movie.NEW_RELEASE
                    && each.getDaysRented () > 1)
                frequentRenterPoints++;

            result += "\t" + each.getMovie ().getTitle () + "\t"
                    + String.valueOf (thisAmount) + "\n";
            totalAmount += thisAmount;

        }

        result += "You owed " + String.valueOf (totalAmount) + "\n";
        result += "You earned " + String.valueOf (frequentRenterPoints) + " frequent renter points\n";


        return result;
    }


    private String name;
    private Vector<Rental> rentals = new Vector<>();
}
