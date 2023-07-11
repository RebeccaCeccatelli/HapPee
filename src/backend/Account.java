package backend;

import java.util.ArrayList;

public abstract class Account {
    protected AccountInformation information;
    protected ArrayList<Review> reviews;

    public void saveName(String newName) {
        information.saveName(newName);
    }

    public void saveEmail(String newEmail) {
        information.saveEmail(newEmail);
    }

    public void savePassword(String newPassword) {
        information.savePassword(newPassword);
    }

    public abstract void saveSpecificField(Object... params);

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

    public abstract Object getSpecificField();

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}

