package database;

import java.sql.*;

public abstract class TableManager {
    protected static final String DB_URL = "jdbc:postgresql://localhost:5432/HapPee";
    protected static final String DB_USER = "postgres";
    protected static final String DB_PASSWORD = "Pianoforte2000!";

    public abstract boolean insert(Object... params);

    public String getFromDatabase(String email, String column) {
        String desiredField = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT " + column + " FROM \""+ getClientType() +"\" WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                desiredField = resultSet.getString(column);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return desiredField;
    }

    public abstract String getClientType();

    public boolean emailAlreadyExists(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "SELECT COUNT(*) FROM public.\"User\" WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCommand)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
