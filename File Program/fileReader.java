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

        reader.close(); //wrong area move

        System.out.println(file.getName() + " has been processed");

        } catch (IOException e) {
            System.out.println("File not found");

        }
        
         
        return lines;
        
        
    }

    public static void main(String[] args) {

        System.out.println("Progam has started");

        String[] paths = {
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

      List<Slide> slideshow = new ArrayList<>();
      List<PhotoType> verticals = new ArrayList<>();

        for (PhotoType p : processsedPhotos) {
        if (!p.isVertical) {
        slideshow.add(new Slide(p)); // Horizontal photos become slides instantly
        } else {
        verticals.add(p); // Save verticals to pair them up later
        if (verticals.size() == 2) {
            slideshow.add(new Slide(verticals.get(0), verticals.get(1)));
            verticals.clear(); // Empty the temp list for the next pair
        }
    }
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
    Set<String> tags; 

    public PhotoType(int id, String type, String[] tagsArray) {
        this.id = id;
        this.isVertical = type.trim().equalsIgnoreCase("V");
       
        this.tags = new HashSet<>(Arrays.asList(tagsArray));
    }
}

static class Slide {
    List<Integer> ids = new ArrayList<>(); //Stores 1 or 2 Photo IDs
    Set<String> tags = new HashSet<>(); //The combined "Toolbox" of tags

    //Constructor for a single Horizontal photo

    public Slide (PhotoType photo) {
        this.ids.add(photo.id);
        this.tags.addAll(photo.tags); //Copy all tags into the slide
    }

    //Constructor for two Vertical photos
    public Slide (PhotoType p1, PhotoType p2) {
        this.ids.add(p1.id);
        this.ids.add(p2.id);
    

    //This is the merge!
    //addAll takes everything from the photo's set and puts it in the slide's set

    this.tags.addAll(p1.tags);
    this.tags.addAll(p2.tags);

    }

    }

}

// pre size hashset. dynamic 10 - 60 bits. okay no problem.
// 