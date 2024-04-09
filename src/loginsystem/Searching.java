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
public class Searching {
    private ArrayList<String> list = new ArrayList<String>();
    private final String codeName = "dictionary.txt";
    
    
    
    public Searching()throws FileNotFoundException{
        File f = new File(codeName);
        try(Scanner s = new Scanner(f)){
            while(s.hasNextLine()){
                list.add(s.next());
                s.nextLine();
            }
        }catch(IOException e){
        }
    }

    public ArrayList<String> getList() {
        return list;
    }
    
    public String[] listToArray(){
        
        String[] stringArray = list.toArray(new String[0]);
        return stringArray;
    }
    
    public static int binarySearch(String term, String[] list){
        int start = 0;
        int end = list.length;
        
        while(start <= end){
            int mid = (start+end)/2;
            int compare = list[mid].compareTo(term);
            
            if(compare == 0){
                return mid;
            }
            else if(compare < 0){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        return -1;
    }
    
    public static int seqSearch(String term, String[] list){
        int end = list.length;
        for(int i=0; i < end; i ++){
            if(list[i].equals(term)){
                return i;
            }
        }
        
        return -1;
    }
}
