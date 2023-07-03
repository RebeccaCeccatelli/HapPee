package backend;

import java.util.ArrayList;

public class Map {
    private ArrayList<Business> businesses = new ArrayList<>();

    public void addBusiness(Business localBusiness) {
        businesses.add(localBusiness);
    }

    public void removeBusiness(Business localBusiness) {
        businesses.remove(localBusiness);
    }

    public void findRestroomsNearMe(User user) {

    }

}
