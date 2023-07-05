package backend;

public abstract class Account {
    protected AccountInformation information;

    public int getId() {
        return information.getId();
    }

    public String getName() {
        return information.getName();
    }

    public String getEmail() {
        return information.getEmail();
    }

    public String getPassword() {
        return information.getPassword();
    }

    public void saveName(String newName) {
        information.saveName(newName);
    }

    public void saveEmail(String newEmail) {
        information.saveEmail(newEmail);
    }

    public void savePassword(String newPassword) {
        information.savePassword(newPassword);
    }
}

