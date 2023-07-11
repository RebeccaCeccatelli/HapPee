package database;

import backend.Address;

import java.sql.*;
import java.util.HashMap;

public class AddressDAO extends DAO {
    private int addressId;

    @Override
    public boolean addRow(Object... params) {
        Address address = (Address) params[0];
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "INSERT INTO \"Address\" (street, civic_number, postcode, city, country) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCivicNumber());
            statement.setString(3, address.getPostCode());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getCountry());

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    addressId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    String getTableName() {
        return "Address";
    }

    int getAddressId(){
        return addressId;
    }

    public HashMap<Integer, Address> getAllAddresses() {
        HashMap<Integer, Address> addressMap = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM \"Address\"";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int addressId = resultSet.getInt("id");
                String street = resultSet.getString("street");
                String civicNumber = resultSet.getString("civic_number");
                String postCode = resultSet.getString("postcode");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");

                Address address = new Address(street, civicNumber, postCode, city, country);
                addressMap.put(addressId, address);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressMap;
    }

    public Address getAddressByBusinessId(int businessId) {
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
}
