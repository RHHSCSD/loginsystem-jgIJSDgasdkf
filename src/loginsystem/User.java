/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.util.*;
import java.security.*;

/**
 * @author Daniel Zhong
 * The User class represents a user in a login system.
 */
public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String salt;
    private boolean isLogin;

    /**
     * Constructs a new User object with the provided information.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param userName  the username of the user
     * @param password  the password of the user
     * @param email     the email address of the user
     */
    public User(String firstName, String lastName, String userName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.salt = generateSalt();
        this.password = encryptPassword(password, salt);
        this.email = email;
    }

    /**
     * Constructs a new User object with the provided information including the salt.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param userName  the username of the user
     * @param password  the password of the user
     * @param email     the email address of the user
     * @param salt      the salt used for password encryption
     */
    public User(String firstName, String lastName, String userName, String password, String email, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.salt = salt;
    }

    /**
     * Constructs a new User object by parsing an array of strings containing user information.
     *
     * @param split an array of strings containing user information: [firstName, lastName, userName, password, email]
     */
    public User(String[] split) {
        this.firstName = split[0];
        this.lastName = split[1];
        this.userName = split[2];
        this.salt = generateSalt();
        this.password = encryptPassword(split[3], salt);
        this.email = split[4];
    }

    /**
     * Generates a random salt.
     *
     * @return the generated salt
     */
    public static String generateSalt() {
        String s = "";
        int randomLength = new Random().nextInt(6);
        for (int i = 0; i < randomLength; i++) {
            int randomInt = new Random().nextInt(52);
            char start = (randomInt < 26) ? 'A' : 'a';
            s += (char) (start + randomInt % 26);
        }
        return s;
    }

    /**
     * Encrypts the password using SHA-256 algorithm with the given salt.
     *
     * @param password the password to be encrypted
     * @param salt     the salt used for encryption
     * @return the encrypted password
     */
    public static String encryptPassword(String password, String salt) {
        String encryptedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; ++i) {
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm, " + e);
        }
        return encryptedPassword + salt;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the salt used for password encryption.
     *
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Returns whether the user is logged in.
     *
     * @return true if the user is logged in, otherwise false
     */
    public boolean isIsLogin() {
        return isLogin;
    }

    /**
     * Sets the login status of the user.
     *
     * @param isLogin the login status
     */
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return a string representation including user information and login status
     */
    @Override
    public String toString() {
        String loginStatus = isLogin ? "logged in" : "not logged in";
        return "{first name: " + firstName +
                ", last name: " + lastName +
                ", user name: " + userName +
                ", email : " + email +
                ", status: " + loginStatus + "}";
    }
}
