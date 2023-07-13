package frontend;

import database.DAO;
import javafx.scene.control.*;

public abstract class SignUpInterface extends Interface {
    protected TextField nameField;
    protected TextField emailField;
    protected PasswordField passwordField;
    protected PasswordField confirmPasswordField;

    void register() {
        DAO DAO = getDAO();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (DAO.emailAlreadyExists(email)) {
            showAlert("Email already exists", "Please enter a new email");
            emailField.clear();
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match", "Please re-enter the password");
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            if (DAO.addRow(name, getSpecificField(), email, password)){
                addRowToDetails(DAO, email);
                showConfirmationDialog("Registration Completed", "Registration successfully completed!");
                Dashboard dashboard = getDashboard(DAO.getIdByEmail(email));
                showNextInterface(dashboard);
            }
        }
    }

    abstract DAO getDAO();

    abstract void addRowToDetails(DAO DAO, String email);

    abstract Object getSpecificField();

    abstract Dashboard getDashboard(int id);
}
