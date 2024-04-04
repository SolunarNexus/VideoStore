package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class CustomerStatementTest {

    private Customer customer = new Customer("John Doe");

    @Test
    void emptyCustomerName() {
        customer = new Customer("");

        assertStatement("""
                Rental Record for\s
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void nullCustomerName() {
        customer = new Customer(null);

        assertStatement("""
                Rental Record for null
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void unicodeCustomerName() {
        customer = new Customer("František Vopršálek");

        assertStatement("""
                Rental Record for František Vopršálek
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void emptyMovieName() {
        customer.addRental(new Rental(new RegularMovie("") {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

        assertStatement("""
                Rental Record for John Doe
                		5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovieName() {
        customer.addRental(new Rental(new RegularMovie(null) {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

        assertStatement("""
                Rental Record for John Doe
                	null	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void unicodeMovieName() {
        customer.addRental(new Rental(new RegularMovie("Příběhy obyčejného šílenství") {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

        assertStatement("""
                Rental Record for John Doe
                	Příběhy obyčejného šílenství	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void NegativePrice() {
        customer.addRental(new Rental(new RegularMovie("New Release Movie"){
            @Override
            public double getPriceOf(int daysRented) {
                return -30;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, -10));

        assertStatement("""
                Rental Record for John Doe
                	New Release Movie	-30.0
                You owed -30.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovie() {
        customer.addRental(new Rental(null, 4));

        assertThrows(NullPointerException.class, () -> new Statement(customer).format());
    }

    @Test
    void nullRental() {
        customer.addRental(null);

        assertThrows(NullPointerException.class, () -> new Statement(customer).format());
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
        customer.addRental(new Rental(new RegularMovie("Movie") {
            @Override
            public double getPriceOf(int daysRented) {
                return 1.5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

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
        customer.addRental(new Rental(new RegularMovie("") {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

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
        customer.addRental(new Rental(new RegularMovie(null) {
            @Override
            public double getPriceOf(int daysRented) {
                return 1.5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

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
        customer.addRental(new Rental(new RegularMovie("Příběhy obyčejného šílenství") {
            @Override
            public double getPriceOf(int daysRented) {
                return 5;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, 4));

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
        customer.addRental(new Rental(new RegularMovie("New Release Movie"){
            @Override
            public double getPriceOf(int daysRented) {
                return -30;
            }

            @Override
            int getFrequenterPoints(int daysRented) {
                return 1;
            }
        }, -10));

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

    private void assertStatement(String expectedStatement) {
        assertEquals(expectedStatement, new Statement(customer).format());
    }

    private void assertHtmlStatement(String expectedStatement) {
        assertEquals(expectedStatement, new Statement(customer).htmlStatement());
    }
}
