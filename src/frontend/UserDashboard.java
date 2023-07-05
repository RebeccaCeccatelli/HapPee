package frontend;

import backend.User;
import javafx.stage.Stage;

public class UserDashboard extends Dashboard {
    private User user;

    public UserDashboard(int id) {
        this.user = new User(id);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Utente loggato come User");
    }
}
