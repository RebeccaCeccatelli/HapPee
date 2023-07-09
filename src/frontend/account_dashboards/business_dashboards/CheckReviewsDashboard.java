package frontend.account_dashboards.business_dashboards;

import backend.Business;
import backend.Review;
import database.UserDAO;
import frontend.Interface;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CheckReviewsDashboard extends Interface {
    private Business business;

    public CheckReviewsDashboard(Business business) {
        this.business = business;
    }

    private void showReview(Review review) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Show review");
        String userName = new UserDAO().getStringFromDB(review.getUserId(), "name");

        alert.setHeaderText("Review from: " + userName + ". ");

        alert.setContentText("Date: " + review.getDate() + ", Time: " + review.getTime() +
                "\nText: " + review.getText() +
                "\nRating: " + review.getRating());

        alert.getDialogPane().setGraphic(null);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();
        ArrayList<Review> reviews = business.getReviews();

        Label listLabel = new Label("Here is the list of the reviews on your business: ");
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(listLabel, 0, 0);
        if (!reviews.isEmpty()){
            Label averageRatingLabel = new Label("Average rating: " + business.getAverageRating());
            addToGridPane(averageRatingLabel, 0, 1);

            int i = 2;
            for (Review review : reviews) {
                String userName = new UserDAO().getStringFromDB(review.getUserId(), "name");
                Label reviewLabel = new Label("Review from '"+ userName + "': ");
                Button openReviewButton = createButton("Open review", e -> showReview(review));

                addToGridPane(reviewLabel, 0, i);
                addToGridPane(openReviewButton, 1, i);
                i += 1;
            }
            addToGridPane(backButton, 0, i);
        }
        else {
            Label emptyListLabel = new Label("You haven't been reviewed yet.");
            addToGridPane(emptyListLabel, 0, 1);
            addToGridPane(backButton, 0, 2);
        }

        showCurrentInterface("Check reviews");
    }
}
