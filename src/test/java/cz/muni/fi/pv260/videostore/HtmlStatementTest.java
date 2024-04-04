package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class HtmlStatementTest {

    private Customer customer = new Customer("John Doe");

    @Test
    void nullMovie() {
        customer.addRental(new Rental(null, 4));

        assertThrows(NullPointerException.class, () -> new HtmlStatement(customer).format());
    }

    @Test
    void nullRental() {
        customer.addRental(null);

        assertThrows(NullPointerException.class, () -> new HtmlStatement(customer).format());
    }

    @Test
    void noRentalsHtmlStatement() {
        assertHtmlStatement("""
                <h1>Rentals for <em>John Doe</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <th> You owe <td> 0.0
                </table>
                <p>On this rental you earned <strong>0</strong> frequent renter
                points</p>""");
    }

    @Test
    void singleRentalHtmlStatement() {
        customer.addRental(new Rental(getBasicMovie("Movie", 1.5), 4));

        assertHtmlStatement("""
                <h1>Rentals for <em>John Doe</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <td> Movie <td> 1.5
                  <tr> <th> You owe <td> 1.5
                </table>
                <p>On this rental you earned <strong>1</strong> frequent renter
                points</p>""");
    }

    @Test
    void emptyCustomerNameHtml() {
        customer = new Customer("");

        assertHtmlStatement("""
                <h1>Rentals for <em></em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <th> You owe <td> 0.0
                </table>
                <p>On this rental you earned <strong>0</strong> frequent renter
                points</p>""");
    }

    @Test
    void nullCustomerNameHtml() {
        customer = new Customer(null);

        assertHtmlStatement("""
                <h1>Rentals for <em>null</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <th> You owe <td> 0.0
                </table>
                <p>On this rental you earned <strong>0</strong> frequent renter
                points</p>""");
    }

    @Test
    void unicodeCustomerNameHtml() {
        customer = new Customer("František Vopršálek");

        assertHtmlStatement("""
                <h1>Rentals for <em>František Vopršálek</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <th> You owe <td> 0.0
                </table>
                <p>On this rental you earned <strong>0</strong> frequent renter
                points</p>""");
    }

    @Test
    void emptyMovieNameHtml() {
        customer.addRental(new Rental(getBasicMovie("", 5), 4));

        assertHtmlStatement("""
                <h1>Rentals for <em>John Doe</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <td>  <td> 5.0
                  <tr> <th> You owe <td> 5.0
                </table>
                <p>On this rental you earned <strong>1</strong> frequent renter
                points</p>""");
    }

    @Test
    void nullMovieNameHtml() {
        customer.addRental(new Rental(getBasicMovie(null, 1.5), 4));

        assertHtmlStatement("""
                <h1>Rentals for <em>John Doe</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <td> null <td> 1.5
                  <tr> <th> You owe <td> 1.5
                </table>
                <p>On this rental you earned <strong>1</strong> frequent renter
                points</p>""");
    }

    @Test
    void unicodeMovieNameHtml() {
        customer.addRental(new Rental(getBasicMovie("Příběhy obyčejného šílenství", 5), 4));

        assertHtmlStatement("""
                <h1>Rentals for <em>John Doe</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <td> Příběhy obyčejného šílenství <td> 5.0
                  <tr> <th> You owe <td> 5.0
                </table>
                <p>On this rental you earned <strong>1</strong> frequent renter
                points</p>""");
    }

    @Test
    void NegativePriceHtml() {
        customer.addRental(new Rental(getBasicMovie("New Release Movie", -30), -10));

        assertHtmlStatement("""
                <h1>Rentals for <em>John Doe</em></h1>
                <table>
                <thead>
                  <tr> <th> Movie <th> Price
                <tbody>
                  <tr> <td> New Release Movie <td> -30.0
                  <tr> <th> You owe <td> -30.0
                </table>
                <p>On this rental you earned <strong>1</strong> frequent renter
                points</p>""");
    }

    private Movie getBasicMovie(String title, double price){
        return new RegularMovie(title){
            @Override
            public double getPriceOf(int daysRented) {
                return price;
            }
        };
    }

    private void assertHtmlStatement(String expectedStatement) {
        assertEquals(expectedStatement, new HtmlStatement(customer).format());
    }
}
