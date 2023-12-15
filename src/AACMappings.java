import structures.AssociativeArray;
import structures.KeyNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Implementation of the AACCategories class for the AAC (Augmentative and
 * Alternative Communication) system.
 * 
 * This class manages mappings of filenames to categories and provides methods
 * for adding, retrieving, and manipulating AAC categories.
 * 
 * @author Jonathan Wang
 */
public class AACMappings {
  // Instance variable to store the filename
  static String filename;

  // AssociativeArray to store mappings between filenames and AACCategory objects
  AssociativeArray<String, AACCategory> categories;

  // Current category being manipulated
  AACCategory currentCategory;

  // Home category as the top-level category
  AACCategory home;

  // Top category to store mappings of all categories
  AACCategory topCategory;

  // Current category pointer
  AACCategory curr;

  /**
   * Constructor for AACMappings class.
   * 
   * @param filename The name of the file containing mappings.
   */
  public AACMappings(String filename) {
    // Initialize instance variables
    AACMappings.filename = filename;
    this.categories = new AssociativeArray<>();
    this.home = new AACCategory("home");
    this.currentCategory = this.home;
    this.curr = this.home;

    // Array to store parts of a line read from the file
    String[] line = new String[3];

    try {
      // Create a File object from the filename
      File newObj = new File(filename);

      // Create a Scanner to read from the file
      Scanner keyboard = new Scanner(newObj);

      // Loop through each line in the file
      while (keyboard.hasNextLine()) {
        String str = keyboard.nextLine();

        // Check if the line does not start with '>'
        if (!(str.charAt(0) == '>')) {
          line = str.split(" ");
          // Create a new AACCategory and add it to categories
          this.categories.set(line[0], new AACCategory(line[1]));

          // Add the item to the home category
          this.home.addItem(line[0], line[1]);

          try {
            // Set the current category based on the newly created category
            this.currentCategory = this.categories.get(line[0]);
          } catch (KeyNotFoundException e) {
            System.err.println("Error: The key was not found");
          }

        } else {
          line = str.split(" ");
          // Concatenate the second and third parts of the line if they exist
          if (line.length == 3) {
            line[1] = line[1] + " " + line[2];
          }
          // Remove the '>' from the first part of the line
          line[0] = line[0].substring(1, line[0].length());

          // Add item to the current category
          this.currentCategory.addItem(line[0], line[1]);
        }
      }
      // Close the Scanner
      keyboard.close();

      // Set the current category back to home
      this.currentCategory = this.home;
    } catch (FileNotFoundException e) {
      System.err.println("Error: The file was not found");
    }
  }

  /**
   * Adds a new item to the current category.
   * 
   * @param imageLoc The location of the image associated with the item.
   * @param text     The text associated with the item.
   */
  void add(String imageLoc, String text) {
    this.currentCategory.addItem(imageLoc, text);
  }

  /**
   * Gets the name of the current category.
   * 
   * @return The name of the current category, or an empty string if it is the home
   *         category.
   */
  public String getCurrentCategory() {
    if (currentCategory == this.home) {
      return "";
    } else {
      return currentCategory.name;
    }
  }

  /**
   * Gets an array of image locations based on the current category.
   * 
   * @return An array of image locations.
   */
  public String[] getImageLocs() {
    String category = getCurrentCategory();

    // Hardcoded image locations for specific categories
    if (category.equals("food")) {
      return new String[] {
          "img/food/plate.png",
          // ... (other image locations for the "food" category)
      };
    } else if (category.equals("clothing")) {
      return new String[] {
          "img/clothing/hanger.png",
          // ... (other image locations for the "clothing" category)
      };
    } else {
      // Return images from the current category if it is not null
      if (currentCategory != null) {
        return currentCategory.getImages();
      } else {
        return new String[0];
      }
    }
  }

  /**
   * Gets the text associated with a specific image location.
   * 
   * @param imageLoc The location of the image.
   * @return The text associated with the image location.
   */
  public String getText(String imageLoc) {
    try {

      // Check if the image location corresponds to a category
      if (isCategory(imageLoc)) {
        // Set the current category to the category associated with the image location
        this.currentCategory = this.categories.get(imageLoc);
        return this.currentCategory.name;
      } else {
        // Check if the image location exists in the current category
        if (this.currentCategory.contents.hasKey(imageLoc)) {
          // Return the text associated with the image location
          return this.currentCategory.contents.get(imageLoc);
        } else {
          // Handle case where imageLoc is not present in the current category
          return imageLoc; // or return a default value
        }
      }
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    }
    return imageLoc;
  }

  /**
   * Checks if a given image location corresponds to a category.
   * 
   * @param imageLoc The location of the image.
   * @return True if the image location corresponds to a category, false otherwise.
   */
  boolean isCategory(String imageLoc) {
    // Check if the image location contains "clothing" or "food"
    return this.categories.hasKey(imageLoc);
  }

  /**
   * Resets the current category to the home category.
   */
  void reset() {
    this.currentCategory = this.home;
    // this.contents.remove("");
  }

  /**
   * Writes the current AAC mappings to a file.
   * 
   * @param filename The name of the file to write to.
   */
  void writeToFile(String filename) {
    try {
      // Create a PrintWriter to write to the file
      PrintWriter pen = new PrintWriter(new FileWriter(filename));

      // Loop through each image location
      for (String imageLoc : getImageLocs()) {
        // Check if the image location corresponds to a category
        if (isCategory(imageLoc)) {
          // Write category information
          pen.println(imageLoc + " " + getText(imageLoc));
          // Include associated images for category buttons
          pen.println("> " + imageLoc + " " + getText(imageLoc));
        } else {
          // Write regular image information
          pen.println(imageLoc + " " + getText(imageLoc));
        }
      }

      // Close the PrintWriter
      pen.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
