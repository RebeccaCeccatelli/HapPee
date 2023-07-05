package backend;

import database.TableManager;
import database.UserTableManager;

public class User  extends Account{
    private Position position;
    private Subscription subscription;

    public User(int id) {
        information = new UserInformation(id);
    }

    public void setSubscription(String type) {
        if(!type.equals("standard")) {
            subscription = new PremiumSubscription();
        }
        else {
            subscription = new StandardSubscription();
        }
    }

}
