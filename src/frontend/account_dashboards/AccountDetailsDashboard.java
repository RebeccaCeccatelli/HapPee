package frontend.account_dashboards;

import backend.Account;
import frontend.Interface;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public abstract class AccountDetailsDashboard extends Interface {
    protected Account account;

    protected void saveName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify Name");
        dialog.setHeaderText("Enter a new name:");

        String currentName = account.getName();
        dialog.setContentText("Current Name: " + currentName);

        dialog.getDialogPane().setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            account.saveName(newName);
            showConfirmationDialog("Save new name", "New name successfully saved!");
        });
    }

    protected void saveEmail() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify Email");
        dialog.setHeaderText("Enter a new email:");

        String currentEmail = account.getEmail();
        dialog.setContentText("Current Email: " + currentEmail);

        dialog.getDialogPane().setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newEmail -> {
            account.saveEmail(newEmail);
            showConfirmationDialog("Save new email", "New email successfully saved!");
        });
    }

    protected void savePassword() {
        TextInputDialog oldPasswordDialog = new TextInputDialog();
        oldPasswordDialog.setTitle("Modify Password");
        oldPasswordDialog.setHeaderText("Enter your old password:");
        oldPasswordDialog.setContentText("Old password:");

        oldPasswordDialog.getDialogPane().setGraphic(null);
        Optional<String> oldPasswordResult = oldPasswordDialog.showAndWait();
        oldPasswordResult.ifPresent(oldPassword -> {
            if (verifyOldPassword(oldPassword)) {
                TextInputDialog newPasswordDialog = new TextInputDialog();
                newPasswordDialog.setTitle("Modify Password");
                newPasswordDialog.setHeaderText("Enter a new password:");
                newPasswordDialog.setContentText("New password:");

                String currentPassword = account.getPassword();

                newPasswordDialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                    newPasswordDialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(newValue.equals(currentPassword));
                });

                newPasswordDialog.getDialogPane().setGraphic(null);
                Optional<String> newPasswordResult = newPasswordDialog.showAndWait();
                newPasswordResult.ifPresent(newPassword -> {
                    account.savePassword(newPassword);
                    showConfirmationDialog("Save new password", "New password successfully saved!");
                });
            } else {
                showAlert("Incorrect password", "The old password you entered is not correct. Try again.");
                savePassword();
            }
        });
    }

    private boolean verifyOldPassword(String oldPassword) {
        return oldPassword.equals(account.getPassword());
    }
}
