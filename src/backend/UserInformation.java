package backend;

import database.UserDAO;

public class UserInformation extends AccountInformation {
    private String surname;

    public UserInformation(int id) {
        super(id, new UserDAO());
        this.surname = new UserDAO().getStringFromDB(id, "surname");
    }

    //The specific field is surname
    void saveSpecificField(Object... params) {
        String newSurname = (String) params[0];
        setSurname(newSurname);
        DAO.update(id, "surname", newSurname);
    }

    private void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    Object getSpecificField() {
        return surname;
    }
}
