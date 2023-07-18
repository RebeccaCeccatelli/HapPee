package backend;

import database.UserPaymentDetailsDAO;

public class User  extends Account{
    private static final float PREMIUM_SUBSCRIPTION_COST = 100;
    private String subscription;
    private double creditBalance;
    private UserPaymentDetailsDAO userPaymentDetailsDAO = new UserPaymentDetailsDAO();

    public User(int id) {
        super(id);

        int userPaymentDetailsId = userPaymentDetailsDAO.getIdByUserId(id);
        subscription = userPaymentDetailsDAO.getStringFromDB(userPaymentDetailsId, "subscription");
        creditBalance = userPaymentDetailsDAO.getDoubleFromDB(userPaymentDetailsId, "credit");
    }

    private void saveCreditBalance() {
        userPaymentDetailsDAO.update(
                userPaymentDetailsDAO.getIdByUserId(getId()), "credit", creditBalance
        );
    }

    private void saveSubscription(String subscription) {
        setSubscription(subscription);

        userPaymentDetailsDAO.update(
                userPaymentDetailsDAO.getIdByUserId(getId()), "subscription", "premium" );
    }

    public void saveReview(int businessId, String text, float rating) {
        Review review = new Review(getId(), businessId, text, rating);
        addReview(review);
        review.save();
    }

    public boolean upgradeToPremiumSubscription() {
        if (creditBalance >= PREMIUM_SUBSCRIPTION_COST) {
            creditBalance -= PREMIUM_SUBSCRIPTION_COST;
            saveSubscription("premium");
            return true;
        }
        else{
            return false;
        }
    }

    //method created for testing purposes
    public void downgradeToStandardSubscription() {
        saveSubscription("standard");
    }

    public void topUpCredit(float amount) {
        setCredit(amount);
        saveCreditBalance();
    }

    public boolean pay(double amount) {
        if (amount > creditBalance) {
            return false;
        }
        else {
            this.creditBalance -= amount;
            return true;
        }
    }

    private void addReview(Review review) {
        reviews.add(review);
    }

    private void setCredit(float amount) {
        creditBalance += amount;
    }

    private void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    public String getSubscription() {
        return subscription;
    }

    protected String getIdType() {
        return "user_id";
    }

    protected AccountInformation retrieveAccountInformation(int id) {
        return new UserInformation(id);
    }
}
