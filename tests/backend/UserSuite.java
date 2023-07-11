package backend;

import org.junit.Assert;
import org.junit.Test;

public class UserSuite {
    private User user = new User(32);

    //tests both saveCreditBalance() and topUpCredit()
    @Test
    public void topUpCreditTest() {
        float oldCredit = user.getCreditBalance();
        user.topUpCredit(20);
        float newCredit = user.getCreditBalance();

        Assert.assertEquals(oldCredit + 20, newCredit, 0.0);
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
    }

    @Test
    public void payTest() {
        float oldBalance = user.getCreditBalance();
        final float amountToPay = 10;
        if(user.pay(amountToPay)) {
            Assert.assertEquals(oldBalance - amountToPay, user.getCreditBalance(), 0);
        }
        else {
            Assert.assertEquals(oldBalance, user.getCreditBalance());
        }
    }

    //tests also UserInformation::saveSpecificField()
    @Test
    public void saveSpecificFieldTest() {
        String newSurname = "Verdi";

        user.saveSpecificField(newSurname);

        Assert.assertEquals(newSurname, user.getSpecificField());
    }
}
