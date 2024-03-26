package cz.muni.fi.pv260.videostore;

public class Movie
{
    public static final int CHILDRENS   = 2;
    public static final int REGULAR     = 0;
    public static final int NEW_RELEASE = 1;

    public Movie (String title, int priceCode) {
        this.title      = title;
        this.priceCode  = priceCode;
    }

    public double getPriceOf(int daysRented){
        double price;

        switch (getPriceCode()){
            case Movie.REGULAR:
                price = 2;
                if (daysRented > 2)
                    price += (daysRented - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                price = daysRented * 3;
                break;
            case Movie.CHILDRENS:
                price = 1.5;
                if (daysRented > 3)
                    price += (daysRented - 3) * 1.5;
                break;
            default:
                return 0;
        }
        return price;
    }

    public int getPriceCode () {
        return priceCode;
    }

    public String getTitle () {
        return title;
    }

    private String title;
    private int priceCode;
}
