package cz.muni.fi.pv260.videostore;

import cz.muni.fi.pv260.videostore.movie.ChildrenMovie;
import cz.muni.fi.pv260.videostore.movie.Movie;
import cz.muni.fi.pv260.videostore.movie.NewReleaseMovie;
import cz.muni.fi.pv260.videostore.movie.RegularMovie;
import cz.muni.fi.pv260.videostore.statement.Statement;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class StatementTest {

    private static final Movie REGULAR_MOVIE = new RegularMovie("Regular Movie");
    private static final Movie NEW_RELEASE_MOVIE = new NewReleaseMovie("New Release Movie");
    private static final Movie CHILDRENS_MOVIE = new ChildrenMovie("Children's Movie");

    private final Customer customer = new Customer("John Doe");

    @Test
    void emptyStatement() {
        Statement statement = new Statement(customer);
        assertEquals(statement.getCustomerName(), "John Doe");
        assertEquals(statement.getMoviePrices(), new ArrayList<>());
    }
    @Test
    void allMoviesStatement() {
        customer.addRental(new Rental(REGULAR_MOVIE, 10));
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 10));
        customer.addRental(new Rental(CHILDRENS_MOVIE, 10));

        Statement statement = new Statement(customer);
        var prices = statement.getMoviePrices();
        assertEquals(prices.get(0), new Pair<>("Regular Movie", 14.0));
        assertEquals(prices.get(1), new Pair<>("New Release Movie", 30.0));
        assertEquals(prices.get(2), new Pair<>("Children's Movie", 12.0));
    }

    @Test
    void totalFrequenterPointsMultiple(){
        customer.addRental(new Rental(REGULAR_MOVIE, 10));
        customer.addRental(new Rental(NEW_RELEASE_MOVIE, 10));
        customer.addRental(new Rental(CHILDRENS_MOVIE, 10));

        Statement statement = new Statement(customer);
        assertEquals(statement.getFrequenterPoints(), 4);
    }

//    @Test
//    void frequenterPointsRegularAndChildrensMovie(){
//        Rental regular = new Rental(REGULAR_MOVIE, 10);
//        assertEquals(Statement.getRentalFrequenterPoints(regular), 1);
//
//        Rental children = new Rental(CHILDRENS_MOVIE, 10);
//        assertEquals(Statement.getRentalFrequenterPoints(children), 1);
//    }
//
//    @Test
//    void frequenterPointsNewReleaseZeroDays(){
//        Rental regular = new Rental(NEW_RELEASE_MOVIE, 0);
//        assertEquals(Statement.getRentalFrequenterPoints(regular),1);
//    }
//
//    @Test
//    void frequenterPointsNewReleaseBoundary(){
//        Rental regular = new Rental(NEW_RELEASE_MOVIE, 1);
//        assertEquals(Statement.getRentalFrequenterPoints(regular), 1);
//    }
//
//    @Test
//    void frequenterPointsNewReleaseExtraPoints(){
//        Rental regular = new Rental(NEW_RELEASE_MOVIE, 2);
//        assertEquals(Statement.getRentalFrequenterPoints(regular), 2);
//    }

}
