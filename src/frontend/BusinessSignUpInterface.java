package frontend;

import backend.Address;
import database.BusinessTableManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BusinessSignUpInterface extends Application {

    private GridPane gridPane = new GridPane();
    private Stage primaryStage;
    private TextField businessNameField;
    private TextField streetField;
    private TextField civicNumberField;
    private TextField postcodeField;
    private TextField cityField;
    private TextField countryField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label businessNameLabel = new Label("Business Name:");
        businessNameField = new TextField();

        Label addressLabel = new Label("Address:");
        streetField = new TextField();
        civicNumberField = new TextField();
        postcodeField = new TextField();
        cityField = new TextField();
        countryField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordField = new PasswordField();

        gridPane.add(businessNameLabel, 0, 0);
        gridPane.add(businessNameField, 1, 0);
        gridPane.add(addressLabel, 0, 1);
        gridPane.add(new Label("Street:"), 1, 1);
        gridPane.add(streetField, 2, 1);
        gridPane.add(new Label("Civic Number:"), 1, 2);
        gridPane.add(civicNumberField, 2, 2);
        gridPane.add(new Label("Postcode:"), 1, 3);
        gridPane.add(postcodeField, 2, 3);
        gridPane.add(new Label("City:"), 1, 4);
        gridPane.add(cityField, 2, 4);
        gridPane.add(new Label("Country:"), 1, 5);
        gridPane.add(countryField, 2, 5);
        gridPane.add(emailLabel, 0, 6);
        gridPane.add(emailField, 1, 6);
        gridPane.add(passwordLabel, 0, 7);
        gridPane.add(passwordField, 1, 7);
        gridPane.add(confirmPasswordLabel, 0, 8);
        gridPane.add(confirmPasswordField, 1, 8);


        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> register());

        VBox buttonBox = new VBox(10, registerButton);
        buttonBox.setPadding(new Insets(10));

        GridPane sceneGridPane = new GridPane();
        sceneGridPane.setPadding(new Insets(20));
        sceneGridPane.setHgap(10);
        sceneGridPane.setVgap(10);

        sceneGridPane.add(gridPane, 0, 0);
        sceneGridPane.add(buttonBox, 0, 1);

        Scene scene = new Scene(sceneGridPane);
        primaryStage.setTitle("Business Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void register() {
        BusinessTableManager businessTableManager = new BusinessTableManager();
        String businessName = businessNameField.getText();
        Address address = new Address(
                streetField.getText(),
                civicNumberField.getText(),
                postcodeField.getText(),
                cityField.getText(),
                countryField.getText()
        );
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (businessTableManager.emailAlreadyExists(email)) {
            showAlert("Email already exists", "Please enter a new email");
            emailField.clear();
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match", "Please re-enter the password");
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            if (businessTableManager.insert(businessName, address, email, password)){
                showConfirmationDialog();
                BusinessDashboard businessDashboard = new BusinessDashboard(email);
                businessDashboard.start(primaryStage);
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Success");
        alert.setHeaderText(null);
        alert.setContentText("Registration successfully completed!");
        alert.showAndWait();
    }

    private void showText(String message) {
        Label messageLabel = new Label(message);
        GridPane.setConstraints(messageLabel, 0, 6, 2, 1);
        GridPane.setMargin(messageLabel, new Insets(10, 0, 0, 0));
        gridPane.getChildren().add(messageLabel);
    }
}