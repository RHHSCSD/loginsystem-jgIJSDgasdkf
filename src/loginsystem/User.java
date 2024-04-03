/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.util.*;
import java.security.*;
/**
 *
 * @author Daniel Zhong
 */
public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String salt;
    private boolean isLogin;
    

    public User(String firstName, String lastName, String userName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.salt = generateSalt() ;
        this.password = encryptPassword(password,salt);
        this.email = email;
    }
    public User(String firstName, String lastName, String userName, String password, String email,String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = encryptPassword(password,salt);
        this.email = email;
        this.salt = salt;
    }
    public User(String[] split){
        this.firstName = split[0];
        this.lastName = split[1];
        this.userName = split[2];
        this.salt = generateSalt();
        this.password = encryptPassword(split[3],salt);
        this.email = split[4];
    }
    public static String generateSalt(){
        String s = "";
        int randomLength = new Random().nextInt(6);
        for(int i = 0; i < randomLength;i ++){
            int randomInt = new Random().nextInt(52);
            char start = (randomInt < 26)? 'A':'a';
            s += (char)(start+ randomInt %26);
        }
        
        return s;
    }
    
    public static String encryptPassword(String password, String salt){
        String encryptedPassword="";
        try{
            //java helper class to perform encryption
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //give the helper function the password
            md.update(password.getBytes());
            //perform the encryption
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; ++i) {
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) |
                0x100).substring(1,3));
            }
        }catch(NoSuchAlgorithmException e){
            System.out.println("No such algorithm, " + e);
        }
       return encryptedPassword+salt;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    
    

    
    
    
    @Override
    public String toString(){
        return"{first name: "+ firstName + 
        ", last name: "+ lastName + 
        ", user name: " + userName +
        ", email : " + email +"}";
    }
    
    
    public static void main(String[]args){
    }

    
}
