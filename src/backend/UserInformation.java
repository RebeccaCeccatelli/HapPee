package backend;

import database.UserTableManager;

public class UserInformation extends AccountInformation {
    private String surname;

    public UserInformation(String email) {
        super(email, new UserTableManager());
        this.surname = new UserTableManager().getFromDatabase(email, "surname");
    }

}
