package backend;

import database.BusinessDetailsDAO;

import java.sql.*;

public class BusinessDetails {
    private int id;
    private String businessType;
    private float singleAccessPrice;
    private Time openingTime;
    private Time closingTime;
    private int accessCode;
    private BusinessDetailsDAO businessDetailsTableManager = new BusinessDetailsDAO();


    public BusinessDetails(int businessId) {
        this.id = businessDetailsTableManager.getIdFromBusinessId(businessId);
        this.businessType = businessDetailsTableManager.getStringFromDB(id, "business_type");
        this.singleAccessPrice = businessDetailsTableManager.getFLoatFromDB(id, "single_access_price");
        this.openingTime = businessDetailsTableManager.getTimeFromDB(id, "opening_time");
        this.closingTime = businessDetailsTableManager.getTimeFromDB(id, "closing_time");
        this.accessCode = businessDetailsTableManager.getIntFromDB(id, "access_code");
    }

    public float getAccessPrice() {
        return singleAccessPrice;
    }

    public int getAccessCode() { return accessCode; }

    public String getBusinessType() {
        return businessType;
    }

    public void saveAccessPrice(float accessPrice) {
        setAccessPrice(accessPrice);
        businessDetailsTableManager.update(id, "single_access_price", accessPrice);
    }

    public void saveAccessCode(int accessCode) {
        setAccessCode(accessCode);
        businessDetailsTableManager.update(id, "access_code", accessCode);
    }

    private void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public void saveBusinessType(String businessType) {
        setBusinessType(businessType);
        businessDetailsTableManager.update(id, "business_type", businessType);
    }

    private void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private void setAccessPrice(float accessPrice) {
        this.singleAccessPrice = accessPrice;
    }

    public void saveTime(Time time, String type) {
        setTime(time, type);
        businessDetailsTableManager.update(id, type, time);
    }

    private void setTime(Time time, String type) {
        if (type.equals("opening_time")) {
            this.openingTime = time;
        }
        else if (type.equals("closing_time")) {
            this.closingTime = time;
        }
    }

    public String getOpeningTime() {
        return openingTime.toString();
    }

    public String getClosingTime() {
        return closingTime.toString();
    }

}
