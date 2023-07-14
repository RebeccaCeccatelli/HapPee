package frontend.account_dashboards.business_dashboards;

import backend.Business;
import frontend.Interface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Time;

public class SetTimesDashboard extends Interface {
    private Business business;
    private TextField openingTimeField;
    private TextField closingTimeField;


    public SetTimesDashboard(Business business) {
        this.business = business;
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label openingTimeLabel = new Label("Opening Time:");
        Label closingTimeLabel = new Label("Closing Time:");

        openingTimeField = new TextField();
        closingTimeField = new TextField();

        Button saveOCTimesBtn = createButton("Save", e -> saveOpeningClosingTimes());
        Button backBUtton = createButton("Back", e -> goBack());

        addToGridPane(openingTimeLabel, 0, 0);
        addToGridPane(openingTimeField, 1, 0);
        addToGridPane(closingTimeLabel, 0, 1);
        addToGridPane(closingTimeField, 1, 1);
        addToGridPane(backBUtton, 0, 2);
        addToGridPane(saveOCTimesBtn, 1, 2);
        showCurrentInterface("Set Opening and Closing Times");
    }

    private void saveOpeningClosingTimes() {
        String openingTimeString = openingTimeField.getText();
        String closingTimeString = closingTimeField.getText();

        if (!isValidFormat(openingTimeString) || !isValidFormat(closingTimeString)) {
            showAlert("Format error", "Invalid format. Please use HH:MM format.");
        }

        Time openingTime = Time.valueOf(openingTimeString + ":00");
        Time closingTime = Time.valueOf(closingTimeString + ":00");

        business.saveTime(openingTime, "opening_time");
        business.saveTime(closingTime, "closing_time");
        showConfirmationDialog("Set Opening and Closing Times", "New Opening and Closing Times successfully saved!");
        goBack();
    }

    private boolean isValidFormat(String timeString) {
        return timeString.matches("^\\d{2}:\\d{2}$");
    }
}
