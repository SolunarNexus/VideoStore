package cz.muni.fi.pv260.videostore;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {
    private static final Movie REGULAR_MOVIE = new RegularMovie("Regular Movie");
    private static final Movie NEW_RELEASE_MOVIE = new NewReleaseMovie("New Release Movie");
    private static final Movie CHILDRENS_MOVIE = new ChildrenMovie("Children's Movie");

    @Test
    void individualMoviePriceRegularFlatRate(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(REGULAR_MOVIE.getPriceOf(-1)).isEqualTo(2);
        softly.assertThat(REGULAR_MOVIE.getPriceOf(0)).isEqualTo(2);
        softly.assertThat(REGULAR_MOVIE.getPriceOf(1)).isEqualTo(2);
        softly.assertAll();
    }

    @Test
    void individualMoviePriceRegularFlatRateEdgeCase(){
        assertEquals(2, REGULAR_MOVIE.getPriceOf(2));
    }

    @Test
    void individualMoviePriceRegularProgressiveRate(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(REGULAR_MOVIE.getPriceOf(3)).isEqualTo(3.5);
        softly.assertThat(REGULAR_MOVIE.getPriceOf(4)).isEqualTo(5);
        softly.assertAll();
    }

    @Test
    void individualMoviePriceNewRelease(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(NEW_RELEASE_MOVIE.getPriceOf(-1)).isEqualTo(-3);
        softly.assertThat(NEW_RELEASE_MOVIE.getPriceOf(0)).isEqualTo(0);
        softly.assertThat(NEW_RELEASE_MOVIE.getPriceOf(1)).isEqualTo(3);
        softly.assertThat(NEW_RELEASE_MOVIE.getPriceOf(2)).isEqualTo(6);
        softly.assertAll();
    }

    @Test
    void individualMoviePriceChildrenFlatRate(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(CHILDRENS_MOVIE.getPriceOf(-1)).isEqualTo(1.5);
        softly.assertThat(CHILDRENS_MOVIE.getPriceOf(0)).isEqualTo(1.5);
        softly.assertThat(CHILDRENS_MOVIE.getPriceOf(1)).isEqualTo(1.5);
        softly.assertAll();
    }

    @Test
    void individualMoviePriceChildrenFlatRateEdgeCase(){
        assertEquals(1.5, CHILDRENS_MOVIE.getPriceOf(3));
    }

    @Test
    void individualMoviePriceChildrenProgressiveRate(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(CHILDRENS_MOVIE.getPriceOf(4)).isEqualTo(3);
        softly.assertThat(CHILDRENS_MOVIE.getPriceOf(5)).isEqualTo(4.5);
        softly.assertAll();
    }

    @Test
    void frequenterPointsBasicAmount(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(REGULAR_MOVIE.getFrequenterPoints(5)).isEqualTo(1);
        softly.assertThat(CHILDRENS_MOVIE.getFrequenterPoints(5)).isEqualTo(1);
        softly.assertThat(NEW_RELEASE_MOVIE.getFrequenterPoints(0)).isEqualTo(1);
        softly.assertAll();
    }

    @Test
    void frequenterPointsBasicAmountEdgeCase(){
        assertEquals(1, NEW_RELEASE_MOVIE.getFrequenterPoints(1));
    }

    @Test
    void frequenterPointsExtraAmount(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(NEW_RELEASE_MOVIE.getFrequenterPoints(2)).isEqualTo(2);
        softly.assertThat(NEW_RELEASE_MOVIE.getFrequenterPoints(5)).isEqualTo(2);
        softly.assertAll();
    }

    @Test
    void newReleaseMovieNegativeDays() {
        assertEquals(-30,NEW_RELEASE_MOVIE.getPriceOf(-10));
    }

    @Test
    void childrensMovieNegativeDays() {
        assertEquals(1.5,CHILDRENS_MOVIE.getPriceOf(-10));
    }
}

