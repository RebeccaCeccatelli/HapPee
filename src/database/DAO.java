package database;

import java.sql.*;

public abstract class DAO {
    protected static final String DB_URL = "jdbc:postgresql://localhost:5432/HapPee";
    protected static final String DB_USER = "postgres";
    protected static final String DB_PASSWORD = "Pianoforte2000!";

    public abstract boolean addRow(Object... params);

    abstract String getTableName();

    public void update(int id, String column, Object value) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "UPDATE \"" + getTableName() + "\" SET " + column + " = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setObject(1, value);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public int getAccountIdByEmail(String email) {
        int id = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id FROM \"" + getTableName() + "\" WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getIntFromDB(int id, String column) {
        int desiredField = -1;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT " + column + " FROM \"" + getTableName() + "\" WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                desiredField = resultSet.getInt(column);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desiredField;
    }

    public float getFLoatFromDB(int id, String column) {
        float desiredField = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT " + column + " FROM \"" + getTableName() + "\" WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                desiredField = resultSet.getFloat(column);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desiredField;
    }

    public Time getTimeFromDB(int id, String column) {
        Time desiredField = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT " + column + " FROM \"" + getTableName() + "\" WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                desiredField = resultSet.getTime(column);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desiredField;
    }

    public String getStringFromDB(int id, String column) {
        String desiredField = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT " + column + " FROM \"" + getTableName() + "\" WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

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

    public int getMaxId() {
        int maxId = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT MAX(id) AS max_id FROM \"" + getTableName() + "\"";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                maxId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }

}