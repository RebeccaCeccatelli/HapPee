package database;

import java.sql.*;

public class BusinessDetailsDAO extends DAO {

    public boolean add(Object... params) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "INSERT INTO \"BusinessDetails\" (" + params[0] +") VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            if (params[1] instanceof Integer) {
                statement.setInt(1, (Integer) params[1]);
            }
            else if (params[1] instanceof String) {
                statement.setString(1, (String) params[1]);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    String getTableName() {
        return "BusinessDetails";
    }

    public int getIdByBusinessId(int businessId){
        int desiredField = -1;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id FROM \"" + getTableName() + "\" WHERE business_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, businessId);

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
    public void deleteBusinessDetails(int businessId) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM \"BusinessDetails\" WHERE business_id = ?")) {

            statement.setInt(1, businessId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
