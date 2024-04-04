package cz.muni.fi.pv260.videostore;

import java.util.stream.Collectors;

public class AsciiStatement extends Statement {

    public AsciiStatement(Customer customer) {
        super(customer);
    }

    @Override
    public String format () {
        return "Rental Record for " + getCustomer().getName() + "\n" +
                getCustomer().getRentals()
                        .map(rental ->
                                "\t" + rental.getMovie().getTitle() + "\t" + rental.getRentalPrice() + "\n")
                        .collect(Collectors.joining()) +
                "You owed " + getTotalRentalPrice() + "\n" +
                "You earned " + getTotalFrequenterPoints() + " frequent renter points\n";
    }
}
