package backend;


import database.ReviewDAO;
import database.UserPaymentDetailsDAO;

public class User  extends Account{
    private String subscription;
    private float creditBalance;
    private UserPaymentDetailsDAO userPaymentDetailsDAO = new UserPaymentDetailsDAO();

    public User(int id) {
        information = new UserInformation(id);
        reviews = new ReviewDAO().getReviewsByAccountId(id, "user_id");
        int userPaymentDetailsId = userPaymentDetailsDAO.getIdFromUserId(id);
        subscription = userPaymentDetailsDAO.getStringFromDB(userPaymentDetailsId, "subscription");
        creditBalance = userPaymentDetailsDAO.getFLoatFromDB(userPaymentDetailsId, "credit");
    }

    public boolean upgradeToPremiumSubscription() {
        if (creditBalance >= 100) {
            creditBalance -= 100;
            saveSubscription("premium");
            return true;
        }
        else{
            return false;
        }
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

    public void topUpCredit(float amount) {
        setCredit(amount);
        saveCreditBalance();
    }

    public void saveCreditBalance() {
        userPaymentDetailsDAO.update(
                userPaymentDetailsDAO.getIdFromUserId(getId()), "credit", creditBalance
        );
    }

    public void setCredit(float amount) {
        creditBalance += amount;
    }

    public float getCreditBalance() {
        return creditBalance;
    }

    private void saveSubscription(String subscription) {
        setSubscription(subscription);

        userPaymentDetailsDAO.update(
                userPaymentDetailsDAO.getIdFromUserId(getId()), "subscription", "premium" );
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getSubscription() {
        return subscription;
    }

    public void saveSpecificField(Object... params) {
        information.saveSpecificField(params);
    }

    public Object getSpecificField() {
        return information.getSpecificField();
    }

    public void saveReview(int businessId, String text, float rating) {
        Review review = new Review(getId(), businessId, text, rating);
        addReview(review);
        review.save();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
