package frontend;

import backend.Business;
import frontend.account_dashboards.business_dashboards.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BusinessDashboard extends Dashboard {
    private Business business;

    public BusinessDashboard(int id) {
        this.business = new Business(id);
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label greetingLabel = new Label("Hi, what do you want to do today?");
        Button setBusinessTypeBtn = createButton("Set business type", e -> setBusinessType());
        Button setAccessPriceBtn = createButton("Set single access price", e -> setAccessPrice());
        Button setOpeningClosingTimesBtn = createButton("Set business opening and closing times", e -> setOpeningClosingTimes());
        Button setRestroomAccessCodeBtn = createButton("Set access code for restroom", e -> setAccessCode());
        Button checkReviewsBtn = createButton("Check customer reviews", e -> checkCustomerReviews());
        Button modifyAccountDetailsBtn = createButton("Modify account details", e -> modifyAccountDetails());
        Button logoutBtn = createButton("Log out", e -> goBack());

        addToGridPane(greetingLabel, 0, 0);
        addToGridPane(setBusinessTypeBtn, 0, 1);
        addToGridPane(setAccessPriceBtn, 0, 2);
        addToGridPane(setRestroomAccessCodeBtn, 0, 3);
        addToGridPane(setOpeningClosingTimesBtn, 0, 4);
        addToGridPane(checkReviewsBtn, 0, 5);
        addToGridPane(modifyAccountDetailsBtn, 0, 6);
        addToGridPane(logoutBtn, 0, 7);

        showCurrentInterface("Business Dashboard");
    }

    private void setAccessCode() {
        SetAccessCodeDashboard setAccessCodeDashboard = new SetAccessCodeDashboard(business);
        showNextInterface(setAccessCodeDashboard);
    }

    private void checkCustomerReviews() {
        CheckReviewsDashboard checkReviewsDashboard = new CheckReviewsDashboard(business);
        showNextInterface(checkReviewsDashboard);
    }

    private void modifyAccountDetails() {
        BusinessAccountDetailsDashboard businessAccountDetailsDashboard = new BusinessAccountDetailsDashboard(business);
        showNextInterface(businessAccountDetailsDashboard);
    }

    private void setOpeningClosingTimes() {
        SetTimesDashboard setTimesDashboard = new SetTimesDashboard(business);
        showNextInterface(setTimesDashboard);
    }

    private void setAccessPrice() {
        SetAccessPriceDashboard setAccessPriceDashboard = new SetAccessPriceDashboard(business);
        showNextInterface(setAccessPriceDashboard);
    }

    private void setBusinessType() {
        SetBusinessTypeDashboard setBusinessTypeDashboard = new SetBusinessTypeDashboard(business);
        showNextInterface(setBusinessTypeDashboard);
    }
}

