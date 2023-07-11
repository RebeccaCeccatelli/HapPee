package backend;

import database.ReviewDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Review {
    private int userId;
    private int businessId;
    private String text;
    private float rating;
    private Date date;
    private Time time;

    public Review(int userId, int businessId, String text, float rating){
        this.userId = userId;
        this.businessId = businessId;
        this.text = text;
        this.rating = rating;
        this.date = Date.valueOf(LocalDate.now());
        this.time = Time.valueOf(LocalTime.now());
    }

    public Review(int userId, int businessId, String text, float rating, Date date, Time time) {
        this(userId, businessId, text, rating);
        this.date = date;
        this.time = time;
    }

    void save() {
        new ReviewDAO().addRow(userId, businessId, text, rating, date, time);
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

    public float getRating() {
        return rating;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTime() {
        return time.toString();
    }
}
