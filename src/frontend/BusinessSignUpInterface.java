package frontend;

import backend.Address;
import database.BusinessDetailsDAO;
import database.BusinessDAO;
import database.DAO;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BusinessSignUpInterface extends SignUpInterface {
    private TextField streetField;
    private TextField civicNumberField;
    private TextField postcodeField;
    private TextField cityField;
    private TextField countryField;

    protected DAO getDAO() {
        return new BusinessDAO();
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label businessNameLabel = new Label("Business Name:");
        Label addressLabel = new Label("Address:");
        Label streetLabel = new Label("Street:");
        Label civicNumberLabel = new Label("Civic Number:");
        Label postCodeLabel = new Label("Postcode:");
        Label cityLabel = new Label("City:");
        Label countryLabel = new Label("Country:");
        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");

        nameField = new TextField();
        streetField = new TextField();
        civicNumberField = new TextField();
        postcodeField = new TextField();
        cityField = new TextField();
        countryField = new TextField();
        emailField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        Button registerButton = createButton("Register", e -> register());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(businessNameLabel, 0, 0);
        addToGridPane(addressLabel, 0, 1);
        addToGridPane(streetLabel, 1, 1);
        addToGridPane(civicNumberLabel, 1, 2);
        addToGridPane(postCodeLabel, 1, 3);
        addToGridPane(cityLabel, 1, 4);
        addToGridPane(countryLabel, 1, 5);
        addToGridPane(emailLabel, 0, 6);
        addToGridPane(passwordLabel, 0, 7);
        addToGridPane(confirmPasswordLabel, 0, 8);

        addToGridPane(nameField, 1, 0);
        addToGridPane(streetField, 2, 1);
        addToGridPane(civicNumberField, 2, 2);
        addToGridPane(postcodeField, 2, 3);
        addToGridPane(cityField, 2, 4);
        addToGridPane(countryField, 2, 5);
        addToGridPane(emailField, 1, 6);
        addToGridPane(passwordField, 1, 7);
        addToGridPane(confirmPasswordField, 1, 8);

        addToGridPane(registerButton, 1, 9);
        addToGridPane(backButton, 0, 9);

        showCurrentInterface("Business Registration");
    }

    protected void optional(DAO DAO, String email){
        int businessId = DAO.getAccountId(email);
        new BusinessDetailsDAO().addNewRow("business_id", businessId);
    }

    protected Dashboard getDashboard(int id) {
        return new BusinessDashboard(id);
    }

    protected Object getSpecificField() {
        return new Address(
                streetField.getText(),
                civicNumberField.getText(),
                postcodeField.getText(),
                cityField.getText(),
                countryField.getText()
        );
    }
}