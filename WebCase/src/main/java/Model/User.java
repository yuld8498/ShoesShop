package Model;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class User{
    private int userID;
    private String userName;
    private String password;
    private String fullName;
    private int age;
    private String email;
    private String phoneNumber;
    private int address;

    public User() {
    }

    public User(int userID, String fullName, int age, String email, String phoneNumber, int address) {
        this.userID = userID;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(String fullName, int age, String email, String phoneNumber, int address) {
        this.fullName = fullName;
        this.age = age;
        this.email=email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(int userID, String userName, String password, String fullName, int age, String email, String phoneNumber, int address) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.email=email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(String userName, String password, String fullName, int age, String email, String phoneNumber, int address) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
@NotEmpty(message = "User Name is not empty")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NotEmpty(message = "password is not empty")
//    @Pattern(regexp = "(^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]){8,}$",
//            message = "password Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Full Name is not empty")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotNull(message = "Age is not empty")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotEmpty(message = "Phone Number is not empty")
    @Pattern(regexp = "^[0][1-9][0-9]{8}$", message = "Phone number start 0 and have 10 character")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    @NotEmpty(message = "Email is not empty")
    @Email(message = "Email is not valid. \n ex: shoesshop@gmail.com")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
