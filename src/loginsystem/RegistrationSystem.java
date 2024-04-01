/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.util.*;
import java.io.*;
/**
 *
 * @author Daniel Zhong
 */
public class RegistrationSystem {
    private int userNumber;
    private ArrayList<User> users;
    
    public RegistrationSystem(String dataBase) throws FileNotFoundException{
        Scanner sc;
        File file = new File(dataBase);
        
        try{
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                String[] newLine = sc.nextLine().split(",");
                System.out.println(newLine.length);

                
//                User u = new User(newLine);
//                users.add(u);
            }
            
            sc.close();
        }catch(IOException e){
            System.out.println(e + " something is wrong with the textfile");
        }
        
//        userNumber = users.size();
        
        
    }

    public int getUserNumber() {
        return userNumber;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    
    
    
    public void login(String user, String password){
        
    }
    
    
    
}
