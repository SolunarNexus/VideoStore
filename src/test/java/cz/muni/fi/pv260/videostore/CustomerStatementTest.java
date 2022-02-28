package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerStatementTest {

    private static final Movie REGULAR_MOVIE = new Movie("Regular Movie", Movie.REGULAR);

    private final Customer customer = new Customer("John Doe");

    @Test
    void noRentals() {
        assertStatement("""
                Rental Record for John Doe
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void regularMovieFlatRate() {
        customer.addRental(new Rental(REGULAR_MOVIE, 1));

        assertStatement("""
                Rental Record for John Doe
                	Regular Movie	2.0
                You owed 2.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void regularMovieProgressiveRate() {
        customer.addRental(new Rental(REGULAR_MOVIE, 3));

        assertStatement("""
                Rental Record for John Doe
                	Regular Movie	3.5
                You owed 3.5
                You earned 1 frequent renter points
                """);
    }

    private void assertStatement(String expectedStatement) {
        assertEquals(expectedStatement, customer.statement());
    }
}
