package database;

import backend.Address;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;

public class BusinessDetailsDAOSuite {
    private BusinessDetailsDAO businessDetailsDAO = new BusinessDetailsDAO();

    @Test
    public void addRowTest() {
        int previousSize = businessDetailsDAO.getTableSize();
        String randomString = Utils.generateRandomString();
        Address address = new Address(randomString, randomString, randomString, randomString, randomString);

        int businessId = 0;
        try {
            new BusinessDAO().addRow(randomString, address, randomString, randomString);
            businessId = new BusinessDAO().getIdByBusinessName(randomString);
            businessDetailsDAO.addRow("business_id", businessId);

            Assert.assertEquals(previousSize + 1, businessDetailsDAO.getTableSize());
        }
        finally {
            cleanUp(businessId, randomString);
        }
    }

    @Test
    public void getIdByBusinessIdTest() {
        Assert.assertEquals(18, businessDetailsDAO.getIdByBusinessId(32));
    }

    @Test
    public void getTimeFromDBTest() {
        Assert.assertEquals(Time.valueOf("06:00:00"), businessDetailsDAO.getTimeFromDB(27, "opening_time"));
    }

    @Test
    public void getDoubleFromDBTest() {
        businessDetailsDAO.update(27,"single_access_price", 1);
        Assert.assertEquals(1, businessDetailsDAO.getDoubleFromDB(27, "single_access_price"), 0);

        businessDetailsDAO.update(27, "single_access_price", 0.70);
        Assert.assertEquals(0.70, businessDetailsDAO.getDoubleFromDB(27, "single_access_price"), 0);
    }

    private void cleanUp(int businessId, String randomString) {
        businessDetailsDAO.deleteBusinessDetails(businessId);
        new AddressDAO().deleteTestAddresses(randomString, randomString);
        new BusinessDAO().deleteTestBusiness(randomString);
    }
}
