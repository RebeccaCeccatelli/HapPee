package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientTypeInterface extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        VBox signUpInterface = new VBox(10);
        signUpInterface.setPadding(new Insets(20));

        Text text = new Text("Please select the option that best suits you.");
        Button userButton = new Button("I am a user");
        Button businessButton = new Button("I own a local business");

        userButton.setOnAction(e -> showUserSignUpInterface());
        businessButton.setOnAction(e -> showBusinessSignUpInterface());

        signUpInterface.getChildren().addAll(text, userButton, businessButton);

        Scene scene = new Scene(signUpInterface, 300, 200);
        primaryStage.setTitle("General Sign Up Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showUserSignUpInterface() {
        UserSignUpInterface userSignUpInterface = new UserSignUpInterface();
        userSignUpInterface.start(primaryStage);
    }

    private void showBusinessSignUpInterface() {
        BusinessSignUpInterface businessSignUpInterface = new BusinessSignUpInterface();
        businessSignUpInterface.start(primaryStage);
    }

}
