package database;

import backend.Address;
import backend.Business;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class BusinessDAOSuite {
    BusinessDAO businessDAO = new BusinessDAO();

    @Test
    public void addRowTest() {
        BusinessDAO thisTestBusinessDAO = new BusinessDAO();
        int oldMaxId = thisTestBusinessDAO.getMaxId();
        String test = generateRandomString();
        Address testAddress = new Address(test, test, test, test, test);
        thisTestBusinessDAO.addRow(test, testAddress, test, test);

        Assert.assertEquals(oldMaxId + 1, thisTestBusinessDAO.getMaxId());
    }

    @Test
    public void checkIfBusinessRegisteredTest() {
        BusinessDAO thisTestBusinessDAO = new BusinessDAO();
        String registeredTest = generateRandomString();
        Address testAddress = new Address(registeredTest, registeredTest, registeredTest, registeredTest, registeredTest);
        thisTestBusinessDAO.addRow(registeredTest, testAddress, registeredTest, registeredTest);

        Assert.assertTrue(thisTestBusinessDAO.checkIfBusinessRegistered(registeredTest, registeredTest));
        Assert.assertFalse(thisTestBusinessDAO.checkIfBusinessRegistered("notRegisteredTest", "notRegisteredTest"));
    }

    @Test
    public void getIdByBusinessNameTest() {
        Assert.assertEquals(41, businessDAO.getIdByBusinessName("Bar Roberta"));

        Assert.assertEquals(32, businessDAO.getIdByBusinessName("Pizzeria Il Guado"));
    }

    @Test
    public void getBusinessByBusinessIdTest() {
        Business business = new Business(41);

        Assert.assertTrue(Business.equals(business, businessDAO.getBusinessByBusinessId(41)));
    }

    @Test
    public void getBusinessByAddressIdTest() {
        Business business = new Business(41);
        Assert.assertTrue(Business.equals(business, businessDAO.getBusinessByAddressId(47)));
    }

    @Test
    public void updateTest() {
        String newName = "Bar Roberto";
        businessDAO.update(41, "name", newName);

        Business newBusiness = new Business(41);
        Assert.assertEquals(newName, newBusiness.getName());

        String oldName = "Bar Roberta";
        businessDAO.update(41, "name", oldName);

        Business oldBusiness = new Business(41);
        Assert.assertEquals(oldName, oldBusiness.getName());
    }

    public static String generateRandomString() {
        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int STRING_LENGTH = 5;
        StringBuilder stringBuilder = new StringBuilder(STRING_LENGTH);
        Random random = new Random();

        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
