import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagReader { //main method class, wraps around the whole program, calls the file reader, processes the photos, creates the slideshow, and calculates the score.
    public static void main(String[] args) {

            System.out.println("Progam has started");

            String[] paths = {
                "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\b_lovely_landscapes.txt",
                "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\c_memorable_moments.txt",
                "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\d_pet_pictures.txt",
                "C:\\Users\\loljo\\Documents\\Programming Challenge\\Data-sets\\e_shiny_selfies.txt"
            };

        
        int finalScore = 0; // this will accumulate the total score across all files, which is printed at the end of the program.

        for (String path : paths) { //for loop that will process the files in each array.

            List<String> rawLines = FileHelper.readFile(new String[]{path});


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
         
    }

    Collections.sort(slideshow);

    

   

    int totalScore = FileHelper.pointTally.totalScore(slideshow);  //uses other classes to calculate the score for the slideshow created from the current file, which is then printed and added to the final score.
    System.out.println("Total Score for " + path + ": " + totalScore);

    finalScore += totalScore; // Accumulate the score for this file into the final score

    printSlideshow(slideshow, path);

      }

    System.out.println("Final Score for all files: " + finalScore);

    }

    public static void printSlideshow(List<Slide> slideshow, String path) {
        String outputPath = path.replace(".txt", "_output.txt"); // Creates an output file name based on the input file name.
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(new FileWriter(outputPath)); //PrintWriter used for writing the slideshow to a file.
            writer.println(slideshow.size()); // First line is the number of slides in the slideshow.
            for (Slide s : slideshow) {
                writer.println(s);
            }
            writer.close();
            System.out.println("Output written to: " + outputPath);
        } catch (IOException e) {
            System.out.println("Error writing output for " + path + ": " + e.getMessage());
        }
    }
    

    public static List<PhotoType> parsePhotos (List<String> rawLines) { //Takes the raw lines and turns into objects that can be properly used.

            List<PhotoType> photos = new ArrayList<>();

            if (rawLines.isEmpty()) return photos;
            

            for (int i = 1; i <rawLines.size(); i++) { //starts at 1 to avoid the first line which is just number of photos, then ignores spaces and splits lines into parts.

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

    static class Slide implements Comparable<Slide> { //can hold either two vertical or one horizontal photos.
        List<Integer> ids = new ArrayList<>(); 
        Set<String> tags = new HashSet<>(); 


        @Override
        public int compareTo(Slide other) { //This is the sorting function, it sorts the slides based on the number of tags they have,.
            return Integer.compare(this.tags.size(), other.tags.size());
        }
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
    
    public String toString() {
        if (ids.size() == 1) {
           return "" + ids.get(0);
        } else {
            return "" + ids.get(0) + " " + ids.get(1);

    }

    }
}

    static class FileHelper {
        public static List<String> readFile(String[] args) { //How it reads the file, takes the path as an argument, and returns a list of strings that represent the lines in the file. It also prints out when a file has been processed, and if there is an error.

            File file = new File(args[0]);
            List<String> lines = new ArrayList<>();
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file)); //BufferedReader used for efficiently reading the file line by line.

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

        public static int scoreSlides(Slide s1, Slide s2) { //Scoring function, defines what gets the score.

            int common = 0;

            for (String tag : s1.tags) { //for loop that counts the number of common tags between two slides, which is the first part of the scoring function.
                if (s2.tags.contains(tag)) {
                    common++;
                }
            }

            int only1 = s1.tags.size() - common; //removes common to get unique tags
            int only2 = s2.tags.size() - common;

            return Math.min(common, Math.min(only1, only2)); //takes minimum per rules of the challenge to get the score of the slides.
        }

        public static int totalScore(List<Slide> slideshow) { //adds up the score of each slide transition to get the total score for the slideshow.
            int total = 0;
            for (int i = 0; i < slideshow.size() - 1; i++) {
                total += scoreSlides(slideshow.get(i), slideshow.get(i + 1));
            }
            return total; //returns the total score for the slideshow.
        }
    }}}




