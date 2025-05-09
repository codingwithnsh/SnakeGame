package com.xxx.xxx; //replace the package came with yours


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager class to handle connection and score operations
 * with the MySQL database via JDBC.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/snake_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    public DatabaseManager() {
        // Initialize DB if needed (create table if not exists)
        createTableIfNotExists();
    }

    /**
     * Creates a 'scores' table if it doesn't already exist.
     * Example schema:
     *   CREATE TABLE scores (
     *       id INT AUTO_INCREMENT PRIMARY KEY,
     *       score INT NOT NULL,
     *       date_played TIMESTAMP DEFAULT CURRENT_TIMESTAMP
     *   );
     */
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS scores ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "score INT NOT NULL,"
            + "date_played TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert the player's latest score into the database.
     */
    public void saveScore(int score) {
        String insertSQL = "INSERT INTO scores (score) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve top N scores from the DB in descending order.
     */
    public List<Integer> getTopScores(int topN) {
        List<Integer> topScores = new ArrayList<>();

        String querySQL = "SELECT score FROM scores ORDER BY score DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            pstmt.setInt(1, topN);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    topScores.add(rs.getInt("score"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topScores;
    }
}
