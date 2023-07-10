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
    public void saveName(String newName) {
        setName(newName);
        DAO.update(id, "name", newName);
    }

    private void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void saveEmail(String newEmail) {
        setEmail(newEmail);
        DAO.update(id, "email", newEmail);
    }

    private void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void savePassword(String newPassword) {
        setPassword(newPassword);
        DAO.update(id, "password", newPassword);
    }

    private void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return this.id;
    }

    public abstract void saveSpecificField(Object... params);

    public abstract Object getSpecificField();
}
