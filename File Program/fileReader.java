import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class fileReader {
    public static void readFile(String[] args) {
        // 1.) Describe where the file is.
        // File is the imported class and file is a variable name.
        File file = new File(args[0]);
        
        //attaching a scanner to read the file
        try {
            
         Scanner reader = new Scanner(file);

        //Says whilst the file has another line keep looping this script.

         while (reader.hasNextLine()) {

            //reads it as a string

             String line = reader.nextLine();

                //print the line that was read

                System.out.println("Read line: " + line);
         }

         // Used if file is not found properly.

         reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static void main(String[] args) {
        readFile(new String[]{"C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\b_lovely_landscapes.txt"});
        readFile(new String[]{"C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\c_memorable_moments.txt"});
        readFile(new String[]{"C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\d_pet_pictures.txt"});
        readFile(new String[]{"C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\e_shiny_selfies.txt"});
    }

    public class scoreTally {
        
}}


    