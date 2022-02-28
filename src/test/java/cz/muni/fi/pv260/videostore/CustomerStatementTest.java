package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerStatementTest {

    private final Customer customer = new Customer("John Doe");

    @Test
    void noRentals() {
        String expectedStatement = """
                Rental Record for John Doe
                You owed 0.0
                You earned 0 frequent renter points
                """;
        assertEquals(expectedStatement, customer.statement());
    }

    @Test
    void regularMovieFlatRate() {
        customer.addRental(new Rental(new Movie("Regular Movie", Movie.REGULAR), 1));
        String expectedStatement = """
                Rental Record for John Doe
                	Regular Movie	2.0
                You owed 2.0
                You earned 1 frequent renter points
                """;
        assertEquals(expectedStatement, customer.statement());
    }

    @Test
    void regularMovieProgressiveRate() {
        customer.addRental(new Rental(new Movie("Regular Movie", Movie.REGULAR), 3));
        String expectedStatement = """
                Rental Record for John Doe
                	Regular Movie	3.5
                You owed 3.5
                You earned 1 frequent renter points
                """;
        assertEquals(expectedStatement, customer.statement());
    }
}
