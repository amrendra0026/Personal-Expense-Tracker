import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String DB_FILE =
            new File(AppPaths.userDataDirectory(),
                    "expense_tracker").getAbsolutePath();

    private static final String URL =
            "jdbc:h2:file:" + DB_FILE.replace('\\', '/')
                    + ";MODE=MySQL"
                    + ";DATABASE_TO_LOWER=TRUE"
                    + ";DEFAULT_NULL_ORDERING=HIGH";

    static {
        try {
            Class.forName("org.h2.Driver");
            initializeDatabase();

        } catch (Exception e) {
            throw new ExceptionInInitializerError(
                    "Unable to initialize local Expense Tracker database: "
                            + e.getMessage()
            );
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, "sa", "");
    }

    private static void initializeDatabase() throws Exception {

        try (Connection c = getConnection();
             Statement s = c.createStatement()) {

            s.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(100) NOT NULL," +
                            "email VARCHAR(100) UNIQUE NOT NULL," +
                            "phone VARCHAR(20)," +
                            "password VARCHAR(255) NOT NULL," +
                            "photo VARCHAR(1000)," +
                            "role VARCHAR(20) NOT NULL DEFAULT 'USER'" +
                            ")"
            );

            s.execute(
                    "CREATE TABLE IF NOT EXISTS transactions (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "user_id INT NOT NULL," +
                            "type VARCHAR(20) NOT NULL," +
                            "category VARCHAR(100) NOT NULL," +
                            "amount DECIMAL(10,2) NOT NULL," +
                            "description VARCHAR(255)," +
                            "transaction_date DATE NOT NULL," +
                            "CONSTRAINT fk_transactions_user " +
                            "FOREIGN KEY (user_id) REFERENCES users(id)" +
                            ")"
            );
        }
    }
}
