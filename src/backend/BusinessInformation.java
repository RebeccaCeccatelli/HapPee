package backend;

import database.AddressDAO;
import database.BusinessDAO;

public class BusinessInformation extends AccountInformation{
    private Address address;

    public BusinessInformation(int businessId) {
        super(businessId, new BusinessDAO());
        this.address = new BusinessDAO().getAddressFromDatabase(businessId);
    }

    public void saveSpecificField(Object... params) {
        setAddress(params[0], params[1], params[2], params[3], params[4]);
        AddressDAO addressTableManager = new AddressDAO();
        int addressId = new BusinessDAO().getIntFromDB(getId(), "address_id");
        addressTableManager.update(addressId, "street", params[0]);
        addressTableManager.update(addressId, "civic_number", params[1]);
        addressTableManager.update(addressId, "postcode", params[2]);
        addressTableManager.update(addressId, "city", params[3]);
        addressTableManager.update(addressId, "country", params[4]);
    }

    public void setAddress(Object... params) {
        address.setStreet((String) params[0]);
        address.setCivicNumber((String) params[1]);
        address.setPostCode((String) params[2]);
        address.setCity((String) params[3]);
        address.setCountry((String) params[4]);
    }

    public Object getSpecificField() {
        return address;
    }

}
