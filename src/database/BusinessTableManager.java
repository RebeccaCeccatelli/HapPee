package database;

import java.sql.*;
import backend.Address;

public class BusinessTableManager extends TableManager {

    public Address getAddressFromDatabase(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Address address = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT b.email, a.street, a.civic_number, a.postcode, a.city, a.country " +
                    "FROM \"Business\" b " +
                    "JOIN \"Address\" a ON b.address_id = a.id " +
                    "WHERE b.email = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, email);

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

    public boolean insert(Object... params) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            connection.setAutoCommit(false);

            String addressSql = "INSERT INTO \"Address\"  (street, civic_number, postcode, city, country) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement addressStatement = connection.prepareStatement(addressSql, PreparedStatement.RETURN_GENERATED_KEYS);
            Address address = (Address) params[1];
            addressStatement.setString(1, address.street());
            addressStatement.setString(2, address.civicNumber());
            addressStatement.setString(3, address.postCode());
            addressStatement.setString(4, address.city());
            addressStatement.setString(5, address.country());
            addressStatement.executeUpdate();

            ResultSet generatedKeys = addressStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int addressId = generatedKeys.getInt(1);

                // Insert Business record
                String businessSql = "INSERT INTO \"Business\"  (name, address_id, email, password) " +
                        "VALUES (?, ?, ?, ?)";
                PreparedStatement businessStatement = connection.prepareStatement(businessSql);
                businessStatement.setString(1, (String) params[0]);
                businessStatement.setInt(2, addressId);
                businessStatement.setString(3, (String) params[2]);
                businessStatement.setString(4, (String) params[3]);
                businessStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Handle any potential exceptions
        }
        return true;
    }

    public boolean checkBusiness(String email, String password) {
        boolean result = false;
        String query = "SELECT * FROM \"Business\" WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = true; // Trovato corrispondenza
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getClientType() {
        return "Business";
    }
}
