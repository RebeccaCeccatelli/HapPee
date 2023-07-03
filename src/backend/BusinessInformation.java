package backend;

import database.BusinessTableManager;

public class BusinessInformation extends AccountInformation{
    private Address address;

    public BusinessInformation(String email) {
        super(email, new BusinessTableManager());
        this.address = new BusinessTableManager().getAddressFromDatabase(email);
    }

}
