package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {
    private static final Movie REGULAR_MOVIE = new RegularMovie("Regular Movie");
    private static final Movie NEW_RELEASE_MOVIE = new NewReleaseMovie("New Release Movie");
    private static final Movie CHILDRENS_MOVIE = new ChildrenMovie("Children's Movie");

    @Test
    void individualMoviePriceRegularFlatRate(){
        assertEquals(2, REGULAR_MOVIE.getPriceOf( -1));
        assertEquals(2, REGULAR_MOVIE.getPriceOf( 0));
        assertEquals(2, REGULAR_MOVIE.getPriceOf(1));
    }

    @Test
    void individualMoviePriceRegularFlatRateEdgeCase(){
        assertEquals(2, REGULAR_MOVIE.getPriceOf(2));
    }

    @Test
    void individualMoviePriceRegularProgressiveRate(){
        assertEquals(3.5, REGULAR_MOVIE.getPriceOf(3));
        assertEquals(5, REGULAR_MOVIE.getPriceOf( 4));
    }

    @Test
    void individualMoviePriceNewRelease(){
        assertEquals(-3, NEW_RELEASE_MOVIE.getPriceOf(-1));
        assertEquals(0, NEW_RELEASE_MOVIE.getPriceOf(0));
        assertEquals(3, NEW_RELEASE_MOVIE.getPriceOf(1));
        assertEquals(6, NEW_RELEASE_MOVIE.getPriceOf( 2));
    }

    @Test
    void individualMoviePriceChildrenFlatRate(){
        assertEquals(1.5, CHILDRENS_MOVIE.getPriceOf(-1));
        assertEquals(1.5, CHILDRENS_MOVIE.getPriceOf(0));
        assertEquals(1.5, CHILDRENS_MOVIE.getPriceOf(1));
    }

    @Test
    void individualMoviePriceChildrenFlatRateEdgeCase(){
        assertEquals(1.5, CHILDRENS_MOVIE.getPriceOf(3));
    }

    @Test
    void individualMoviePriceChildrenProgressiveRate(){
        assertEquals(3, CHILDRENS_MOVIE.getPriceOf(4));
        assertEquals(4.5, CHILDRENS_MOVIE.getPriceOf(5));
    }

    @Test
    void frequenterPointsBasicAmount(){
        assertEquals(1, REGULAR_MOVIE.getFrequenterPoints(5));
        assertEquals(1, CHILDRENS_MOVIE.getFrequenterPoints(5));
        assertEquals(1, NEW_RELEASE_MOVIE.getFrequenterPoints( 0));
    }

    @Test
    void frequenterPointsBasicAmountEdgeCase(){
        assertEquals(1, NEW_RELEASE_MOVIE.getFrequenterPoints(1));
    }

    @Test
    void frequenterPointsExtraAmount(){
        assertEquals(2, NEW_RELEASE_MOVIE.getFrequenterPoints(2));
        assertEquals(2, NEW_RELEASE_MOVIE.getFrequenterPoints(5));
    }
}

