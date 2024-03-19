package cz.muni.fi.pv260.videostore.movie;

public class RegularMovie extends Movie {

    private static final double PRICE_PER_DAY = 1.5;
    private static final int BASE_RENT_PRICE = 2;
    private static final int BASE_RENT_TIME = 2;

    public RegularMovie(String title) {
        super(title);
    }

    @Override
    public double getRentalPrice(int daysRented) {
        return BASE_RENT_PRICE + Integer.max(0, daysRented - BASE_RENT_TIME) * PRICE_PER_DAY;
    }

    @Override
    public boolean countsToFrequentPoints() {
        return false;
    }
}
