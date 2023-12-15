import java.util.List;
import structures.AssociativeArray;
import structures.KeyNotFoundException;

/**
 * Implementation of the AACCategory class for the AAC (Augmentative and
 * Alternative Communication) system.
 * 
 * This class represents a category in the AAC system, mapping image locations
 * to associated words or text.
 * 
 * @author Jonathan Wang
 */
public class AACCategory {

    // AssociativeArray to store mappings between image locations and associated text
    AssociativeArray<String, String> contents;

    // Name of the category
    String name;

    /**
     * Constructor for AACCategory class.
     * 
     * @param name The name of the category.
     */
    public AACCategory(String name) {
        this.name = name;
        this.contents = new AssociativeArray<>();
    }

    /**
     * Adds a new item to the category.
     * 
     * @param imageLoc The location of the image associated with the item.
     * @param text     The text associated with the item.
     */
    public void addItem(String imageLoc, String text) {
        this.contents.set(imageLoc, text);
    }

    /**
     * Gets the name of the category associated with a specific image location.
     * 
     * @param imageLoc The location of the image.
     * @return The name of the category.
     * @throws KeyNotFoundException If the image location is not found in the category.
     */
    public String getCategory(String imageLoc) throws KeyNotFoundException {
        return this.contents.name;
    }

    /**
     * Gets the text associated with a specific image location.
     * 
     * @param imageLoc The location of the image.
     * @return The text associated with the image location.
     * @throws KeyNotFoundException If the image location is not found in the category.
     */
    public String getText(String imageLoc) throws KeyNotFoundException {
        return this.contents.get(imageLoc);
    }

    /**
     * Checks if the category contains a specific image location.
     * 
     * @param imageLoc The location of the image.
     * @return True if the category contains the image location, false otherwise.
     */
    public boolean hasImage(String imageLoc) {
        return this.contents.hasKey(imageLoc);
    }

    /**
     * Gets an array of all image locations in the category.
     * 
     * @return An array of image locations.
     */
    public String[] getImages() {
        // Convert the list of image locations to an array
        List<String> contentList = this.contents.keys();
        return contentList.toArray(new String[0]);
    }
}
