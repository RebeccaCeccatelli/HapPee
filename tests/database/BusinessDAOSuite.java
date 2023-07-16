package database;

import backend.Address;
import backend.Business;
import org.junit.Assert;
import org.junit.Test;

public class BusinessDAOSuite {
    BusinessDAO businessDAO = new BusinessDAO();

    @Test
    public void addRowTest() {
        int previousSize = businessDAO.getTableSize();
        String randomString = Utils.generateRandomString();
        Address testAddress = new Address(randomString, randomString, randomString, randomString, randomString);

        businessDAO.add(randomString, testAddress, randomString, randomString);
        try {
            Assert.assertEquals(previousSize + 1, businessDAO.getTableSize());
        }
        finally {
            cleanUp(randomString);
        }
    }

    @Test
    public void checkIfBusinessRegisteredTest() {
        String randomString = Utils.generateRandomString();
        Address testAddress = new Address(randomString, randomString, randomString, randomString, randomString);

        businessDAO.add(randomString, testAddress, randomString, randomString);
        try {
            Assert.assertTrue(businessDAO.checkIfBusinessRegistered(randomString, randomString));
            Assert.assertFalse(businessDAO.checkIfBusinessRegistered("notRegisteredTest", "notRegisteredTest"));
        }
        finally {
            cleanUp(randomString);
        }
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

    @Test
    public void emailAlreadyExistsTest() {
        String randomString = Utils.generateRandomString();

        Assert.assertFalse(businessDAO.emailAlreadyExists(randomString));

        Address address = new Address(randomString, randomString, randomString, randomString ,randomString);
        businessDAO.add(randomString, address, randomString, randomString);

        try {
            Assert.assertTrue(businessDAO.emailAlreadyExists(randomString));
        }
        finally {
            cleanUp(randomString);
        }
    }

    @Test
    public void getIdByEmailTest() {
        Assert.assertEquals(31, businessDAO.getIdByEmail("pizzaquadra@libero.it"));

        Assert.assertEquals(41, businessDAO.getIdByEmail("barroberta@gmail.com"));
    }

    @Test
    public void getIntFromDBTest() {
        Assert.assertEquals(47, businessDAO.getIntFromDB(41, "address_id"));
    }


    private void cleanUp(String key) {
        new AddressDAO().deleteTestAddresses(key, key);
        businessDAO.deleteTestBusiness(key);
    }
}
