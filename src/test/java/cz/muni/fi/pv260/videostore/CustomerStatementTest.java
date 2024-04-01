package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class CustomerStatementTest {

    private Customer customer = new Customer("John Doe");

    @Test
    void emptyCustomerName() {
        customer = new Customer("");

        assertStatement("""
                Rental Record for\s
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void nullCustomerName() {
        customer = new Customer(null);

        assertStatement("""
                Rental Record for null
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void unicodeCustomerName() {
        customer = new Customer("František Vopršálek");

        assertStatement("""
                Rental Record for František Vopršálek
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void emptyMovieName() {
        customer.addRental(new Rental(new RegularMovie(""), 4));

        assertStatement("""
                Rental Record for John Doe
                		5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovieName() {
        customer.addRental(new Rental(new RegularMovie(null), 4));

        assertStatement("""
                Rental Record for John Doe
                	null	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void unicodeMovieName() {
        customer.addRental(new Rental(new RegularMovie("Příběhy obyčejného šílenství"), 4));

        assertStatement("""
                Rental Record for John Doe
                	Příběhy obyčejného šílenství	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovie() {
        customer.addRental(new Rental(null, 4));

        assertThrows(NullPointerException.class, () -> customer.statement());
    }

    @Test
    void nullRental() {
        customer.addRental(null);

        assertThrows(NullPointerException.class, () -> customer.statement());
    }

    private void assertStatement(String expectedStatement) {
        assertEquals(expectedStatement, customer.statement());
    }
}
