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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCivicNumber(String civicNumber) {
        this.civicNumber = civicNumber;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    //valutare se spostare questo come metodo di Map, invece che qui
    public String getAddressAsString() {
        return street + " " + civicNumber + ", " + postCode + " " + city + ", " + country;
    }
}
