package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AsciiStatementTest {

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
        customer.addRental(new Rental(new RegularMovie("") {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

        assertStatement("""
                Rental Record for John Doe
                		5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovieName() {
        customer.addRental(new Rental(new RegularMovie(null) {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

        assertStatement("""
                Rental Record for John Doe
                	null	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void unicodeMovieName() {
        customer.addRental(new Rental(new RegularMovie("Příběhy obyčejného šílenství") {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

        assertStatement("""
                Rental Record for John Doe
                	Příběhy obyčejného šílenství	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void NegativePrice() {
        customer.addRental(new Rental(new RegularMovie("New Release Movie"){
            @Override
            public double getPriceOf(int daysRented) {
                return -30;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, -10));

        assertStatement("""
                Rental Record for John Doe
                	New Release Movie	-30.0
                You owed -30.0
                You earned 1 frequent renter points
                """);
    }

    private void assertStatement(String expectedStatement) {
        assertEquals(expectedStatement, new AsciiStatement(customer).format());
    }
}
