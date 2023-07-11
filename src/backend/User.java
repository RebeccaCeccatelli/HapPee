package backend;

import database.ReviewDAO;
import database.UserPaymentDetailsDAO;

public class User  extends Account{
    private static final float PREMIUM_SUBSCRIPTION_COST = 100;
    private String subscription;
    private float creditBalance;
    private UserPaymentDetailsDAO userPaymentDetailsDAO = new UserPaymentDetailsDAO();

    public User(int id) {
        information = new UserInformation(id);
        reviews = new ReviewDAO().getReviewsByAccountId(id, "user_id");

        int userPaymentDetailsId = userPaymentDetailsDAO.getIdByUserId(id);
        subscription = userPaymentDetailsDAO.getStringFromDB(userPaymentDetailsId, "subscription");
        creditBalance = userPaymentDetailsDAO.getFLoatFromDB(userPaymentDetailsId, "credit");
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

    public void saveSpecificField(Object... params) {
        information.saveSpecificField(params);
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

    public void topUpCredit(float amount) {
        setCredit(amount);
        saveCreditBalance();
    }

    public boolean pay(float amount) {
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

    public float getCreditBalance() {
        return creditBalance;
    }

    public String getSubscription() {
        return subscription;
    }

    public Object getSpecificField() {
        return information.getSpecificField();
    }
}
