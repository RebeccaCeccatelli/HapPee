package backend;

import database.DAO;

public abstract class AccountInformation {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected DAO tableManager;

    public AccountInformation(int id, DAO tableManager) {
        this.tableManager = tableManager;
        this.id = id;
        this.name = tableManager.getStringFromDB(id, "name");
        this.email = tableManager.getStringFromDB(id, "name");
        this.password = tableManager.getStringFromDB(id, "password");
    }
    public void saveName(String newName) {
        setName(newName);
        tableManager.update(id, "name", newName);
    }

    private void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void saveEmail(String newEmail) {
        setEmail(newEmail);
        tableManager.update(id, "email", newEmail);
    }

    private void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void savePassword(String newPassword) {
        setPassword(newPassword);
        tableManager.update(id, "password", newPassword);
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
