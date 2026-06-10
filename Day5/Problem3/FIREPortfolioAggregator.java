import java.sql.*;

interface PortfolioManager {
    void restructurePortfolio(long investorId);
}

abstract class FinancialDatabaseConfig {

    private final String url = "jdbc:postgresql://localhost:5432/firedb";
    private final String username = "postgres";
    private final String password = "postgres";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

class PortfolioRepository extends FinancialDatabaseConfig implements PortfolioManager {

    private static final String PORTFOLIO_QUERY =
            "SELECT h.asset_class, SUM(h.current_value) AS total_value " +
            "FROM investors i " +
            "INNER JOIN holdings h ON i.investor_id = h.investor_id " +
            "WHERE i.investor_id = ? " +
            "GROUP BY h.asset_class";

    private static final String DEBT_UPDATE =
            "UPDATE holdings SET current_value = current_value - ? " +
            "WHERE investor_id = ? AND asset_class = 'Debt'";

    private static final String EQUITY_UPDATE =
            "UPDATE holdings SET current_value = current_value + ? " +
            "WHERE investor_id = ? AND asset_class = 'Equity'";

    @Override
    public void restructurePortfolio(long investorId) {

        double transferAmount = 50000.0;

        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false);

            try {

                try (PreparedStatement ps = conn.prepareStatement(PORTFOLIO_QUERY)) {

                    ps.setLong(1, investorId);

                    try (ResultSet rs = ps.executeQuery()) {

                        System.out.println("Current Portfolio:");

                        while (rs.next()) {
                            String assetClass = rs.getString("asset_class");
                            double totalValue = rs.getDouble("total_value");

                            System.out.println(assetClass + " : " + totalValue);
                        }
                    }
                }

                try (PreparedStatement debtStmt = conn.prepareStatement(DEBT_UPDATE);
                     PreparedStatement equityStmt = conn.prepareStatement(EQUITY_UPDATE)) {

                    debtStmt.setDouble(1, transferAmount);
                    debtStmt.setLong(2, investorId);
                    debtStmt.executeUpdate();

                    equityStmt.setDouble(1, transferAmount);
                    equityStmt.setLong(2, investorId);
                    equityStmt.executeUpdate();
                }

                conn.commit();
                System.out.println("Portfolio restructuring completed.");

            } catch (Exception e) {

                conn.rollback();
                System.out.println("Transaction rolled back.");

                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class FIREPortfolioAggregator {

    public static void main(String[] args) {

        PortfolioManager manager = new PortfolioRepository();
        manager.restructurePortfolio(101);
    }
}