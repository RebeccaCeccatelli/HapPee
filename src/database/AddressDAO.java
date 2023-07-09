package database;

import backend.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AddressDAO extends DAO {
    private int addressId;

    @Override
    public String getTableName() {
        return "Address";
    }

    @Override
    public boolean addNewRow(Object... params) {
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

    public int getAddressId(){
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

    public ArrayList<Integer> getAddressesId() {
        ArrayList<Integer> addressesId = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT id FROM \"Address\"";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int addressId = resultSet.getInt("id");
                addressesId.add(addressId);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressesId;
    }
}
