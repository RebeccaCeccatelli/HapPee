package frontend.account_dashboards.business_dashboards;

import backend.Business;
import frontend.Interface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetAccessPriceDashboard extends Interface {

    private Business business;
    private TextField accessPriceTextField;

    public SetAccessPriceDashboard(Business business) {
        this.business = business;
    }

    private void saveAccessPrice() {
        float newAccessPrice = Float.parseFloat(accessPriceTextField.getText());
        business.saveAccessPrice(newAccessPrice);
        showConfirmationDialog("Set Access Price", "New Access Price successfully saved!");
        goBack();
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label accessPriceLabel = new Label("Set single access price:");

        float oldAccessPrice = business.getDetails().getAccessPrice();
        accessPriceTextField = new TextField(Float.toString(oldAccessPrice));
        Button saveAccessPriceBtn = createButton("Save", e -> saveAccessPrice());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(accessPriceLabel, 0, 0);
        addToGridPane(accessPriceTextField, 1, 0);
        addToGridPane(backButton, 0, 1);
        addToGridPane(saveAccessPriceBtn, 1, 1);

        showCurrentInterface("Set Access Price");
    }

}
