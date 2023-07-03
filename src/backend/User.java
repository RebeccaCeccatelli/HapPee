package backend;

public class User {
    private UserInformation userInformation;
    private Position position;
    private Subscription subscription;

    public User(String email) {
        userInformation = new UserInformation(email);

    }

    public void setPosition(Position position) {
        this.position = position;
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
