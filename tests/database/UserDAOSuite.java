package database;

import org.junit.Assert;
import org.junit.Test;

public class UserDAOSuite {
    UserDAO userDAO = new UserDAO();

    @Test
    public void addRowTest() {
        int previousSize = userDAO.getTableSize();
        String randomString = Utils.generateRandomString();

        userDAO.add(randomString, randomString, randomString, randomString);
        try {
            Assert.assertEquals(previousSize + 1, userDAO.getTableSize());
        }
        finally {
            cleanUp(userDAO.getIdByEmail(randomString));
        }
    }

    @Test
    public void checkIfUserRegisteredTest() {
        String randomString = Utils.generateRandomString();
        userDAO.add(randomString, randomString, randomString, randomString);

        try {
            Assert.assertTrue(userDAO.checkIfUserRegistered(randomString, randomString));
            Assert.assertFalse(userDAO.checkIfUserRegistered("notRegisteredTest", "notRegisteredTest"));
        }
        finally {
            cleanUp(userDAO.getIdByEmail(randomString));
        }
    }

    @Test
    public void getStringFromDBTest() {
        Assert.assertEquals("Marco", userDAO.getStringFromDB(32, "name"));
    }

    @Test
    public void getIdByEmailTest() {
        Assert.assertEquals(32, userDAO.getIdByEmail("marcorossi@gmail.com"));
    }

    private void cleanUp(int userId) {
        userDAO.deleteTestUser(userId);
    }

}
