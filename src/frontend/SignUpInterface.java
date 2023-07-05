package frontend;

import database.TableManager;
import javafx.scene.control.*;

public abstract class SignUpInterface extends Interface {
    protected TextField nameField;
    protected TextField emailField;
    protected PasswordField passwordField;
    protected PasswordField confirmPasswordField;

    protected void register() {
        TableManager tableManager = getTableManager();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (tableManager.emailAlreadyExists(email)) {
            showAlert("Email already exists", "Please enter a new email");
            emailField.clear();
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match", "Please re-enter the password");
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            if (tableManager.addNewRow(name, getSpecificField(), email, password)){
                optional(tableManager, email);
                showConfirmationDialog("Registration Completed", "Registration successfully completed!");
                Dashboard dashboard = getDashboard(tableManager.getAccountId(email));
                showNextInterface(dashboard);
            }
        }
    }

    protected abstract TableManager getTableManager();

    //FIXME this method probably to be changed
    protected void optional(TableManager tableManager, String email){}

    protected abstract Object getSpecificField();

    protected abstract Dashboard getDashboard(int id);

}
