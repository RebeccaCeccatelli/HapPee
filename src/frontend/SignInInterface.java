package frontend;

import database.BusinessDAO;
import database.UserDAO;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignInInterface extends Interface {

    private TextField emailTextField = new TextField();
    private PasswordField passwordField = new PasswordField();

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");
        Button loginButton = createButton("Login", e -> login());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(emailLabel, 0, 0);
        addToGridPane(passwordLabel, 0, 1);
        addToGridPane(emailTextField, 1, 0);
        addToGridPane(passwordField, 1, 1);
        addToGridPane(backButton, 0, 2);
        addToGridPane(loginButton, 1, 2);

        showCurrentInterface("Login");
    }

    private void login() {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        UserDAO userDAO = new UserDAO();
        BusinessDAO businessDAO = new BusinessDAO();
        if (userDAO.checkIfUserRegistered(email, password)) {
            UserDashboard userDashboard = new UserDashboard(userDAO.getIdByEmail(email));
            showDashboard(userDashboard);
        }
        else if (businessDAO.checkIfBusinessRegistered(email, password)) {
            BusinessDashboard businessDashboard = new BusinessDashboard(businessDAO.getIdByEmail(email));
            showDashboard(businessDashboard);
        }
        else {
            showAlert("Invalid credentials", "Incorrect username or password. ");
        }
    }

    private void showDashboard(Dashboard dashboard) {
        showConfirmationDialog("Login Completed", "Login successful!");
        clearPersonalFields();
        showNextInterface(dashboard);
    }

    protected void clearPersonalFields() {
        emailTextField.clear();
        passwordField.clear();
    }
}
