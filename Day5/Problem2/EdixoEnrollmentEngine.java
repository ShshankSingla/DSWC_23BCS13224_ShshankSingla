import java.sql.*;

interface RegistrationManager {
    void enrollAtRiskStudents();
}

abstract class DatabaseConnectionProvider {

    private final String url = "jdbc:postgresql://localhost:5432/edixo";
    private final String username = "postgres";
    private final String password = "postgres";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

class EdixoRegistrationRepository extends DatabaseConnectionProvider implements RegistrationManager {

    private static final String FIND_STUDENTS =
            "SELECT s.student_id, s.full_name " +
            "FROM students s " +
            "LEFT JOIN course_registrations cr " +
            "ON s.student_id = cr.student_id " +
            "WHERE cr.student_id IS NULL";

    private static final String INSERT_REGISTRATION =
            "INSERT INTO course_registrations(student_id, course_code, semester) " +
            "VALUES (?, ?, ?)";

    @Override
    public void enrollAtRiskStudents() {

        try (Connection conn = getConnection();
             PreparedStatement findStmt = conn.prepareStatement(FIND_STUDENTS);
             ResultSet rs = findStmt.executeQuery();
             PreparedStatement insertStmt = conn.prepareStatement(INSERT_REGISTRATION)) {

            int batchSize = 0;

            while (rs.next()) {

                long studentId = rs.getLong("student_id");

                insertStmt.setLong(1, studentId);
                insertStmt.setString(2, "ORIENTATION101");
                insertStmt.setString(3, "FALL2026");

                insertStmt.addBatch();
                batchSize++;

                if (batchSize % 1000 == 0) {
                    insertStmt.executeBatch();
                }
            }

            insertStmt.executeBatch();

            System.out.println("Orientation enrollment completed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class EdixoEnrollmentEngine {

    public static void main(String[] args) {

        RegistrationManager manager =
                new EdixoRegistrationRepository();

        manager.enrollAtRiskStudents();
    }
}