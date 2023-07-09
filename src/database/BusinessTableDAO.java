package database;

import java.sql.*;
import backend.Address;

public class BusinessTableDAO extends DAO {

    public boolean addNewRow(Object... params) {
        AddressDAO addressTableManager = new AddressDAO();
        addressTableManager.addNewRow(params[1]);
        int addressId = addressTableManager.getAddressId();

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

    public boolean checkBusinessExistence(String email, String password) {
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

    public int checkBusinessName(String name) {
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

    public int getBusinessIdFromAddressId(int addressId) {
        int businessId = 0;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT id FROM \"Business\" WHERE address_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, addressId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    businessId = resultSet.getInt("id");
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return businessId;
    }

    public Address getAddressFromDatabase(int businessId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Address address = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT b.id, a.street, a.civic_number, a.postcode, a.city, a.country " +
                    "FROM \"Business\" b " +
                    "JOIN \"Address\" a ON b.address_id = a.id " +
                    "WHERE b.id = ?";

            statement = connection.prepareStatement(query);
            statement.setInt(1, businessId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String street = resultSet.getString("street");
                String civicNumber = resultSet.getString("civic_number");
                String postCode = resultSet.getString("postcode");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");

                address = new Address(street, civicNumber, postCode, city, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return address;
    }

    public String getTableName() {
        return "Business";
    }
}
