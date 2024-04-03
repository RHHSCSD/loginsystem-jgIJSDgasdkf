/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.util.*;
import java.io.*;
import java.security.*;
/**
 *
 * @author Daniel Zhong
 */
public class RegistrationSystem {
    private int userNumber;
    private ArrayList<User> users = new ArrayList<>();
    private final String DataBase = "user.txt";
    private final String Delimiter = "\0";
    
    
    public void loadUser() throws FileNotFoundException{
        Scanner sc;
        File file = new File(DataBase);
        
        try{
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                String[] newLine = sc.nextLine().split(Delimiter);                
                if(isUniqueName(newLine[0],newLine[1])){
                    User u = new User(newLine[0],newLine[1],newLine[2],newLine[3],newLine[4],newLine[5]);
                    users.add(u);
                }

            }
            
            sc.close();
        }catch(IOException e){
            System.out.println(e + " something is wrong with the textfile");
        }
        
       userNumber = users.size();
        
        
    }

    
    public int login(String username, String input){
        if(isUser(username)){
            User user = fetchUserInfo(username);
            if(correctPassword(user.getPassword(),user.getSalt(),input)){
                user.setIsLogin(true);
                return 1;
            }else{
                return 0;
            }
            
        }else{
            return -1;
        }      
    }
    
    public boolean correctPassword(String password, String salt, String input){
        String encryptedPassword="";
        try{
            //java helper class to perform encryption
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //give the helper function the password
            md.update(input.getBytes());
            //perform the encryption
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; ++i) {
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) |
                0x100).substring(1,3));
            }
        }catch(NoSuchAlgorithmException e){
            System.out.println("No such algorithm, " + e);
        }
        return (encryptedPassword+salt).equals(password);
    }
    
    public void saveUser(User user)throws FileNotFoundException{
        File file = new File(DataBase);
        PrintWriter writer;
        if(isUniqueName(user.getFirstName(),user.getLastName())){
            try{
                writer = new PrintWriter(new FileWriter(file,true));
                writer.println(user.getFirstName()+Delimiter+user.getLastName()+Delimiter+user.getUserName()+Delimiter+user.getPassword()+Delimiter+user.getEmail()+Delimiter+user.getSalt());
                writer.close();
            }catch(IOException e){
                System.out.println(e + " something is wrong with the textfile");
            }

            this.loadUser();
        }
    }
        
    public boolean isUser(String username){
        for(User user: users){
            if(user.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }
    
    public User fetchUserInfo(String username){
        for(User user: users){
            if(user.getUserName().equals(username)){
                return user;
            }
        }
        return null;
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
