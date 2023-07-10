package frontend.account_dashboards.user_dashboards;

import backend.Review;
import backend.User;
import database.BusinessDAO;
import frontend.Interface;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.ArrayList;

public class CheckGivenReviewsDashboard extends Interface {
    private User user;

    public CheckGivenReviewsDashboard(User user) {
        this.user = user;
    }

    private void showReview(Review review) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Show review");
        String businessName = new BusinessDAO().getStringFromDB(review.getBusinessId(), "name");

        alert.setHeaderText("Review on: " + businessName + ". ");

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
        ArrayList<Review> reviews = user.getReviews();

        Label listLabel = new Label("Here is the list of the reviews you submitted: ");
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(listLabel, 0, 0);
        if (!reviews.isEmpty()){
            int i = 1;
            for (Review review : reviews) {
                String businessName = new BusinessDAO().getStringFromDB(review.getBusinessId(), "name");
                Label reviewLabel = new Label("You reviewed '"+ businessName + "': ");
                Button openReviewButton = createButton("Open review", e -> showReview(review));

                addToGridPane(reviewLabel, 0, i);
                addToGridPane(openReviewButton, 1, i);
                i += 1;
            }
            addToGridPane(backButton, 0, i);
        }
        else {
            Label emptyListLabel = new Label("You haven't submitted any reviews yet.");
            addToGridPane(emptyListLabel, 0, 1);
            addToGridPane(backButton, 0, 2);
        }

        showCurrentInterface("Check given reviews");
    }
}
