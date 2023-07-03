package frontend;

import backend.User;
import backend.UserInformation;
import database.UserTableManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UserSignUpInterface extends Application {
    private TextField nameField;
    private TextField surnameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private GridPane gridPane = new GridPane();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label surnameLabel = new Label("Surname:");
        surnameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordField = new PasswordField();

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(passwordField, 1, 3);
        gridPane.add(confirmPasswordLabel, 0, 4);
        gridPane.add(confirmPasswordField, 1, 4);

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> register());

        gridPane.add(registerButton, 0, 5, 2, 1);

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setTitle("Sign up");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void register() {
        UserTableManager userTableManager = new UserTableManager();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (userTableManager.emailAlreadyExists(email)) {
            showAlert("Email already exists", "Please enter a new email");
            emailField.clear();
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match", "Please re-enter the password");
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            if (userTableManager.insert(name, surname, email, password)) {
                showConfirmationDialog();
                UserDashboard userDashboard = new UserDashboard(email);
                userDashboard.start(primaryStage);
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
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Registration Completed");
        dialog.setHeaderText(null);
        dialog.setContentText("Registration successfully completed!");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        dialog.showAndWait();
    }
}

