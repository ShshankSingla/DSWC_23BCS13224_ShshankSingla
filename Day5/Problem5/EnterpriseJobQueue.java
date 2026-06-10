import java.sql.*;

interface QueueWorker {
    void processNextJob();
}

abstract class EnterpriseConnectionFactory {

    private final String url = "jdbc:postgresql://localhost:5432/enterprise";
    private final String username = "postgres";
    private final String password = "postgres";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

class JobQueueRepository extends EnterpriseConnectionFactory implements QueueWorker {

    private static final String FETCH_JOB =
            "SELECT bj.job_id, d.dept_name, bj.created_at " +
            "FROM background_jobs bj " +
            "INNER JOIN departments d ON bj.dept_id = d.dept_id " +
            "WHERE d.dept_name = ? " +
            "AND bj.status = ? " +
            "ORDER BY bj.created_at " +
            "FOR UPDATE SKIP LOCKED " +
            "LIMIT 1";

    private static final String UPDATE_JOB =
            "UPDATE background_jobs " +
            "SET status = ? " +
            "WHERE job_id = ?";

    @Override
    public void processNextJob() {

        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false);

            try {

                Long jobId = null;

                try (PreparedStatement fetchStmt = conn.prepareStatement(FETCH_JOB)) {

                    fetchStmt.setString(1, "Engineering");
                    fetchStmt.setString(2, "PENDING");

                    try (ResultSet rs = fetchStmt.executeQuery()) {

                        if (rs.next()) {
                            jobId = rs.getLong("job_id");
                        }
                    }
                }

                if (jobId == null) {
                    System.out.println("No pending jobs found.");
                    conn.commit();
                    return;
                }

                try (PreparedStatement updateStmt = conn.prepareStatement(UPDATE_JOB)) {

                    updateStmt.setString(1, "PROCESSING");
                    updateStmt.setLong(2, jobId);

                    updateStmt.executeUpdate();
                }

                conn.commit();

                System.out.println("Processing Job ID: " + jobId);

            } catch (Exception e) {

                conn.rollback();
                throw e;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

public class EnterpriseJobQueue {

    public static void main(String[] args) {

        QueueWorker worker = new JobQueueRepository();
        worker.processNextJob();
    }
}