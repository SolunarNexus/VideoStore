package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerStatementTest {

    @Test
    void noRentals() {
        var customer = new Customer("John Doe");
        String expectedStatement = """
                Rental Record for John Doe
                You owed 0.0
                You earned 0 frequent renter points
                """;
        assertEquals(expectedStatement, customer.statement());
    }
}
