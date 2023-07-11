package database;

import backend.Review;

import java.sql.*;
import java.util.ArrayList;

public class ReviewDAO extends DAO {

    public boolean addRow(Object... params) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlCommand = "INSERT INTO \"Review\"" +
                    "(user_id, business_id, text, rating, date, time) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlCommand);

            statement.setInt(1, (int) params[0]);
            statement.setInt(2, (int) params[1]);
            statement.setString(3, (String) params[2]);
            statement.setFloat(4, (float) params[3]);
            statement.setDate(5, (Date) params[4]);
            statement.setTime(6, (Time) params[5]);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    String getTableName() {
        return "Review";
    }

    public ArrayList<Review> getReviewsByAccountId(int accountId, String idType) {
        ArrayList<Review> reviews = new ArrayList<>();

        String query = "SELECT user_id, business_id, text, rating, date, time FROM \"Review\" WHERE " + idType + " = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    int businessId = resultSet.getInt("business_id");
                    String text = resultSet.getString("text");
                    float rating = resultSet.getFloat("rating");
                    Date date = resultSet.getDate("date");
                    Time time = resultSet.getTime("time");

                    Review review = new Review(userId, businessId, text, rating, date, time);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}

