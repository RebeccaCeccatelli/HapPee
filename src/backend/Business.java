package backend;

public class Business {
    private BusinessInformation businessInformation;
    private Position position;

    public Business(String email) {
        businessInformation = new BusinessInformation(email);
    }
}
