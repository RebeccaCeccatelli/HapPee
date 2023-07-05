package backend;

import database.UserTableManager;

public class UserInformation extends AccountInformation {
    private String surname;

    public UserInformation(int id) {
        super(id, new UserTableManager());
        this.surname = new UserTableManager().getStringFromDB(id, "surname");
    }
}
