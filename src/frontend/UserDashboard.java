package frontend;

import backend.Map;
import backend.User;
import frontend.account_dashboards.user_dashboards.AddReviewDashboard;
import frontend.account_dashboards.user_dashboards.CheckGivenReviewsDashboard;
import frontend.account_dashboards.user_dashboards.UserAccountDetailsDashboard;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class UserDashboard extends Dashboard {
    private User user;

    public UserDashboard(int id) {
        this.user = new User(id);
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label greetingLabel = new Label("Hi, what do you want to do today?");
        Button findRestroomBtn = createButton("Find a restroom near me", e -> findARestroom());
        Button topUpBtn = createButton("Top up credit", e -> topUp());
        Button checkSubscriptionBtn = createButton("Check or upgrade subscription", e -> checkSubscription());
        Button addReviewBtn = createButton("Add a business review", e -> addReview());
        Button checkGivenReviewsBtn = createButton("Check given reviews", e -> checkGivenReviews());
        Button modifyAccountDetailsBtn = createButton("Modify account details", e -> modifyAccountDetails());
        Button logoutBtn = createButton("Log out", e -> goBack());

        addToGridPane(greetingLabel, 0, 0);
        addToGridPane(findRestroomBtn, 0, 1);
        addToGridPane(topUpBtn, 0, 2);
        addToGridPane(checkSubscriptionBtn, 0, 3);
        addToGridPane(addReviewBtn, 0, 4);
        addToGridPane(checkGivenReviewsBtn, 0, 5);
        addToGridPane(modifyAccountDetailsBtn, 0, 6);
        addToGridPane(logoutBtn, 0, 7);

        showCurrentInterface("User Dashboard");
    }

    private void checkSubscription() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check Subscription");
        alert.setHeaderText("You are currently on a " + user.getSubscription() + " subscription.");
        alert.setContentText("Do you wish to upgrade to premium, for only 100$? If so, click on the 'Upgrade' button.");
        ButtonType upgradeButton = new ButtonType("Upgrade");

        alert.getButtonTypes().setAll(upgradeButton);

        alert.getDialogPane().setGraphic(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == upgradeButton) {
                if (user.upgradeToPremiumSubscription()) {
                    showConfirmationDialog("Upgrade to Premium",
                            "Upgrade to Premium Successfully Completed!" +
                                    "\nRemaining credit: " + user.getCreditBalance() + ".");
                }
                else {
                    showAlert("Insufficient credit",
                            "Your remaining credit is " + user.getCreditBalance() + ". Top up to upgrade.");
                }
            }
        });

    }

    private void topUp() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Top up credit");
        dialog.setHeaderText("Enter the amount you want to top up: ");

        float currentBalance = user.getCreditBalance();
        dialog.setContentText("Current balance: " + currentBalance + ". ");

        dialog.getDialogPane().setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            user.topUpCredit(Float.parseFloat(amount));
            showConfirmationDialog("Top up credit", "Credit successfully topped up!");
        });
    }

    private void findARestroom() {
        Map map = new Map();
        map.display();
    }

    private void addReview() {
        AddReviewDashboard addReviewDashboard = new AddReviewDashboard(user);
        showNextInterface(addReviewDashboard);
    }


    private void checkGivenReviews() {
        CheckGivenReviewsDashboard checkGivenReviewsDashboard = new CheckGivenReviewsDashboard(user);
        showNextInterface(checkGivenReviewsDashboard);
    }

    private void modifyAccountDetails() {
        UserAccountDetailsDashboard userAccountDetailsDashboard = new UserAccountDetailsDashboard(user);
        showNextInterface(userAccountDetailsDashboard);
    }
}
