package backend;

import database.TableManager;

public class AccountInformation {
    protected String name;
    protected String email;
    protected String password;

    public AccountInformation(String email, TableManager tableManager) {
        this.name = tableManager.getFromDatabase(email, "name");
        this.email = email;
        this.password = tableManager.getFromDatabase(email, "password");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
