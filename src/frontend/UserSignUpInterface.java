package frontend;

import database.DAO;
import database.UserPaymentDetailsDAO;
import database.UserDAO;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UserSignUpInterface extends SignUpInterface {
    private TextField surnameField;

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label nameLabel = new Label("Name:");
        Label surnameLabel = new Label("Surname:");
        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");

        nameField = new TextField();
        surnameField = new TextField();
        emailField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        Button registerButton = createButton("Register", e -> register());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(nameLabel, 0, 0);
        addToGridPane(surnameLabel, 0, 1);
        addToGridPane(emailLabel, 0, 2);
        addToGridPane(passwordLabel, 0, 3);
        addToGridPane(confirmPasswordLabel, 0, 4);

        addToGridPane(nameField, 1, 0);
        addToGridPane(surnameField, 1, 1);
        addToGridPane(emailField, 1, 2);
        addToGridPane(passwordField, 1, 3);
        addToGridPane(confirmPasswordField, 1, 4);

        addToGridPane(registerButton, 1, 5);
        addToGridPane(backButton, 0, 5);

        showCurrentInterface("User Registration");
    }

    DAO getDAO() {
        return new UserDAO();
    }

    Interface getDashboard(int id) {
        return new UserDashboard(id);
    }

    Object getSpecificField() {
        return surnameField.getText();
    }

    void addRowToDetails(DAO DAO, String email) {
        int userId = DAO.getIdByEmail(email);
        new UserPaymentDetailsDAO().add(userId);
    }

    protected void clearPersonalFields() {
        super.clearPersonalFields();
        surnameField.clear();
    }
}

