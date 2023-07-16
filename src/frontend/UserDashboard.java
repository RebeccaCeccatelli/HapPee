package frontend;

import backend.Business;
import backend.User;
import database.BusinessDAO;
import frontend.account_dashboards.user_dashboards.AddReviewDashboard;
import frontend.account_dashboards.user_dashboards.CheckGivenReviewsDashboard;
import frontend.account_dashboards.user_dashboards.UserAccountDetailsDashboard;
import frontend.map.Map;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class UserDashboard extends Interface implements Dashboard {
    private User user;

    public UserDashboard(int id) {
        this.user = new User(id);
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label greetingLabel = new Label("Hi, what do you want to do today?");
        Button findRestroomBtn = createButton("Find restrooms near me on map", e -> findARestroom());
        Button chooseRestroosBtn = createButton("Choose restroom and get access code", e -> chooseRestroom());
        Button topUpBtn = createButton("Top up credit", e -> topUp());
        Button checkSubscriptionBtn = createButton("Check or upgrade subscription", e -> checkSubscription());
        Button addReviewBtn = createButton("Add a business review", e -> addReview());
        Button checkGivenReviewsBtn = createButton("Check given reviews", e -> checkGivenReviews());
        Button modifyAccountDetailsBtn = createButton("Modify account details", e -> modifyAccountDetails());
        Button logoutBtn = createButton("Log out", e -> goBack());

        addToGridPane(greetingLabel, 0, 0);
        addToGridPane(findRestroomBtn, 0, 1);
        addToGridPane(chooseRestroosBtn, 1, 1);
        addToGridPane(topUpBtn, 0, 2);
        addToGridPane(checkSubscriptionBtn, 0, 3);
        addToGridPane(addReviewBtn, 0, 4);
        addToGridPane(checkGivenReviewsBtn, 0, 5);
        addToGridPane(modifyAccountDetailsBtn, 0, 6);
        addToGridPane(logoutBtn, 0, 7);

        showCurrentInterface("User Dashboard");
    }

    private void findARestroom() {
        Map.display();
    }

    private void chooseRestroom() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose restroom");

        dialog.setContentText("Enter the name of the business where your chosen restroom is located:");

        dialog.getDialogPane().setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::getAccessCode);
    }

    private void getAccessCode(String businessName) {
        int businessId = new BusinessDAO().getIdByBusinessName(businessName);
        if(businessId == -1) {
            showAlert("Business not found", "Business not found. Please try again.");
        }
        else {
            Business business = new BusinessDAO().getBusinessByBusinessId(businessId);
            if (Objects.equals(user.getSubscription(), "premium")) {
                showConfirmationDialog("You are a premium user, every restroom is free for you!",
                        business.getAccessCode());
            }
            else {
                double accessPrice = business.getDetails().getAccessPrice();
                if (requestPaymentConfirmation(accessPrice)) {
                    if (user.pay(accessPrice)) {
                        showConfirmationDialog("Payment successful! You just paid " + accessPrice +
                                        "$ (remaining balance: " + user.getCreditBalance() + "$).",
                                business.getAccessCode());
                    } else {
                        showAlert("Insufficient Credit",
                                "Your current credit is " + user.getCreditBalance() + "$." +
                                        " Insufficient, please top up.");
                    }
                }
            }
        }
    }

    private boolean requestPaymentConfirmation(double amount) {
        final boolean[] permission = {false};
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment confirmation.");
        alert.setHeaderText("You are currently on a " + user.getSubscription() + " subscription.");
        alert.setContentText("Your credit is currently " + user.getCreditBalance() + "$" +
                " and the access price to this restroom is " + amount +
                "$.\nDo you want to proceed? If so, click on the 'Pay' button.");
        ButtonType payButton = new ButtonType("Pay");

        alert.getButtonTypes().setAll(payButton);

        alert.getDialogPane().setGraphic(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == payButton) {
                permission[0] = true;
            }
        });
        return permission[0];
    }

    private void showConfirmationDialog(String message, int accessCode) {
        String title = "Your access code";
        String accessInfo =  "\nUse this code to access the restroom: " + accessCode + ".\nHappee!";
        showConfirmationDialog(title, message + accessInfo);

    }

    private void topUp() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Top up credit");
        dialog.setHeaderText("Enter the amount you want to top up: ");

        double currentBalance = user.getCreditBalance();
        dialog.setContentText("Current balance: " + currentBalance + "$. ");

        dialog.getDialogPane().setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            user.topUpCredit(Float.parseFloat(amount));
            showConfirmationDialog("Top up credit", "Credit successfully topped up!");
        });
    }

    private void checkSubscription() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check Subscription");
        alert.setHeaderText("You are currently on a " + user.getSubscription() + " subscription.");
        alert.getDialogPane().setGraphic(null);
        if (Objects.equals(user.getSubscription(), "standard")) {
            alert.setContentText("Do you wish to upgrade to premium, for only 100$?" +
                    " If so, click on the 'Upgrade' button.");
            ButtonType upgradeButton = new ButtonType("Upgrade");

            alert.getButtonTypes().setAll(upgradeButton);

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
        else {
            alert.showAndWait();
        }
    }

    private void addReview() {
        AddReviewDashboard addReviewDashboard = new AddReviewDashboard(user);
        showNextInterface(addReviewDashboard);
    }

    private void checkGivenReviews() {
        CheckGivenReviewsDashboard checkGivenReviewsDashboard = new CheckGivenReviewsDashboard(user);
        showNextInterface(checkGivenReviewsDashboard);
    }

    public void modifyAccountDetails() {
        UserAccountDetailsDashboard userAccountDetailsDashboard = new UserAccountDetailsDashboard(user);
        showNextInterface(userAccountDetailsDashboard);
    }
}
