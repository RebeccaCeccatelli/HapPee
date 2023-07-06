package frontend;

import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccountTypeInterface extends Interface {

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Text text = new Text("Please select the option that best suits you.");
        Button userButton = createButton("I am a user", e -> showUserSignUpInterface());
        Button businessButton = createButton("I own a local business", e -> showBusinessSignUpInterface());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(text, 0, 0);
        addToGridPane(userButton, 0, 1);
        addToGridPane(businessButton, 0, 2);
        addToGridPane(backButton, 0, 3);

        showCurrentInterface("Client type Interface");
    }

    private void showUserSignUpInterface() {
        UserSignUpInterface userSignUpInterface = new UserSignUpInterface();
        showNextInterface(userSignUpInterface);
    }

    private void showBusinessSignUpInterface() {
        BusinessSignUpInterface businessSignUpInterface = new BusinessSignUpInterface();
        showNextInterface(businessSignUpInterface);
    }

}
