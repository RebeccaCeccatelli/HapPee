package frontend.account_dashboards.business_dashboards;

import backend.Business;
import frontend.Interface;
import javafx.application.Application;
import javafx.stage.Stage;

public class CheckReviewsDashboard extends Interface {
    private Business business;

    public CheckReviewsDashboard(Business business) {
        this.business = business;
    }

    @Override
    public void start(Stage primaryStage) {
        //TODO creare tabella review in database e poi da qui prendere la lista e scorrerla
    }
}
