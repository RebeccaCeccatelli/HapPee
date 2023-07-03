package frontend;

import backend.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class UserDashboard extends Application {
    private User user;

    public UserDashboard(String email) {
        this.user = new User(email);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Utente loggato come User");

    }
}
