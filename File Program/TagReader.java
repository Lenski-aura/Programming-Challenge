import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<String> rawLines = TagReader.readFile(new String[]{path});


        List<PhotoType> processsedPhotos = parsePhotos(rawLines);
  

        List<Slide> slideshow = new ArrayList<>();
        List<PhotoType> verticals = new ArrayList<>();

        for (PhotoType p : processsedPhotos) {
            if (!p.isVertical) {
                slideshow.add(new Slide(p));  // Horizontal photos become slides instantly
            } else {
            verticals.add(p); // Save verticals to pair them up later
            if (verticals.size() == 2) {
                slideshow.add(new Slide(verticals.get(0), verticals.get(1)));
             verticals.clear(); // Empty the temp list for the next pair
        } 
    }

    
    
    //Note: Potentially look into a smarter algorithm for pairing verticals for better score.
    //Note: Call function into the main and print it out.
}
int totalScore = TagReader.pointTally.totalScore(slideshow);
    System.out.println("Total Score for " + path + ": " + totalScore);

      }
    }

 static java.util.Map<String, Integer> tagMap = new java.util.HashMap<>();
 static int tagCounter = 0;

 public static int getTagId(String tag) {
    return tagMap.computeIfAbsent(tag, k -> tagCounter++);
 }

 public static List<PhotoType> parsePhotos (List<String> rawLines) {

        List<PhotoType> photos = new ArrayList<>();

        if (rawLines.isEmpty()) return photos;
        

        for (int i = 1; i <rawLines.size(); i++) {
            String[] parts = rawLines.get(i).split(" ");
            if (parts.length < 2) continue;

           String type = parts[0]; 

           PhotoType p = new PhotoType(i - 1, type);
        
            for (int j = 2; j < parts.length; j++) {
                p.tags.add(parts[j]);
            }

            photos.add(p);
        }


        return photos;
    }


 static class PhotoType { //This represents a single photo, whether it's horizontal or vertical, and its tags.
    int id;
    boolean isVertical;
    Set<String> tags = new HashSet<>();

    public PhotoType(int id, String type) {
        this.id = id;
        this.isVertical = type.trim().equalsIgnoreCase("V");
    }
}

static class Slide { //can hold either two vertical or one horizontal photos.
    List<Integer> ids = new ArrayList<>(); 
    Set<String> tags = new HashSet<>(); 

    //Constructor for a single Horizontal photo

    public Slide (PhotoType photo) {
        this.ids.add(photo.id);
        this.tags.addAll(photo.tags); //Combines the tags from both vertical photos into a singular set. Hashset deals with duplicates.
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


public class TagReader {
    public static List<String> readFile(String[] args) {

        File file = new File(args[0]);
        List<String> lines = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

         

        System.out.println(file.getName() + " has been processed");

        reader.close();

        } 
        

        catch (IOException e) {
            System.out.println("Error");

        }
        
         
        return lines;
        
    }


static class pointTally {

    public static int scoreSlides(Slide s1, Slide s2) {

        int common = 0;

        for (String tag : s1.tags) {
            if (s2.tags.contains(tag)) {
                common++;
            }
        }

        int only1 = s1.tags.size() - common;
        int only2 = s2.tags.size() - common;

        return Math.min(common, Math.min(only1, only2));
    }

    public static int totalScore(List<Slide> slideshow) {
        int total = 0;
        for (int i = 0; i < slideshow.size() - 1; i++) {
            total += scoreSlides(slideshow.get(i), slideshow.get(i + 1));
        }
        return total;
    }
}}



