package cz.muni.fi.pv260.videostore;

import java.util.stream.Collectors;

public abstract class Statement {
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

    /**
     * Information about customer rentals, their price, total price and frequenter points in formatted string
     * @return formatted statement to string
     */
    public abstract String format ();
}
