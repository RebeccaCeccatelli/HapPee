package database;

import backend.Review;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReviewDAOSuite {
    private ReviewDAO reviewDAO = new ReviewDAO();

    @Test
    public void addRowTest() {
        int previousSize = reviewDAO.getTableSize();

        reviewDAO.addRow(32, 41, "test", 4.0, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
        reviewDAO.addRow(32, 41, "test", 5.0, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
        reviewDAO.addRow(32, 41, "test", 3.0, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
        try {
            Assert.assertEquals(previousSize + 3, reviewDAO.getTableSize());
        }
        finally {
            cleanUp();
        }
    }

   @Test
    public void getReviewsByAccountIdTest() {
       reviewDAO.addRow(32, 41, "test", 4.0, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
       reviewDAO.addRow(32, 41, "test", 5.0, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
       reviewDAO.addRow(32, 41, "test", 3.0, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));

       ArrayList<Review> userReviews = reviewDAO.getReviewsByAccountId(32, "user_id");
       ArrayList<Review> businessReviews = reviewDAO.getReviewsByAccountId(41, "business_id");
        try {
           Assert.assertEquals(userReviews.size(), businessReviews.size());

           for (int i = 0; i < userReviews.size(); i++) {
               Assert.assertTrue(Review.equals(userReviews.get(i), businessReviews.get(i)));
           }
       }
       finally {
           cleanUp();
       }
   }

    private void cleanUp() {
        reviewDAO.deleteTestReviews(41, "test");
    }

}
