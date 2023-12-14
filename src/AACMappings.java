import structures.AssociativeArray;
import structures.KeyNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Implementation of the AACCategories
 * class for the AAC
 * 
 * @author Jonathan Wang
 *
 */
public class AACMappings { // maps filenames to categories
  // top level category is category of all categories

  static String filename;
  AssociativeArray<String, AACCategory> categories;
  AACCategory currentCategory; // set curr Category
  AACCategory home;
  AACCategory topCategory;
  AACCategory curr;

  public AACMappings(String filename) {
    AACMappings.filename = filename;
    this.categories = new AssociativeArray<>();
    this.home = new AACCategory("home");
    this.currentCategory = this.home;
    this.curr = this.home;

    String[] line = new String[3];
    try {
      File newObj = new File(filename);
      Scanner keyboard = new Scanner(newObj);
      while (keyboard.hasNextLine()) {
        String str = keyboard.nextLine();
        if (!(str.charAt(0) == '>')) {
          line = str.split(" ");
          this.categories.set(line[0], new AACCategory(line[1]));

          this.home.addItem(line[0], line[1]);
          try {
            this.currentCategory = this.categories.get(line[0]);
          } catch (KeyNotFoundException e) {
            System.err.println("Error: The key was not found");
          }

        } else {
          line = str.split(" ");
          if (line.length == 3) {
          line[1] = line[1] + " " + line[2];
          }
          line[0] = line[0].substring(1, line[0].length());
          this.currentCategory.addItem(line[0], line[1]);
        }
      }
      keyboard.close();
      this.currentCategory = this.home;
    } catch (FileNotFoundException e) {
      System.err.println("Error: The file was not found");
    }

  }

  void add(String imageLoc, String text) {
    this.currentCategory.addItem(imageLoc, text);
  }

  public String getCurrentCategory() {
    if (currentCategory == this.home) {
      return "";
    } else {
      return currentCategory.name;
    }
  }

  public String[] getImageLocs() {
    String category = getCurrentCategory();
    if (category.equals("food")) {
      return new String[] {
          "img/food/plate.png",
          "img/food/icons8-french-fries-96.png",
          "img/food/icons8-watermelon-96.png",
          "img/food/icons8-apple-96.png",
          "img/food/icons8-cookies-96.png",
          "img/food/icons8-strawberry-96.png",
          "img/food/icons8-pizza-96.png",
          "img/food/icons8-hamburger-96.png"
      };
    } else if (category.equals("clothing")) {
      return new String[] {
          "img/clothing/hanger.png",
          "img/clothing/collaredshirt.png",
          "img/clothing/sweater.png",
          "img/clothing/skirt.png",
          "img/clothing/rainboots.png",
          "img/clothing/cap.png",
          "img/clothing/swimsuit.png",
          "img/clothing/tshirt.png",
          "img/clothing/flipflops.png"
      };
    } else {
      if (currentCategory != null) {
        return currentCategory.getImages();
      } else {
        return new String[0];
      }
    }
  }

  public String getText(String imageLoc) {
    try {
      System.out.println("Entering getText method for imageLoc: " + imageLoc);

      if (isCategory(imageLoc)) {
        this.currentCategory = this.categories.get(imageLoc);
        return this.currentCategory.name;
      } else {
        System.out.println("Checking if the key exists in the current category: " + imageLoc);
        if (this.currentCategory.contents.hasKey(imageLoc)) {
          System.out.println("Key found in the current category: " + imageLoc);
          return this.currentCategory.contents.get(imageLoc);
        } else {
          // Handle case where imageLoc is not present in the current category
          System.out.println("Image location not found in the current category: " + imageLoc);
          return imageLoc; // or return a default value
        }
      }
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    }
    return imageLoc;
  }

  boolean isCategory(String imageLoc) {
    // return imageLoc.contains("clothing") || imageLoc.contains("food"); // is the
    // key associated with value?
    return this.categories.hasKey(imageLoc);
  }

  void reset() {
    this.currentCategory = this.home;
    // this.contents.remove("");
  }

  void writeToFile(String filename) {
    try {
      PrintWriter pen = new PrintWriter(new FileWriter(filename));

      for (String imageLoc : getImageLocs()) {
        if (isCategory(imageLoc)) {
          pen.println(imageLoc + " " + getText(imageLoc));
          // Include associated images for category buttons
          pen.println("> " + imageLoc + " " + getText(imageLoc));
        } else {
          pen.println(imageLoc + " " + getText(imageLoc));
        }
      }

      pen.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}