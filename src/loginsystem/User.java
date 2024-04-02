/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.util.*;
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
        this.password = password+salt;
        this.email = email;
    }
    public User(String firstName, String lastName, String userName, String password, String email,String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password+salt;        
        this.email = email;
        this.salt = salt;
    }
    public User(String[] split){
        this.firstName = split[0];
        this.lastName = split[1];
        this.userName = split[2];
        this.salt = generateSalt();
        this.password = split[3]+salt;
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


    
    
    
    @Override
    public String toString(){
        return"{first name: "+ firstName + 
        ", last name: "+ lastName + 
        ", user name: " + userName +
        ", email : " + email +"}";
    }
    

    
}
