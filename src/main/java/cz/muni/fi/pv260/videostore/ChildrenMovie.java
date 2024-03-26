package cz.muni.fi.pv260.videostore;

public class ChildrenMovie extends Movie {
    public ChildrenMovie(String title) {
        super(title);
    }

    @Override
    public double getPriceOf(int daysRented) {
        double price = 1.5;
        if (daysRented > 3){
            price += (daysRented - 3) * 1.5;
        }

        return price;

    }
}
