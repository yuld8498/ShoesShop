package Model;

public class Account {
    private String userName;
    private String password;
    private Account(){};
    private static Account instance;
    public static Account getAccount(){
        if (instance == null) {
            instance = new Account();
            return instance;
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
