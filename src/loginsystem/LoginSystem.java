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
            RegistrationSystem r = new RegistrationSystem("src/loginsystem/user.txt");
//            System.out.println(r.getUsers());

        }catch(FileNotFoundException e){
            System.out.println("File not found you noob");
        }
        
        
    }
    
}
