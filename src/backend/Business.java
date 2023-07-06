package backend;

import database.ReviewTableManager;

import java.sql.Time;

public class Business extends Account {
    private BusinessDetails details;
    private Position position;

    public Business(int id) {
        this.information = new BusinessInformation(id);
        this.details = new BusinessDetails(id);
        this.reviews = new ReviewTableManager().getReviewsByAccountId(id, "business_id");
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

    public void saveSpecificField(Object... params) {
        //TODO
    }

    public float getAverageRating() {
        float average = 0;
        for (Review review : reviews) {
            average += review.getRating();
        }
        return average / reviews.size();
    }

    public Object getSpecificField() {
        return information.getSpecificField();
    }
}
