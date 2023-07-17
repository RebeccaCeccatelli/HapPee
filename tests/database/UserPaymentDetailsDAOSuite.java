package database;

import org.junit.Assert;
import org.junit.Test;

public class UserPaymentDetailsDAOSuite {
    UserPaymentDetailsDAO userPaymentDetailsDAO = new UserPaymentDetailsDAO();

    @Test
    public void addTest() {
        int previousSize = userPaymentDetailsDAO.getTableSize();
        String randomString = Utils.generateRandomString();

        UserDAO userDAO = new UserDAO();
        userDAO.add(randomString, randomString, randomString, randomString);
        int userId = userDAO.getIdByEmail(randomString);

        userPaymentDetailsDAO.add(userId);

        try {
            Assert.assertEquals(previousSize +1, userPaymentDetailsDAO.getTableSize());
        }
        finally {
            cleanUp(userId);
        }
    }

    @Test
    public void getIdByUserIdTest() {
        Assert.assertEquals(13, userPaymentDetailsDAO.getIdByUserId(32));
    }

    private void cleanUp(int userId) {
        userPaymentDetailsDAO.deleteTestUserPaymentDetails(userId);
        new UserDAO().deleteTestUser(userId);
    }
}
