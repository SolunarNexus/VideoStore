package cz.muni.fi.pv260.videostore.movie;

public class ChildrenMovie extends Movie {

    private static final double PRICE_PER_DAY = 1.5;
    private static final int BASE_RENT_TIME = 3;

    public ChildrenMovie(String title) {
        super(title);
    }

    @Override
    public double getRentalPrice(int daysRented) {
        return Integer.max(1, daysRented - BASE_RENT_TIME + 1) * PRICE_PER_DAY;
    }

    @Override
    public boolean countsToFrequentPoints() {
        return false;
    }
}
