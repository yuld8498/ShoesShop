package Model;

public class Order {
    private int ID;
    private String userName;
    private int productID;
    private int productQuaility;
    private String email;
    private String phoneNumber;
    private double price;

    private int cityID;
    private int IDStatus;

    public int getIDStatus() {
        return IDStatus;
    }

    public void setIDStatus(int IDStatus) {
        this.IDStatus = IDStatus;
    }

    public Order() {
    }

    public Order(String userName, int productID, int productQuaility, String email, String phoneNumber, double price, int cityID) {
        this.userName = userName;
        this.productID = productID;
        this.productQuaility = productQuaility;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.cityID = cityID;
    }

    public Order(int ID, String userName, int productID, int productQuaility, String email, String phoneNumber, double price, int cityID) {
        this.ID = ID;
        this.userName = userName;
        this.productID = productID;
        this.productQuaility = productQuaility;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.cityID = cityID;
    }

    public Order(String userName, int productID, int productQuaility) {
        this.userName = userName;
        this.productID = productID;
        this.productQuaility = productQuaility;
    }

    public int getCity() {
        return cityID;
    }

    public void setCity(int cityID) {
        this.cityID = cityID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double orther_option) {
        this.price = orther_option;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductQuaility() {
        return productQuaility;
    }

    public void setProductQuaility(int productQuaility) {
        this.productQuaility = productQuaility;
    }

}
