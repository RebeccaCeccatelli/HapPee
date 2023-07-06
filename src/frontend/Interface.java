package frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public abstract class Interface extends Application {
    protected Stage primaryStage;
    protected Scene currentScene;
    protected Scene previousScene;
    protected String previousSceneTitle;
    protected GridPane gridPane = new GridPane();


    protected void setPreviousSceneTitle(String title) {
        this.previousSceneTitle = title;
    }

    @Override
    public abstract void start(Stage primaryStage);

    protected void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    protected void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    protected void goBack() {
        primaryStage.setTitle(previousSceneTitle);
        primaryStage.setScene(previousScene);
    }

    protected void showCurrentInterface(String title) {
        this.currentScene = new Scene(gridPane, 600, 400);
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    protected static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected static void showConfirmationDialog(String title, String message) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        dialog.showAndWait();
    }

    protected void setGridPane() {
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    }

    protected void showNextInterface(Interface nextInterface) {
        nextInterface.setPreviousScene(currentScene);
        nextInterface.setPreviousSceneTitle(primaryStage.getTitle());
        nextInterface.start(primaryStage);
    }

    protected Button createButton(String buttonName, EventHandler<ActionEvent> action) {
        Button button = new Button(buttonName);
        button.setOnAction(action);
        return button;
    }

    protected void addToGridPane(Node node, int column, int row) {
        gridPane.add(node, column, row);
    }
}
