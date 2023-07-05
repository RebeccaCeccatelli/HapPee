package frontend.account_dashboards.business_dashboards;

import backend.Business;
import frontend.Interface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetBusinessTypeDashboard extends Interface {

    private Business business;
    private TextField businessTypeTextField;

    public SetBusinessTypeDashboard(Business business) {
        this.business = business;
    }

    private void saveBusinessType() {
        String newBusinessType = businessTypeTextField.getText();
        business.saveBusinessType(newBusinessType);
        showConfirmationDialog("Set business type", "New Business Type successfully saved!");
        goBack();
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label setBusinessTypeLabel = new Label("Specify Business Type:");

        String oldBusinessType = business.getDetails().getBusinessType();
        businessTypeTextField = new TextField(oldBusinessType);
        Button saveBusinessTypeBtn = createButton("Save", e -> saveBusinessType());
        Button backButton = createButton("Back", e -> goBack());


        addToGridPane(setBusinessTypeLabel, 0, 0);
        addToGridPane(businessTypeTextField, 1, 0);
        addToGridPane(backButton, 0, 1);
        addToGridPane(saveBusinessTypeBtn, 1, 1);

        showCurrentInterface("Set Business Type");
    }
}
