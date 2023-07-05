package backend;

import java.sql.Time;

public class Business extends Account {
    private BusinessDetails details;
    private Position position;

    public Business(int id) {
        this.information = new BusinessInformation(id);
        this.details = new BusinessDetails(id);
    }

    public BusinessDetails getDetails() {
        return details;
    }

    public void saveAccessPrice(float accessPrice) {
        details.saveAccessPrice(accessPrice);
    }

    public void saveBusinessType(String businessType) {
        details.saveBusinessType(businessType);
    }

    public void saveTime(Time time, String type) {
        details.saveTime(time, type);
    }
}
