package backend;

import database.BusinessDetailsDAO;

import java.sql.*;
import java.util.Objects;

public class BusinessDetails {
    private int id;
    private String businessType;
    private double singleAccessPrice;
    private Time openingTime;
    private Time closingTime;
    private int accessCode;
    private BusinessDetailsDAO businessDetailsDAO = new BusinessDetailsDAO();

    public BusinessDetails(int businessId) {
        this.id = businessDetailsDAO.getIdByBusinessId(businessId);
        this.businessType = businessDetailsDAO.getStringFromDB(id, "business_type");
        this.singleAccessPrice = businessDetailsDAO.getDoubleFromDB(id, "single_access_price");
        this.openingTime = businessDetailsDAO.getTimeFromDB(id, "opening_time");
        this.closingTime = businessDetailsDAO.getTimeFromDB(id, "closing_time");
        this.accessCode = businessDetailsDAO.getIntFromDB(id, "access_code");
    }

    void saveBusinessType(String businessType) {
        setBusinessType(businessType);
        businessDetailsDAO.update(id, "business_type", businessType);
    }

    void saveAccessPrice(double accessPrice) {
        setAccessPrice(accessPrice);
        businessDetailsDAO.update(id, "single_access_price", accessPrice);
    }

    void saveAccessCode(int accessCode) {
        setAccessCode(accessCode);
        businessDetailsDAO.update(id, "access_code", accessCode);
    }

    void saveTime(Time time, String type) {
        setTime(time, type);
        businessDetailsDAO.update(id, type, time);
    }

    private void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    private void setAccessPrice(double accessPrice) {
        this.singleAccessPrice = accessPrice;
    }

    private void setTime(Time time, String type) {
        if (type.equals("opening_time")) {
            this.openingTime = time;
        }
        else if (type.equals("closing_time")) {
            this.closingTime = time;
        }
    }

    public int getId() { return id; }

    public String getBusinessType() {
        return businessType;
    }

    public double getAccessPrice() {
        return singleAccessPrice;
    }

    public int getAccessCode() { return accessCode; }

    public String getOpeningTime() {
        return openingTime.toString();
    }

    public String getClosingTime() {
        return closingTime.toString();
    }

    public static boolean equals(BusinessDetails first, BusinessDetails second) {
        if (first.getId() == second.getId()) {
            if (Objects.equals(first.getBusinessType(), second.getBusinessType())) {
                if (first.getAccessPrice() == second.getAccessPrice()) {
                    if (Objects.equals(first.getOpeningTime(), second.getOpeningTime())) {
                        if (Objects.equals(first.getClosingTime(), second.getClosingTime())) {
                            if (first.getAccessCode() == second.getAccessCode()) {
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
