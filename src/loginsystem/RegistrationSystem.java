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
 * The RegistrationSystem class manages user registration, login, and related operations.
 * It interacts with user data stored in a file and provides methods for user registration,
 * login, and password validation.
 */
public class RegistrationSystem {
    private int userNumber; // Number of users registered
    private ArrayList<User> users = new ArrayList<>(); // List of registered users
    private final String DataBase = "user.txt"; // File path for user data
    private final String PasswordVault = "dictbadpass.txt"; // File path for bad passwords list
    private final String Delimiter = "\0"; // Delimiter for separating user data in files
    
    /**
     * Loads user data from the file into the system.
     *
     * @throws FileNotFoundException if the user data file is not found or if an I/O error occurs
     */
    public void loadUser() throws FileNotFoundException {
        File file = new File(DataBase);
        
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] newLine = sc.nextLine().split(Delimiter);
                if (isUniqueName(newLine[0], newLine[1])) {
                    User u = new User(newLine[0], newLine[1], newLine[2], newLine[3], newLine[4], newLine[5]);
                    users.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println(e + " something is wrong with the textfile");
        }
        
        userNumber = users.size();
    }

    /**
     * Authenticates a user login attempt.
     *
     * @param username the username provided by the user
     * @param input the password provided by the user
     * @return 0 if login is successful, -1 if password is incorrect, -2 if username is not found
     */
    public int login(String username, String input) {
        if (isUser(username)) {
            User user = fetchUserInfo(username);
            if (correctPassword(user.getPassword(), user.getSalt(), input)) {
                user.setIsLogin(true);
                return 0;
            } else {
                return -1;
            }
        } else {
            return -2;
        }      
    }

    /**
     * Registers a new user.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param userName the desired username
     * @param email the email address of the user
     * @param password the desired password
     * @param checkPassword the password confirmation
     * @return 0 if registration is successful, -1 if file not found, -2 if passwords don't match,
     *         -3 if password is in the bad passwords list, -4 if username is not unique, -5 if name is not unique
     */
    public int register(String firstName, String lastName, String userName, String email,
                        String password, String checkPassword) {
        if (isUniqueName(firstName, lastName)) {
            if (isUnqiueName(userName)) {
                if (!isBadPassword(password)) {
                    if (password.equals(checkPassword)) {
                        try {
                            saveUser(new User(firstName, lastName, userName, checkPassword, email));
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found");
                            return -1;
                        }
                        return 0;
                    } else {
                        return -2;
                    }
                } else {
                    return -3;
                }
            } else {
                return -4;
            }
        } else {
            return -5;
        }
    }
    
    /**
     * Checks if a password is in the list of bad passwords.
     *
     * @param password the password to check
     * @return true if the password is bad, false otherwise
     */
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
    
    /**
     * Validates if the provided password matches the stored password for a user.
     *
     * @param password the stored password
     * @param salt the salt used for password encryption
     * @param input the password provided for login
     * @return true if the password matches, false otherwise
     */
    public boolean correctPassword(String password, String salt, String input) {
        String encryptedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; ++i) {
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm, " + e);
        }
        encryptedPassword += salt; 
        return encryptedPassword.equals(password);
    }
    
    /**
     * Saves a new user to the user data file.
     *
     * @param user the user to be saved
     * @throws FileNotFoundException if the user data file is not found or if an I/O error occurs
     */
    public void saveUser(User user) throws FileNotFoundException {
        File file = new File(DataBase);
        if (isUniqueName(user.getFirstName(), user.getLastName())) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.println(user.getFirstName() + Delimiter + user.getLastName() + Delimiter +
                        user.getUserName() + Delimiter + user.getPassword() + Delimiter +
                        user.getEmail() + Delimiter + user.getSalt());
            } catch (IOException e) {
                System.out.println(e + " something is wrong with the textfile");
            }

            this.loadUser();
        }
    }
        
/**
 * Checks if a user with the given username exists.
 *
 * @param username the username to check
 * @return true if a user with the given username exists, false otherwise
 */
public boolean isUser(String username) {
    for (User user : users) {
        if (user.getUserName().equals(username)) {
            return true;
        }
    }
    return false;
}

/**
 * Fetches user information based on the given username.
 *
 * @param username the username of the user to fetch information for
 * @return the User object corresponding to the given username, or null if not found
 */
public User fetchUserInfo(String username) {
    for (User user : users) {
        if (user.getUserName().equals(username)) {
            return user;
        }
    }
    return null;
}

/**
 * Checks if a combination of first name and last name is unique.
 *
 * @param firstName the first name to check
 * @param lastName  the last name to check
 * @return true if the combination of first name and last name is unique, false otherwise
 */
public boolean isUniqueName(String firstName, String lastName) {
    for (User user : users) {
        if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
            return false;
        }
    }
    return true;
}

/**
 * Checks if a username is unique.
 *
 * @param userName the username to check
 * @return true if the username is unique, false otherwise
 */
public boolean isUnqiueName(String userName) {
    for (User user : users) {
        if (user.getUserName().equals(userName)) {
            return false;
        }
    }
    return true;
}

/**
 * Retrieves the total number of registered users.
 *
 * @return the total number of registered users
 */
public int getUserNumber() {
    return userNumber;
}

/**
 * Retrieves the list of registered users.
 *
 * @return the list of registered users
 */
public ArrayList<User> getUsers() {
    return users;
}

    

    
    
    

    
    
}
