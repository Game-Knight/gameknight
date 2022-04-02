package Entities;

import java.io.Serializable;
import java.util.Map;

import Utils.EntityUtils;

public class User implements Serializable {

    private String phoneNumber; // though not very secure, this can act as the username and user id
    private String firstName;
    private String lastName;
    private String passwordSalt;
    private String passwordHash;

    public User(String phoneNumber, String firstName, String lastName, String password) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
//        setPassword(password);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    private void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    private void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPassword(String password) {
        Map<String, String> passwordPair = EntityUtils.hashPassword(password);
        assert(passwordPair.entrySet().size() == 1);
        for (Map.Entry<String, String> entry : passwordPair.entrySet()) {
            setPasswordSalt(entry.getKey());
            setPasswordHash(entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "First Name: " + this.firstName + "\nLast Name: " + this.lastName +
                "\nPhone Number (used as id): " + this.phoneNumber + "\n";
    }
}
