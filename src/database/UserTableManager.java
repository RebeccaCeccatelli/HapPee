package database;

import java.sql.*;

public class UserTableManager extends TableManager {

    public String getTableName() {
        return "User";
    }

    public boolean addNewRow(Object... params) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "INSERT INTO \"User\" (name, surname, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setString(1, (String) params[0]);
            statement.setString(2, (String) params[1]);
            statement.setString(3, (String) params[2]);
            statement.setString(4, (String) params[3]);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean checkUserExistence(String email, String password) {
        boolean result = false;
        String query = "SELECT * FROM \"User\" WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
