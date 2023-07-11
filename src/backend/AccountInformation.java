package backend;

import database.DAO;

public abstract class AccountInformation {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected DAO DAO;

    public AccountInformation(int id, DAO DAO) {
        this.DAO = DAO;
        this.id = id;
        this.name = DAO.getStringFromDB(id, "name");
        this.email = DAO.getStringFromDB(id, "name");
        this.password = DAO.getStringFromDB(id, "password");
    }

    void saveName(String newName) {
        setName(newName);
        DAO.update(id, "name", newName);
    }

    void saveEmail(String newEmail) {
        setEmail(newEmail);
        DAO.update(id, "email", newEmail);
    }

    void savePassword(String newPassword) {
        setPassword(newPassword);
        DAO.update(id, "password", newPassword);
    }

    abstract void saveSpecificField(Object... params);

    private void setName(String newName) {
        this.name = newName;
    }

    private void setEmail(String newEmail) {
        this.email = newEmail;
    }

    private void setPassword(String newPassword) {
        this.password = newPassword;
    }

    int getId() {
        return this.id;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    String getPassword() {
        return password;
    }

    abstract Object getSpecificField();
}
