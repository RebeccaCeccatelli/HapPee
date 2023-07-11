package frontend.account_dashboards.user_dashboards;

import backend.User;
import database.BusinessDAO;
import frontend.Interface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddReviewDashboard extends Interface {
    private User user;
    private TextField businessNameField;
    private TextField reviewField;
    private TextField ratingField;

    public AddReviewDashboard(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label setBusinessNameLabel = new Label("Enter the name of the business you want to review: ");
        businessNameField = new TextField();
        Label setReviewLabel = new Label("Write your review here: ");
        reviewField = new TextField();
        Label setRatingLabel = new Label("Enter your rating (from 1 to 5) here: ");
        ratingField = new TextField();

        Button saveReviewBtn = createButton("Save", e -> saveReview());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(setBusinessNameLabel, 0, 0);
        addToGridPane(businessNameField, 1, 0);
        addToGridPane(setReviewLabel, 0, 1);
        addToGridPane(reviewField, 1, 1);
        addToGridPane(setRatingLabel, 0, 2);
        addToGridPane(ratingField, 1, 2);
        addToGridPane(backButton, 0, 3);
        addToGridPane(saveReviewBtn, 1, 3);

        showCurrentInterface("Add new Review");
    }

    private void saveReview() {
        String businessName = businessNameField.getText();
        String text = reviewField.getText();
        float rating = Float.parseFloat(ratingField.getText());

        int businessId = new BusinessDAO().getIdByBusinessName(businessName);
        if (businessId == -1) {
            showAlert("Invalid business name", "We couldn't find the business name you entered. Try again.");
            businessNameField.clear();
        }
        else {
            if (rating >= 0 && rating <= 5) {
                user.saveReview(businessId, text, rating);
                showConfirmationDialog("Add new review", "Review successfully submitted!");
                goBack();
            }
            else {
                showAlert("Invalid rating", "The rating must be between 0 and 5. ");
                ratingField.clear();
            }
        }
    }
}
