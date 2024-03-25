package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class CustomerStatementTest {

    private static final Movie REGULAR_MOVIE = new Movie("Regular Movie", Movie.REGULAR);
    private static final Movie NEW_RELEASE_MOVIE = new Movie("New Release Movie", Movie.NEW_RELEASE);
    private static final Movie CHILDRENS_MOVIE = new Movie("Children's Movie", Movie.CHILDRENS);

    private Customer customer = new Customer("John Doe");

    @Test
    void individualMoviePriceNullMovie(){
        assertEquals(0, customer.getPriceOf(null, 2));
    }

    @Test
    void individualMoviePriceRegularFlatRate(){
        assertEquals(2, customer.getPriceOf(REGULAR_MOVIE, -1));
        assertEquals(2, customer.getPriceOf(REGULAR_MOVIE, 0));
        assertEquals(2, customer.getPriceOf(REGULAR_MOVIE, 1));
    }

    @Test
    void individualMoviePriceRegularFlatRateEdgeCase(){
        assertEquals(2, customer.getPriceOf(REGULAR_MOVIE, 2));
    }

    @Test
    void individualMoviePriceRegularProgressiveRate(){
        assertEquals(3.5, customer.getPriceOf(REGULAR_MOVIE, 3));
        assertEquals(5, customer.getPriceOf(REGULAR_MOVIE, 4));
    }
    
    @Test
    void individualMoviePriceNewRelease(){
        assertEquals(-3, customer.getPriceOf(NEW_RELEASE_MOVIE, -1));
        assertEquals(0, customer.getPriceOf(NEW_RELEASE_MOVIE, 0));
        assertEquals(3, customer.getPriceOf(NEW_RELEASE_MOVIE, 1));
        assertEquals(6, customer.getPriceOf(NEW_RELEASE_MOVIE, 2));
    }

    @Test
    void individualMoviePriceChildrenFlatRate(){
        assertEquals(1.5, customer.getPriceOf(CHILDRENS_MOVIE, -1));
        assertEquals(1.5, customer.getPriceOf(CHILDRENS_MOVIE, 0));
        assertEquals(1.5, customer.getPriceOf(CHILDRENS_MOVIE, 1));
    }

    @Test
    void individualMoviePriceChildrenFlatRateEdgeCase(){
        assertEquals(1.5, customer.getPriceOf(CHILDRENS_MOVIE, 3));
    }

    @Test
    void individualMoviePriceChildrenProgressiveRate(){
        assertEquals(3, customer.getPriceOf(CHILDRENS_MOVIE, 4));
        assertEquals(4.5, customer.getPriceOf(CHILDRENS_MOVIE, 5));
    }

    @Test
    void totalRentalPriceForMultipleMovies(){
        customer.addRental(new Rental(REGULAR_MOVIE, 10));
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 10));
        customer.addRental(new Rental(CHILDRENS_MOVIE, 10));
        assertEquals(56, customer.getTotalRentalPrice());
    }

    @Test
    void frequenterPointsNullRental(){
        assertEquals(0, customer.getFrequenterPoints(null));
    }

    @Test
    void noRentals() {
        assertStatement("""
                Rental Record for John Doe
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void regularMovieZeroDays() {
        customer.addRental(new Rental(REGULAR_MOVIE, 0));

        assertStatement("""
                Rental Record for John Doe
                	Regular Movie	2.0
                You owed 2.0
                You earned 1 frequent renter points
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
    void regularMovieFlatRateOnBoundary() {
        customer.addRental(new Rental(REGULAR_MOVIE, 2));

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
    void newReleaseMovieZeroDays() {
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 0));

        assertStatement("""
                Rental Record for John Doe
                	New Release Movie	0.0
                You owed 0.0
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
    void childrensMovieZeroDays() {
        customer.addRental(new Rental(CHILDRENS_MOVIE, 0));

        assertStatement("""
                Rental Record for John Doe
                	Children's Movie	1.5
                You owed 1.5
                You earned 1 frequent renter points
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
    void childrensMovieFlatRateOnBoundary() {
        customer.addRental(new Rental(CHILDRENS_MOVIE, 3));

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

    @Test
    void multipleMoviesOrderAndTotals() {
        customer.addRental(new Rental(REGULAR_MOVIE, 10));
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 10));
        customer.addRental(new Rental(CHILDRENS_MOVIE, 10));

        assertStatement("""
                Rental Record for John Doe
                	Regular Movie	14.0
                	New Release Movie	30.0
                	Children's Movie	12.0
                You owed 56.0
                You earned 4 frequent renter points
                """);
    }

    @Test
    void regularMovieNegativeDays() {
        customer.addRental(new Rental(REGULAR_MOVIE, -10));

        assertStatement("""
                Rental Record for John Doe
                	Regular Movie	2.0
                You owed 2.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void newReleaseMovieNegativeDays() {
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, -10));

        assertStatement("""
                Rental Record for John Doe
                	New Release Movie	-30.0
                You owed -30.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void childrensMovieNegativeDays() {
        customer.addRental(new Rental(CHILDRENS_MOVIE, -10));

        assertStatement("""
                Rental Record for John Doe
                	Children's Movie	1.5
                You owed 1.5
                You earned 1 frequent renter points
                """);
    }

    @Test
    void unknownMovieType() {
        customer.addRental(new Rental(new Movie("Unknown", -1), 100));

        assertStatement("""
                Rental Record for John Doe
                	Unknown	0.0
                You owed 0.0
                You earned 1 frequent renter points
                """);
    }

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
        customer.addRental(new Rental(new Movie("", Movie.REGULAR), 4));

        assertStatement("""
                Rental Record for John Doe
                		5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovieName() {
        customer.addRental(new Rental(new Movie(null, Movie.REGULAR), 4));

        assertStatement("""
                Rental Record for John Doe
                	null	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void unicodeMovieName() {
        customer.addRental(new Rental(new Movie("Příběhy obyčejného šílenství", Movie.REGULAR), 4));

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
