package frontend.account_dashboards.business_dashboards;

import backend.Account;
import backend.Address;
import frontend.Interface;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyBusinessAddressDashboard extends Interface {
    private Account business;
    private TextField streetField;
    private TextField civicNumberField;
    private TextField postCodeField;
    private TextField cityField;
    private TextField countryField;

    public ModifyBusinessAddressDashboard(Account business) {
        this.business = business;
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label modifyAddressLabel = new Label("Modify the business address.");
        Label streetLabel = new Label("Street: ");
        Address currentAddress = (Address) business.getSpecificField();
        streetField = new TextField(currentAddress.getStreet());
        Label civicNumberLabel = new Label("Civic number: ");
        civicNumberField = new TextField(currentAddress.getCivicNumber());
        Label postCodeLabel = new Label("Postcode: ");
        postCodeField = new TextField(currentAddress.getPostCode());
        Label cityLabel = new Label("City: ");
        cityField = new TextField(currentAddress.getCity());
        Label countryLabel = new Label("Country: ");
        countryField = new TextField(currentAddress.getCountry());

        Button saveNewAddressBtn = createButton("Save", e -> saveNewAddress());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(modifyAddressLabel, 0, 0);
        addToGridPane(streetLabel, 0, 1);
        addToGridPane(streetField, 1, 1);
        addToGridPane(civicNumberLabel, 0, 2);
        addToGridPane(civicNumberField, 1, 2);
        addToGridPane(postCodeLabel, 0, 3);
        addToGridPane(postCodeField, 1, 3);
        addToGridPane(cityLabel, 0, 4);
        addToGridPane(cityField, 1, 4);
        addToGridPane(countryLabel, 0, 5);
        addToGridPane(countryField, 1, 5);
        addToGridPane(backButton, 0, 6);
        addToGridPane(saveNewAddressBtn, 1, 6);

        showCurrentInterface("Modify business address");
    }

    private void saveNewAddress() {
        String newStreet = streetField.getText();
        String newCivicNumber = civicNumberField.getText();
        String newPostCode = postCodeField.getText();
        String newCity = cityField.getText();
        String newCountry = countryField.getText();

        business.saveSpecificField(newStreet, newCivicNumber, newPostCode, newCity, newCountry);
        showConfirmationDialog("Save new Address", "New Address successfully saved!");
        goBack();
    }
}
