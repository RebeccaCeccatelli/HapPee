package backend;

public class Address{
    private String street;
    private String civicNumber;
    private String postCode;
    private String city;
    private String country;

    public Address(String street, String civicNumber, String postCode, String city, String country){
        this.street = street;
        this.civicNumber = civicNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    void setStreet(String street) {
        this.street = street;
    }

    void setCivicNumber(String civicNumber) {
        this.civicNumber = civicNumber;
    }

    void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    void setCity(String city) {
        this.city = city;
    }

    void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getCivicNumber() {
        return civicNumber;
    }

    public String getPostCode(){
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressAsString() {
        return street + " " + civicNumber + ", " + postCode + " " + city + ", " + country;
    }
}
