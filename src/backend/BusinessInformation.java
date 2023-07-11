package backend;

import database.AddressDAO;
import database.BusinessDAO;

public class BusinessInformation extends AccountInformation{
    private Address address;

    public BusinessInformation(int businessId) {
        super(businessId, new BusinessDAO());
        this.address = new AddressDAO().getAddressByBusinessId(businessId);
    }

    //The specific field is address
    void saveSpecificField(Object... params) {
        setAddress(params[0], params[1], params[2], params[3], params[4]);
        AddressDAO addressDAO = new AddressDAO();
        int addressId = new BusinessDAO().getIntFromDB(getId(), "address_id");
        addressDAO.update(addressId, "street", params[0]);
        addressDAO.update(addressId, "civic_number", params[1]);
        addressDAO.update(addressId, "postcode", params[2]);
        addressDAO.update(addressId, "city", params[3]);
        addressDAO.update(addressId, "country", params[4]);
    }

    private void setAddress(Object... params) {
        address.setStreet((String) params[0]);
        address.setCivicNumber((String) params[1]);
        address.setPostCode((String) params[2]);
        address.setCity((String) params[3]);
        address.setCountry((String) params[4]);
    }

    Object getSpecificField() {
        return address;
    }
}
