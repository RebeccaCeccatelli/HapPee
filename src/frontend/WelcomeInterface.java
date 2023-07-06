package frontend;

import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeInterface extends Interface {

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Text text = new Text("Welcome to HapPee! Do you already have a HapPee Account?");
        Button signUpButton = createButton("No: Let's sign up!",e -> showSignUpWindow());
        Button signInButton = createButton("Yes: Sign in. ", e -> showSignInWindow());

        addToGridPane(text, 0, 0);
        addToGridPane(signUpButton, 0, 1);
        addToGridPane(signInButton, 0, 2);

        showCurrentInterface("Welcome Interface");
    }

    private void showSignUpWindow() {
        AccountTypeInterface clientTypeInterface = new AccountTypeInterface();
        showNextInterface(clientTypeInterface);
    }

    private void showSignInWindow() {
        SignInInterface signInInterface = new SignInInterface();
        showNextInterface(signInInterface);
    }

    public static void main(String[] args) {launch(args);}
}
