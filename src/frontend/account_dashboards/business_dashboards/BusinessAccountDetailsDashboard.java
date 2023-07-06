package frontend.account_dashboards.business_dashboards;

import backend.Business;
import frontend.account_dashboards.AccountDetailsDashboard;
import javafx.stage.Stage;

import javafx.scene.control.*;

public class BusinessAccountDetailsDashboard extends AccountDetailsDashboard {

    public BusinessAccountDetailsDashboard(Business business) {
        this.account = business;
    }

    private void saveAddress() {
            //TODO
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label optionLabel = new Label("Select an option: ");
        Button modifyNameButton = createButton("Modify business name", e -> saveName());
        Button modifyEmailButton = createButton("Modify email", e -> saveEmail());
        Button modifyPasswordButton = createButton("Change password", e -> savePassword());
        Button modifyAddressButton = createButton("Modify address", e -> saveAddress());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(optionLabel, 0, 0);
        addToGridPane(modifyNameButton, 0, 1);
        addToGridPane(modifyEmailButton, 0, 2);
        addToGridPane(modifyPasswordButton, 0, 3);
        addToGridPane(modifyAddressButton, 0, 4);
        addToGridPane(backButton, 0, 5);

        showCurrentInterface("Modify Account Details");
    }
}
