package database;

import backend.Address;

import java.sql.*;

public class AddressTableManager extends TableManager {
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
}
