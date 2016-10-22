package ba.biggy.androidbis.POJO;



public class User {

    private String username;
    private String password;
    private String protectionLevel1;
    private String protectionLevel2;
    private String priceHourWork;
    private String priceHourTravel;
    private String authorizedService;
    private String country;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtectionLevel1() {
        return protectionLevel1;
    }

    public void setProtectionLevel1(String protectionLevel1) {
        this.protectionLevel1 = protectionLevel1;
    }

    public String getProtectionLevel2() {
        return protectionLevel2;
    }

    public void setProtectionLevel2(String protectionLevel2) {
        this.protectionLevel2 = protectionLevel2;
    }

    public String getPriceHourWork() {
        return priceHourWork;
    }

    public void setPriceHourWork(String priceHourWork) {
        this.priceHourWork = priceHourWork;
    }

    public String getPriceHourTravel() {
        return priceHourTravel;
    }

    public void setPriceHourTravel(String priceHourTravel) {
        this.priceHourTravel = priceHourTravel;
    }

    public String getAuthorizedService() {
        return authorizedService;
    }

    public void setAuthorizedService(String authorizedService) {
        this.authorizedService = authorizedService;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
