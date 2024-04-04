/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;
import java.io.*;
/**
 *
 * @author michael.roy-diclemen
 */
public class LoginSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try{
            RegistrationSystem r = new RegistrationSystem();
            r.loadUser();
            User d = new User("Daniel","Zhong","AKIHI","123456","daniel@gmail.com");
            r.saveUser(d);
            System.out.println(r.login("AKIHI", "123456"));

            r.register("Just", "Min", "Juwon", "justin@gmail.com", "juwon123", "juwon123");
            System.out.println(r.getUsers());
            r.login("Juwon","juwon123");
            System.out.println(r.getUsers());
            System.out.println(r.login("jack","123"));
            System.out.println(r.register("Danny", "Uan", "SlamDunk", "Danny123@hot.mail", "123456", "123456"));

            


        }catch(FileNotFoundException e){
            System.out.println("File not found you noob");
        }
        
        
        
    }
    
}
