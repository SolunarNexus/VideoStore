import cz.muni.fi.pv260.videostore.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Demonstrates how the {@link Customer}, {@link Rental} and {@link Movie}
 * classes are intended to be used together in a real application.
 * <p>
 * <b>NOTE:</b> Everything from the aforementioned classes used in this example
 * is to be considered their <em>public interface</em>!
 */
public final class Main {

    private static Customer customer = createCustomer();

    public static void main(String[] args) {
        List<Rental> rentals = createRentals();

        addRentalsToCustomer(rentals);

        printRentedMovies(rentals);
        printRentalStatement();
    }

    private static Customer createCustomer() {
        return new Customer("Martin Fowler");
    }

    private static List<Rental> createRentals() {
        return List.of(
                new Rental(new RegularMovie("Alien"), 1),
                new Rental(new ChildrenMovie("Lion King"), 2),
                new Rental(new NewReleaseMovie("Titanic"), 3)
        );
    }

    private static void addRentalsToCustomer(List<Rental> rentals) {
        rentals.forEach(customer::addRental);
    }

    private static void printRentedMovies(List<Rental> rentals) {
        System.out.println(customer.getName() + " -> " + rentals.stream()
                .map(r -> r.getMovie().getTitle())
                .collect(Collectors.joining(", ")));
        System.out.println();
    }

    private static void printRentalStatement() {
        System.out.print(new AsciiStatement(customer));
    }
}
