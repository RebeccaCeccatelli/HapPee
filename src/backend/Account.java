package backend;

import database.ReviewDAO;

import java.util.ArrayList;

public abstract class Account {
    protected AccountInformation information;
    protected ArrayList<Review> reviews;

    public Account(int id) {
        this.information = createInformation(id);
        this.reviews = new ReviewDAO().getReviewsByAccountId(id, getIdType());
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

    public void saveSpecificField(Object... params) {information.saveSpecificField(params); }

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

    public Object getSpecificField() {return information.getSpecificField(); }

    public AccountInformation getInformation() { return information; }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    protected abstract String getIdType();

    protected abstract AccountInformation createInformation(int id);

}

