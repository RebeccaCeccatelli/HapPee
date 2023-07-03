package frontend;

import backend.Business;
import javafx.application.Application;
import javafx.stage.Stage;

public class BusinessDashboard extends Application {

    private Business business;

    public BusinessDashboard(String email) {
        this.business = new Business(email);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Utente loggato come Business");
    }
}
