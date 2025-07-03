package com.restaurant.rms.utilities;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Used to create the receipt
 *
 */
public class FileHandler {

    public static boolean openFile(File file){
        if (file == null || !file.exists()){
            System.out.println("file not found");
        }
        else {
           try {
               BufferedReader reader = new BufferedReader(new FileReader(file));
               System.out.println("Opened and read file!");
               return true;
           } catch (Exception e) {
               System.out.println("Error opening file");
           }
        }
        return false;

    }

    public static boolean writeToFile(String message, File file){
        if (openFile(file)){
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(message);
                System.out.println(" Successfully wrote to the file!");
                return true;
            } catch (IOException e) {
                System.out.println("Error writing to the file!");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean closeFile(File file){
        return false;
    }
}
