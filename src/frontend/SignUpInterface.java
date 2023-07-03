package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public abstract class SignUpInterface extends Application {
    protected TextField nameField;
    protected TextField emailField;
    protected PasswordField passwordField;
    protected PasswordField confirmPasswordField;
    GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        setGridPane();
        setCommonLabels();
        setSpecificLabels();

    }

    protected abstract void setSpecificLabels();

    private void setCommonLabels() {
        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordField = new PasswordField();
    }

    protected abstract void register();
    //TODO

    private void setGridPane() {
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
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
