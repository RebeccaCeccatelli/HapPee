package database;


import java.sql.*;

public class BusinessDetailsTableManager extends TableManager{


    public int getIdFromBusinessId(int businessId){
        int desiredField = -1;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id FROM \"" + getClientType() + "\" WHERE business_id = ?";
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

    public String getClientType() {
        return "BusinessDetails";
    }

    public boolean update(int businessId, String newBusinessType) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "UPDATE \"BusinessDetails\" SET business_type = ? WHERE business_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setString(1, newBusinessType);
            statement.setInt(2, businessId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error during Update operation in BusinessDetails table: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTime(int businessId, Time time, String type) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "UPDATE \"BusinessDetails\" SET " + type + " = ? WHERE business_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setTime(1, time);
            statement.setInt(2, businessId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error during Update operation in BusinessDetails table: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAccessPrice(int businessId, float accessPrice) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "UPDATE \"BusinessDetails\" SET single_access_price = ? WHERE business_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setFloat(1, accessPrice);
            statement.setInt(2, businessId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error during Update operation in BusinessDetails table: " + e.getMessage());
            return false;
        }
    }

    public boolean addNewRow(Object... params) { //column of the database, value
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
            System.out.println("Error during Insert operation in User table: " + e.getMessage());
            return false;
        }
        return true;
    }
}
