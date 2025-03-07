package cz.muni.fi.pv260.videostore;

public class NewReleaseMovie extends Movie {

    public NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public double getPriceOf(int daysRented) {
        return daysRented * 3;
    }

    @Override
    int getFrequenterPoints(int daysRented) {
        return daysRented > 1 ? 2 : 1;
    }
}
