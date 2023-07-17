package database;

import backend.Address;
import backend.Business;
import org.junit.Assert;
import org.junit.Test;

public class AddressDAOSuite {
    AddressDAO addressDao = new AddressDAO();

    @Test
    public void addTest() {
        int previousSize = addressDao.getTableSize();
        String test = "test";
        Address address = new Address(test, test, test, test, test);

        addressDao.add(address);
        try {
            Assert.assertEquals(previousSize + 1, addressDao.getTableSize());
        }
        finally {
            addressDao.deleteTestAddresses(test, test);
        }
    }

    @Test
    public void getAddressByBusinessIdTest() {
        Business business = new Business(41);
        business.saveSpecificField("via Pistoiese", "370", "59100", "Prato", "Italia");

        Address address = addressDao.getAddressByBusinessId(41);

        Assert.assertTrue(Address.equals(new Address("via Pistoiese", "370", "59100", "Prato", "Italia"), address));
    }

    @Test
    public void updateTest() {
        String newStreet = "via Cellerese";
        String newCivicNumber = "17";
        addressDao.update(47, "street", newStreet);
        addressDao.update(47, "civic_number", newCivicNumber);

        Business business = new Business(41);
        Assert.assertEquals(newStreet, ((Address) business.getSpecificField()).getStreet());
        Assert.assertEquals(newCivicNumber, ((Address) business.getSpecificField()).getCivicNumber());

        String oldStreet = "via Pistoiese";
        String oldCivicNumber = "370";
        addressDao.update(47, "street", oldStreet);
        addressDao.update(47, "civic_number", oldCivicNumber);
        business = new Business(41);
        Assert.assertEquals(oldStreet, ((Address) business.getSpecificField()).getStreet());
        Assert.assertEquals(oldCivicNumber, ((Address) business.getSpecificField()).getCivicNumber());
    }

}
