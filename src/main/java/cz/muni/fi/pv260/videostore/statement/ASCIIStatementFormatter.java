package cz.muni.fi.pv260.videostore.statement;

public class ASCIIStatementFormatter implements StatementFormatter {

    @Override
    public String formatStatement(Statement statement) {
        StringBuilder result = new StringBuilder("Rental Record for " + statement.getCustomerName() + "\n");

        statement.getMoviePrices().forEach(p ->
                result.append("\t")
                        .append(p.getKey())
                        .append("\t")
                        .append(p.getValue())
                        .append("\n"));

        result.append("You owed ")
                .append(statement.getTotalRentalPrice())
                .append("\n");

        result.append("You earned ")
                .append(statement.getFrequenterPoints())
                .append(" frequent renter points\n");

        return result.toString();
    }
}
