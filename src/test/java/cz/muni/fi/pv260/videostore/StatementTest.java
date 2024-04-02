package cz.muni.fi.pv260.videostore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class StatementTest {

    private static final String CUSTOMER_NAME = "John Doe";

    @Test
    void emptyCustomerName() {
        var statement = new Statement.StatementBuilder()
                .setCustomerName("")
                .build();

        assertASCIIFormatStatement(statement,"""
                Rental Record for\s
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void nullCustomerName() {
        var statement = new Statement.StatementBuilder()
                .setCustomerName(null)
                .build();

        assertASCIIFormatStatement(statement, """
                Rental Record for null
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void unicodeCustomerName() {
        var statement = new Statement.StatementBuilder()
                .setCustomerName("František Vopršálek")
                .build();

        assertASCIIFormatStatement(statement, """
                Rental Record for František Vopršálek
                You owed 0.0
                You earned 0 frequent renter points
                """);
    }

    @Test
    void emptyMovieName() {
        var statement = new Statement.StatementBuilder()
                .setCustomerName(CUSTOMER_NAME)
                .addRental("", 5.0, 1)
                .build();

        assertASCIIFormatStatement(statement, """
                Rental Record for John Doe
                		5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovieName() {
        var statement = new Statement.StatementBuilder()
                .setCustomerName(CUSTOMER_NAME)
                .addRental(null, 5.0, 1)
                .build();

        assertASCIIFormatStatement(statement, """
                Rental Record for John Doe
                	null	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void unicodeMovieName() {
        var statement = new Statement.StatementBuilder()
                .setCustomerName(CUSTOMER_NAME)
                .addRental("Příběhy obyčejného šílenství", 5.0, 1)
                .build();

        assertASCIIFormatStatement(statement, """
                Rental Record for John Doe
                	Příběhy obyčejného šílenství	5.0
                You owed 5.0
                You earned 1 frequent renter points
                """);
    }

    @Test
    void nullMovie() {
        var builder = new Statement.StatementBuilder()
                .setCustomerName(CUSTOMER_NAME);

        assertThrows(NullPointerException.class, () -> builder.addRental(new Rental(null, 4)));
    }

    @Test
    void nullRental() {
        var builder = new Statement.StatementBuilder()
                .setCustomerName(CUSTOMER_NAME);

        assertThrows(NullPointerException.class, () -> builder.addRental(null));
    }

    private void assertASCIIFormatStatement(Statement statement, String expectedStatement) {
        assertEquals(expectedStatement, statement.toASCIIFormat());
    }
}
