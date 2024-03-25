package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


public class VideoStoreTest {
    String getNameFromResult(String result){
        Pattern pattern = Pattern.compile("Rental Record for (.*)\n");
        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    ArrayList<String> getMovieTitleFromResult(String result){
        Pattern pattern = Pattern.compile("\t(.*)\t.*\n");
        Matcher matcher = pattern.matcher(result);
        ArrayList<String> titles = new ArrayList<>();

        while (matcher.find()){
            titles.add(matcher.group(1));
        }

        return titles;
    }

    ArrayList<String> getAmountFromResult(String result){
        Pattern pattern = Pattern.compile("\t.*\t(.*)\n");
        Matcher matcher = pattern.matcher(result);
        ArrayList<String> amounts = new ArrayList<>();

        while (matcher.find()){
            amounts.add(matcher.group(1));
        }

        return amounts;
    }

    @Test
    void testCorrectMovieTitles(){
        Customer c = new Customer("");
        Movie m1 = new Movie("Shrek 1", 0);
        Movie m2 = new Movie("Shrek 2", 0);
        c.addRental(new Rental(m1, 1));
        c.addRental(new Rental(m2, 1));

        String result = c.statement();
        ArrayList<String> titles = getMovieTitleFromResult(result);
        String[] expected = {"Shrek 1", "Shrek 2"};

        for (int i = 0; i < expected.length; i++){
            assertThat(titles.get(i)).isEqualTo(expected[i]);
        }
    }

    @Test
    void testCorrectCustomerName(){
        String name = "John";
        Customer c = new Customer(name);
        Movie m1 = new Movie("", 0);
        c.addRental(new Rental(m1, 1));

        String result = c.statement();

        assertThat(getNameFromResult(result)).isEqualTo(name);
    }

    @Test
    void testCorrectAmountsRegularMovies(){}

    @Test
    void testCorrectAmountsNewReleaseMovies(){}

    @Test
    void testCorrectAmountsChildrenMovies(){}

    @Test
    void testValidPriceCode(){}

    @Test
    void testRenterPointsRegularMovies(){}

    @Test
    void testRenterPointsNewReleaseMovies(){}

    @Test
    void testRenterPointsChildrenMovies(){}
}
