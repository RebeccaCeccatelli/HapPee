package frontend.account_dashboards.business_dashboards;

import backend.Business;
import frontend.Interface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetAccessCodeDashboard extends Interface {
    private Business business;
    private TextField accessCodeTextField;

    public SetAccessCodeDashboard(Business business) {
        this.business = business;
    }

    private void saveAccessCode() {
        int newAccessCode = Integer.parseInt(accessCodeTextField.getText());
        business.saveAccessCode(newAccessCode);
        showConfirmationDialog("Set Access Code", "New Access Code successfully saved!");
        goBack();
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label accessCodeLabel = new Label("Set access code:");

        int oldAccessCode = business.getDetails().getAccessCode();
        accessCodeTextField = new TextField(Float.toString(oldAccessCode));
        Button saveAccessCodeBtn = createButton("Save", e -> saveAccessCode());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(accessCodeLabel, 0, 0);
        addToGridPane(accessCodeTextField, 1, 0);
        addToGridPane(backButton, 0, 1);
        addToGridPane(saveAccessCodeBtn, 1, 1);

        showCurrentInterface("Set Access Code");
    }
}
