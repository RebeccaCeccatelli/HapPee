package frontend.account_dashboards.user_dashboards;

import backend.User;
import frontend.account_dashboards.AccountDetailsDashboard;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.Optional;

public class UserAccountDetailsDashboard extends AccountDetailsDashboard {

    public UserAccountDetailsDashboard(User user) {
        this.account = user;
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setGridPane();

        Label optionLabel = new Label("Select an option: ");
        Button modifyNameButton = createButton("Modify user name", e -> saveName());
        Button modifySurnameButton = createButton("Modify user surname", e -> saveSurname());
        Button modifyEmailButton = createButton("Modify email", e -> saveEmail());
        Button modifyPasswordButton = createButton("Change password", e -> savePassword());
        Button backButton = createButton("Back", e -> goBack());

        addToGridPane(optionLabel, 0, 0);
        addToGridPane(modifyNameButton, 0, 1);
        addToGridPane(modifySurnameButton, 0, 2);
        addToGridPane(modifyEmailButton, 0, 3);
        addToGridPane(modifyPasswordButton, 0, 4);
        addToGridPane(backButton, 0, 5);

        showCurrentInterface("Modify Account Details");
    }

    private void saveSurname() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify Surname");
        dialog.setHeaderText("Enter a new surname:");

        String currentSurname = (String) account.getSpecificField();
        dialog.setContentText("Current Surname: " + currentSurname);

        dialog.getDialogPane().setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newSurname -> {
            account.saveSpecificField(newSurname);
            showConfirmationDialog("Save new surname", "New surname successfully saved!");
        });
    }

}
