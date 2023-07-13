package backend;

import database.ReviewDAO;
import org.junit.Assert;
import org.junit.Test;

public class UserSuite {
    private User user = new User(32);

    //tests both saveCreditBalance() and topUpCredit()
    @Test
    public void topUpCreditTest() {
        double oldCredit = user.getCreditBalance();
        user.topUpCredit(5);
        double newCredit = user.getCreditBalance();

        Assert.assertEquals(oldCredit + 5, newCredit, 0.0);
    }

    //tests both saveSubscription() and upgradeToPremiumSubscription()
    @Test
    public void upgradeToPremiumSubscriptionTest() {
        if (user.upgradeToPremiumSubscription()) {
            Assert.assertEquals("premium", user.getSubscription());
            user.downgradeToStandardSubscription();
        }
        else {
            Assert.assertEquals("standard", user.getSubscription());
        }
    }

    //tests at the same time save(), addReview() and saveReview()
    @Test
    public void saveReviewTest() {
        int oldReviewsSize = user.getReviews().size();

        user.saveReview(41,"test", 4);

        Assert.assertEquals(oldReviewsSize + 1, new User(32).getReviews().size());

        new ReviewDAO().deleteTestReviews(41, "test");
    }

    @Test
    public void payTest() {
        double oldBalance = user.getCreditBalance();
        final double amountToPay = 10;
        if(user.pay(amountToPay)) {
            Assert.assertEquals(oldBalance - amountToPay, user.getCreditBalance(), 0);
        }
        else {
            Assert.assertEquals(oldBalance, user.getCreditBalance(),0);
        }
    }

    //tests also UserInformation::saveSpecificField()
    @Test
    public void saveSpecificFieldTest() {
        String newSurname = "Verdi";

        user.saveSpecificField(newSurname);
        Assert.assertEquals(newSurname, user.getSpecificField());

        user.saveSpecificField("Rossi");
        Assert.assertEquals("Rossi", user.getSpecificField());
    }
}
