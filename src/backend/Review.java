package backend;

import database.ReviewDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Review {
    private int userId;
    private int businessId;
    private String text;
    private double rating;
    private Date date;
    private Time time;

    public Review(int userId, int businessId, String text, double rating){
        this.userId = userId;
        this.businessId = businessId;
        this.text = text;
        this.rating = rating;
        this.date = Date.valueOf(LocalDate.now());
        this.time = Time.valueOf(LocalTime.now());
    }

    public Review(int userId, int businessId, String text, double rating, Date date, Time time) {
        this(userId, businessId, text, rating);
        this.date = date;
        this.time = time;
    }

    void save() {
        new ReviewDAO().add(userId, businessId, text, rating, date, time);
    }

    public int getBusinessId() {
        return businessId;
    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public double getRating() {
        return rating;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTime() {
        return time.toString();
    }

    public static boolean equals(Review first, Review second) {
        if (first.getUserId() == second.getUserId()) {
            if (first.getBusinessId() == second.getBusinessId()) {
                if (Objects.equals(first.getText(), second.getText())) {
                    if (first.getRating() == second.getRating()) {
                        if (Objects.equals(first.getDate(), second.getDate())) {
                            if (Objects.equals(first.getTime(), second.getTime())) {
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
