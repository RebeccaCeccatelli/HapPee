package database;

import backend.Address;
import backend.Business;
import org.junit.Assert;
import org.junit.Test;

public class AddressDAOSuite {
    AddressDAO addressDao = new AddressDAO();

    @Test
    public void addRowTest() {
        int oldMaxId = addressDao.getMaxId();
        Address address = new Address("test", "test", "test", "test", "test");

        addressDao.addRow(address);

        Assert.assertEquals(oldMaxId + 1, addressDao.getMaxId());
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
    }

}
