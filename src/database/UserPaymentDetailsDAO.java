package database;

import java.sql.*;

public class UserPaymentDetailsDAO extends DAO {

    @Override
    public boolean addRow(Object... params) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "INSERT INTO \"UserPaymentDetails\" (user_id) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setInt(1, (int) params[0]);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    String getTableName() {
        return "UserPaymentDetails";
    }

    public int getIdByUserId(int userId){
        int desiredField = -1;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id FROM \"" + getTableName() + "\" WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                desiredField = resultSet.getInt("id");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desiredField;
    }

    //implemented for testing purposes
    public void deleteTestUserPaymentDetails(int userId) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM \"UserPaymentDetails\" WHERE user_id = ?")) {

            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
