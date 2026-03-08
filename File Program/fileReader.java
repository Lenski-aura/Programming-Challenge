import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;




public class fileReader {
    public static List<String> readFile(String[] args) {

        File file = new File(args[0]);
        List<String> lines = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

        reader.close();

        System.out.println(file.getName() + " has been processed");

        } catch (IOException e) {
            System.out.println("File not found");

        }
        
        return lines;
        
        
    }

    public static void main(String[] args) {

        System.out.println("Progam has started");

        String[] paths = {
            "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\a_example.txt",
            "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\b_lovely_landscapes.txt",
            "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\c_memorable_moments.txt",
            "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\d_pet_pictures.txt",
            "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\e_shiny_selfies.txt"
        };

      //Process each file one by one

      for (String path : paths) {
        List<String> rawLines = readFile(new String[]{path});
        List<PhotoType> processsedPhotos = parsePhotos(rawLines);

        System.out.println("Finished " + path + " | Objects: " + processsedPhotos.size());

      }

    } 

    public static List<PhotoType> parsePhotos (List<String> rawLines) {

        List<PhotoType> photos = new ArrayList<>();

        if (rawLines.isEmpty()) {
            return photos;
        }

        for (int i = 1; i <rawLines.size(); i++) {
            String[] parts = rawLines.get(i).split(" ");
            if (parts.length < 2) continue;

            String type = parts[0];
            String [] tagsOnly = Arrays.copyOfRange (parts, 2, parts.length);

            photos.add(new PhotoType(i - 1, type, tagsOnly));
        
        }


        return photos;
    }



   static class PhotoType {
    int id;
    boolean isVertical;
    List<String> tags;

    public PhotoType(int id, String type, String[] tagsArray) {
        this.id = id;
        this.isVertical = type.trim().equalsIgnoreCase("V");
        this.tags = new ArrayList<>(Arrays.asList(tagsArray));
    }
   } 

}

// pre size hashset. dynamic 10 - 60 bits. okay no problem.
// 