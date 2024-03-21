package cz.muni.fi.pv260.videostore;

import cz.muni.fi.pv260.videostore.movie.Movie;
import cz.muni.fi.pv260.videostore.movie.ChildrenMovie;
import cz.muni.fi.pv260.videostore.movie.NewReleaseMovie;
import cz.muni.fi.pv260.videostore.movie.RegularMovie;
import cz.muni.fi.pv260.videostore.statement.ASCIIStatementFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class CustomerStatementTest {

    private static final Movie REGULAR_MOVIE = new RegularMovie("Regular Movie");
    private static final Movie NEW_RELEASE_MOVIE = new NewReleaseMovie("New Release Movie");
    private static final Movie CHILDRENS_MOVIE = new ChildrenMovie("Children's Movie");

    private Customer customer = new Customer("John Doe");

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

        assertThrows(NullPointerException.class, () -> customer.statement().getMoviePrices());
    }

    @Test
    void nullRental() {
        customer.addRental(null);

        assertThrows(NullPointerException.class, () -> customer.statement().getMoviePrices());
    }

    @Test
    void rentalMoviePriceNewRelease(){
        var rental = new Rental(new NewReleaseMovie("Shrek"), 3);
        customer.addRental(rental);
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(9);
    }

    @Test
    void rentalMoviePriceRegularLessThanThreeDays(){
        var rental = new Rental(new RegularMovie("Shrek"), 2);
        customer.addRental(rental);
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(2);
    }

    @Test
    void rentalMoviePriceRegularMoreThanThreeDays(){
        var rental = new Rental(new RegularMovie("Shrek"), 3);
        customer.addRental(rental);
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(3.5);
    }

    @Test
    void rentalMoviePriceChildrensLessThanFourDays(){
        var rental = new Rental(new ChildrenMovie("Shrek"), 3);
        customer.addRental(rental);
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(1.5);
    }

    @Test
    void rentalMoviePriceChildrensMoreThanFourDays(){
        var rental = new Rental(new ChildrenMovie("Shrek"), 4);
        customer.addRental(rental);
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(3);
    }

    @Test
    void totalRentalPriceEmpty(){
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(0);
    }

    @Test
    void totalRentalPriceRegularNegativeDays(){
        customer.addRental(new Rental(REGULAR_MOVIE, -10));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(2);
    }
    @Test
    void totalRentalPriceRegularZeroDays(){
        customer.addRental(new Rental(REGULAR_MOVIE, 0));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(2);
    }

    @Test
    void totalRentalPriceRegularFlatRate(){
        customer.addRental(new Rental(REGULAR_MOVIE, 1));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(2);
    }

    @Test
    void totalRentalPriceRegularBoundary(){
        customer.addRental(new Rental(REGULAR_MOVIE, 2));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(2);
    }

    @Test
    void totalRentalPriceRegularProgressiveRate(){
        customer.addRental(new Rental(REGULAR_MOVIE, 3));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(3.5);
    }

    @Test
    void totalRentalPriceNewReleaseNegativeDays(){
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, -10));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(-30);
    }

    @Test
    void totalRentalPriceNewReleaseZeroDays(){
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 0));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(0);
    }

    @Test
    void totalRentalPriceNewReleaseNonZeroDays(){
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 5));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(15);
    }

    @Test
    void totalRentalPriceChildrensNegativeDays(){
        customer.addRental(new Rental(CHILDRENS_MOVIE, -10));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(1.5);
    }

    @Test
    void totalRentalPriceChildrensZeroDays(){
        customer.addRental(new Rental(CHILDRENS_MOVIE, 0));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(1.5);
    }

    @Test
    void totalRentalPriceChildrensFlatRate(){
        customer.addRental(new Rental(CHILDRENS_MOVIE, 1));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(1.5);
    }

    @Test
    void totalRentalPriceChildrensBoundary(){
        customer.addRental(new Rental(CHILDRENS_MOVIE, 3));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(1.5);
    }

    @Test
    void totalRentalPriceChildrensProgressiveRate(){
        customer.addRental(new Rental(CHILDRENS_MOVIE, 4));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(3);
    }

    @Test
    void totalRentalPriceMultiple(){
        customer.addRental(new Rental(REGULAR_MOVIE, 10));
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 10));
        customer.addRental(new Rental(CHILDRENS_MOVIE, 10));
        assertThat(customer.statement().getTotalRentalPrice()).isEqualTo(56);
    }

    @Test
    void frequenterPointsRegularAndChildrensMovie(){
        Rental regular = new Rental(REGULAR_MOVIE, 10);
        customer.addRental(regular);
        assertThat(customer.statement().getFrequenterPoints()).isEqualTo(1);

        Rental children = new Rental(CHILDRENS_MOVIE, 10);
        customer.addRental(regular);
        assertThat(customer.statement().getFrequenterPoints()).isEqualTo(2);
    }

    @Test
    void frequenterPointsNewReleaseZeroDays(){
        Rental regular = new Rental(NEW_RELEASE_MOVIE, 0);
        customer.addRental(regular);
        assertThat(customer.statement().getFrequenterPoints()).isEqualTo(1);
    }

    @Test
    void frequenterPointsNewReleaseBoundary(){
        Rental regular = new Rental(NEW_RELEASE_MOVIE, 1);
        customer.addRental(regular);
        assertThat(customer.statement().getFrequenterPoints()).isEqualTo(1);
    }

    @Test
    void frequenterPointsNewReleaseExtraPoints(){
        Rental regular = new Rental(NEW_RELEASE_MOVIE, 2);
        customer.addRental(regular);
        assertThat(customer.statement().getFrequenterPoints()).isEqualTo(2);
    }

    @Test
    void totalFrequenterPointsMultiple(){
        customer.addRental(new Rental(REGULAR_MOVIE, 10));
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 10));
        customer.addRental(new Rental(CHILDRENS_MOVIE, 10));
        assertThat(customer.statement().getFrequenterPoints()).isEqualTo(4);
    }

    private void assertStatement(String expectedStatement) {
        assertEquals(expectedStatement, new ASCIIStatementFormatter().formatStatement(customer.statement()));
    }
}
