package cz.muni.fi.pv260.videostore;

public class RegularMovie extends Movie {

    public RegularMovie(String title) {
        super(title);
    }

    @Override
    public double getPriceOf(int daysRented) {
        double price = 2;
        if (daysRented > 2)
            price += (daysRented - 2) * 1.5;
        return price;
    }
}
