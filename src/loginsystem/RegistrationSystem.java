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
    private ArrayList<User> users = new ArrayList<User>();
    private final String dataBase = "src/loginsystem/user.txt";
    public RegistrationSystem() throws FileNotFoundException{
        Scanner sc;
        File file = new File(dataBase);
        
        try{
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                String[] newLine = sc.nextLine().split(",");                
                User u = new User(newLine[0],newLine[1],newLine[2],newLine[3],newLine[4]);
                users.add(u);
            }
            
            sc.close();
        }catch(IOException e){
            System.out.println(e + " something is wrong with the textfile");
        }
        
       userNumber = users.size();
        
        
    }
    
    public void login(String user, String password){
        
    }

    public void saveUser(User user)throws FileNotFoundException{
        File file = new File(dataBase);
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(file,true));
            writer.println(user.getFirstName()+","+user.getLastName()+","+user.getUserName()+","+user.getPassword()+","+user.getEmail());
            users.add(user);
            writer.close();
        }catch(IOException e){
            System.out.println(e + " something is wrong with the textfile");
        }

    }

    public boolean isUniqueName(String firstName, String lastName){
        boolean isUnique = true;
        for(User user:users){
            if(user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)){
                isUnique = false;
            }
        }
        return isUnique;
    }
    
    public int getUserNumber(){
        return userNumber;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    
    

    
    
}
