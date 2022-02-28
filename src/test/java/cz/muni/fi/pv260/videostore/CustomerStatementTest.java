package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerStatementTest {

    private static final Movie REGULAR_MOVIE = new Movie("Regular Movie", Movie.REGULAR);
    private static final Movie NEW_RELEASE_MOVIE = new Movie("New Release Movie", Movie.NEW_RELEASE);
    private static final Movie CHILDRENS_MOVIE = new Movie("Children's Movie", Movie.CHILDRENS);

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

    @Test
    void newReleaseMovieBasicPoints() {
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 1));

        assertStatement("""
                Rental Record for John Doe
                	New Release Movie	3.0
                You owed 3.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void newReleaseMovieExtraPoints() {
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 2));

        assertStatement("""
                Rental Record for John Doe
                	New Release Movie	6.0
                You owed 6.0
                You earned 2 frequent renter points
                """);
    }

    @Test
    void childrensMovieFlatRate() {
        customer.addRental(new Rental(CHILDRENS_MOVIE, 1));

        assertStatement("""
                Rental Record for John Doe
                	Children's Movie	1.5
                You owed 1.5
                You earned 1 frequent renter points
                """);
    }

    @Test
    void childrensMovieProgressiveRate() {
        customer.addRental(new Rental(CHILDRENS_MOVIE, 4));

        assertStatement("""
                Rental Record for John Doe
                	Children's Movie	3.0
                You owed 3.0
                You earned 1 frequent renter points
                """);
    }

    private void assertStatement(String expectedStatement) {
        assertEquals(expectedStatement, customer.statement());
    }
}
