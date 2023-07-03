package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeInterface extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        VBox welcomeInterface = new VBox(10);
        welcomeInterface.setPadding(new Insets(20));

        Text text = new Text("Welcome to HapPee! Do you already have a HapPee Account?");
        Button signUpButton = new Button("No: Let's sign up!");
        Button signInButton = new Button("Yes: Sign in.");

        signUpButton.setOnAction(e -> showSignUpWindow());
        signInButton.setOnAction(e -> showSignInWindow());

        welcomeInterface.getChildren().addAll(text, signUpButton, signInButton);

        Scene scene = new Scene(welcomeInterface, 300, 200);
        primaryStage.setTitle("Welcome Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSignUpWindow() {
        ClientTypeInterface signUpInterface = new ClientTypeInterface();
        signUpInterface.start(primaryStage);
    }

    private void showSignInWindow() {
        SignInInterface signInInterface = new SignInInterface();
        signInInterface.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
