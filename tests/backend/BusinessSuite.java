package backend;

import database.ReviewDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

public class BusinessSuite {
    private Business business = new Business(41);

    //correspondent savers in BusinessDetails and BusinessInformation also tested through these tests
    @Test
    public void saveBusinessTypeTest() {
        String newBusinessType = "Bar e Punto Pranzo";
        business.saveBusinessType(newBusinessType);
        Assert.assertEquals(business.getDetails().getBusinessType(), newBusinessType);

        String oldBusinessType = "Bar";
        business.saveBusinessType(oldBusinessType);
        Assert.assertEquals(business.getDetails().getBusinessType(), oldBusinessType);
    }

    @Test
    public void saveAccessPriceTest() {
        business.saveAccessPrice(5);
        Assert.assertNotEquals(0.70, business.getDetails().getAccessPrice());
        Assert.assertEquals(5, business.getDetails().getAccessPrice(), 0);

        business.saveAccessPrice(0.70);
        Assert.assertEquals(0.70, business.getDetails().getAccessPrice(), 0);
    }

    @Test
    public void saveAccessCodeTest() {
        int newAccessCode = 11111111;
        business.saveAccessCode(newAccessCode);
        Assert.assertEquals(business.getAccessCode(), newAccessCode);
    }

    @Test
    public void saveTimeTest() {
        Time openingTime = Time.valueOf("06:00:00");
        business.saveTime(openingTime, "opening_time");
        Assert.assertEquals(business.getDetails().getOpeningTime(), "06:00:00");

        Time closingTime = Time.valueOf("22:00:00");
        business.saveTime(closingTime, "closing_time");
        Assert.assertEquals(business.getDetails().getClosingTime(), "22:00:00");
    }

    @Test
    public void saveSpecificFieldTest() {
        String street = "via Milazzo";
        String civicNumber = "35";
        String postcode = "59100";
        String city = "Prato";
        String country = "Italia";

        Address address = new Address(street, civicNumber, postcode, city, country);

        business.saveSpecificField(street, civicNumber, postcode, city, country);
        Assert.assertTrue(Address.equals(address, (Address) business.getSpecificField()));

        business.saveSpecificField("via Pistoiese", "370", "59100", "Prato", "Italia");
        Assert.assertFalse(Address.equals(address, (Address) business.getSpecificField()));
    }

    @Test
    public void getAverageRatingTest() {
        double oldRating = business.getAverageRating();
        int oldSize = business.getReviews().size();
        double oldNumerator = oldRating * oldSize;

        User user = new User(32);

        user.saveReview(41, "test", 3);
        user.saveReview(41,"test",5);
        user.saveReview(41, "test", 2);
        user.saveReview(41, "test", 5);

        double expectedRating = (oldNumerator + 15) / (oldSize + 4);
        double averageRating = new Business(41).getAverageRating();
        Assert.assertEquals(expectedRating, averageRating, 0.0);

        new ReviewDAO().deleteTestReviews(41, "test");
    }

}
