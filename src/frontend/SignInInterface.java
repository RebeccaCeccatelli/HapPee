package frontend;

import backend.User;
import backend.UserInformation;
import database.BusinessTableManager;
import database.UserTableManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignInInterface extends Application {

    private TextField emailTextField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label emailLabel = new Label("Email:");

        Label passwordLabel = new Label("Password:");

        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        gridPane.add(loginButton, 0, 2, 2, 1);

        Scene scene = new Scene(gridPane, 300, 150);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void login() {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (new UserTableManager().checkUser(email, password)) {
            UserDashboard userDashboard = new UserDashboard(email);
            userDashboard.start(primaryStage);
        }
        else if (new BusinessTableManager().checkBusiness(email, password)) {
            BusinessDashboard businessDashboard = new BusinessDashboard(email);
            businessDashboard.start(primaryStage);
        }
        else {
            showAlert("Invalid credentials", "Incorrect username or password. ");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
