package backend;

import database.UserTableManager;

public class UserInformation extends AccountInformation {
    private String surname;

    public UserInformation(int id) {
        super(id, new UserTableManager());
        this.surname = new UserTableManager().getStringFromDB(id, "surname");
    }

    public void saveSpecificField(Object... params) {
        String newSurname = (String) params[0];
        setSurname(newSurname);
        tableManager.update(id, "surname", newSurname);
    }

    private void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    public Object getSpecificField() {
        return surname;
    }
}
