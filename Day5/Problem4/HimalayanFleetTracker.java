import java.sql.*;
import java.time.LocalDateTime;

interface TelemetryService {
    void printLatestLocations();
}

abstract class FleetDatabaseConnection {

    private final String url = "jdbc:postgresql://localhost:5432/fleetdb";
    private final String username = "postgres";
    private final String password = "postgres";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

class FleetRepository extends FleetDatabaseConnection implements TelemetryService {

    private static final String QUERY =
            "WITH LatestPings AS (" +
            " SELECT gp.ping_id, gp.rider_id, gp.latitude, gp.longitude, gp.recorded_at," +
            " ROW_NUMBER() OVER (PARTITION BY gp.rider_id ORDER BY gp.recorded_at DESC) AS rn" +
            " FROM gps_pings gp" +
            ")" +
            " SELECT r.rider_id, r.rider_name, r.bike_model," +
            " lp.latitude, lp.longitude, lp.recorded_at" +
            " FROM riders r" +
            " INNER JOIN LatestPings lp ON r.rider_id = lp.rider_id" +
            " WHERE lp.rn = 1";

    @Override
    public void printLatestLocations() {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                long riderId = rs.getLong("rider_id");
                String riderName = rs.getString("rider_name");
                String bikeModel = rs.getString("bike_model");

                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                LocalDateTime recordedAt =
                        rs.getObject("recorded_at", LocalDateTime.class);

                System.out.println(
                        riderId + " | " +
                        riderName + " | " +
                        bikeModel + " | " +
                        latitude + ", " +
                        longitude + " | " +
                        recordedAt
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class HimalayanFleetTracker {

    public static void main(String[] args) {

        TelemetryService service = new FleetRepository();
        service.printLatestLocations();
    }
}