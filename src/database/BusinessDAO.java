package database;

import java.sql.*;

import backend.Business;

public class BusinessDAO extends DAO {

    public boolean addRow(Object... params) {
        AddressDAO addressDAO = new AddressDAO();
        addressDAO.addRow(params[1]);
        int addressId = addressDAO.getAddressId();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "INSERT INTO \"Business\" (name, address_id, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setString(1, (String) params[0]);
            statement.setInt(2, addressId);
            statement.setString(3, (String) params[2]);
            statement.setString(4, (String) params[3]);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    String getTableName() {
        return "Business";
    }

    public boolean checkIfBusinessRegistered(String email, String password) {
        boolean result = false;
        String query = "SELECT * FROM \"Business\" WHERE email = ? AND password = ?";

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

    public int getIdByBusinessName(String name) {
        int id = -1;
        String query = "SELECT * FROM \"Business\" WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Business getBusinessByBusinessId(int businessId) {
        return new Business(businessId);
    }

    public Business getBusinessByAddressId(int addressId) {
        Business business = null;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT id FROM \"Business\" WHERE address_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, addressId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int businessId = resultSet.getInt("id");
                    business = new Business(businessId);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return business;
    }

    //implemented for testing purposes
    public void deleteTestBusiness(String email) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM \"Business\" WHERE email = ?")) {

            statement.setString(1, email);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
