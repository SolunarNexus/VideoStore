package cz.muni.fi.pv260.videostore;

public class NewReleaseMovie extends Movie {

    public NewReleaseMovie(String title) {
        super(title, Movie.NEW_RELEASE);
    }

    @Override
    public double getPriceOf(int daysRented) {
        return daysRented * 3;
    }
}
