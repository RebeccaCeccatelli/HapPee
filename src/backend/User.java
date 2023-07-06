package backend;


import database.ReviewTableManager;
import database.UserPaymentDetailsTableManager;

public class User  extends Account{
    private Position position;
    private String subscription;
    private float creditBalance;
    private UserPaymentDetailsTableManager userPaymentDetailsTableManager = new UserPaymentDetailsTableManager();

    public User(int id) {
        information = new UserInformation(id);
        reviews = new ReviewTableManager().getReviewsByAccountId(id, "user_id");
        int userPaymentDetailsId = userPaymentDetailsTableManager.getIdFromUserId(id);
        subscription = userPaymentDetailsTableManager.getStringFromDB(userPaymentDetailsId, "subscription");
        creditBalance = userPaymentDetailsTableManager.getFLoatFromDB(userPaymentDetailsId, "credit");
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

    public void topUpCredit(float amount) {
        setCredit(amount);
        saveCreditBalance();
    }

    public void saveCreditBalance() {
        userPaymentDetailsTableManager.update(
                userPaymentDetailsTableManager.getIdFromUserId(getId()), "credit", creditBalance
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

        userPaymentDetailsTableManager.update(
                userPaymentDetailsTableManager.getIdFromUserId(getId()), "subscription", "premium" );
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
