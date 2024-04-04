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
    private final String PasswordVault = "dictbadpass.txt";

    private final String Delimiter = "\0";
    
    
    public void loadUser() throws FileNotFoundException{
        File file = new File(DataBase);
        
        try(Scanner sc = new Scanner(file)){
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
                return 0;
            }else{
                return -1;
            }
            
        }else{
            return -2;
        }      
    }

    public int register(String firstName, String lastName, String userName, String email, String password, String checkPassword){
        if(isUniqueName(firstName, lastName)){
            if(isUnqiueName(userName)){
                if(!isBadPassword(password)){
                    if(password.equals(checkPassword)){
                        try{
                            saveUser(new User(firstName, lastName, userName, checkPassword, email));
                        }catch(FileNotFoundException e){
                            System.out.println("File not found");
                            return -1;
                        }
                        return 0;
                    }else{
                        return -2;
                    }
                }else{
                    return -3;
                }
            }else{
                return -4;
            }
        }else{
            return -5;
        }
    }
    public boolean isBadPassword(String password) {
        try (Scanner scanner = new Scanner(new File(PasswordVault))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(password)) {
                    return true; 
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return false; 
    }
    public boolean correctPassword(String password, String salt, String input){
        String encryptedPassword="";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; ++i) {
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) |
                0x100).substring(1,3));
            }
        }catch(NoSuchAlgorithmException e){
            System.out.println("No such algorithm, " + e);
        }
        encryptedPassword += salt; 
        return (encryptedPassword).equals(password);
    }
    
    public void saveUser(User user)throws FileNotFoundException{
        File file = new File(DataBase);
        if(isUniqueName(user.getFirstName(),user.getLastName())){
            try(PrintWriter writer = new PrintWriter(new FileWriter(file,true));){
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
        for(User user:users){
            if(user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)){
                return false;
            }
        }
        return true;
    }

    public boolean isUnqiueName(String userName){
        for(User user: users){
            if(user.getUserName().equals(userName)){
                return false;
            }
        }

        return true;
    }
    
    public int getUserNumber(){
        return userNumber;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    

    
    
    

    
    
}
