import java.util.List;
import structures.AssociativeArray;
import structures.KeyNotFoundException;

/**
 * Implementation of the AACCategories
 * class for the AAC
 * 
 * @author Jonathan Wang
 *
 */
public class AACCategory {

    AssociativeArray<String, String> contents;
    String name;

    public AACCategory(String name) { // maps locations to words
        this.name = name;
        this.contents = new AssociativeArray<>();
    }

    public void addItem(String imageLoc, String text) {
        this.contents.set(imageLoc, text);
    }

    public String getCategory(String imageLoc) throws KeyNotFoundException {
        return this.contents.name;
    }

    public String getText(String imageLoc) throws KeyNotFoundException {
        return this.contents.get(imageLoc);
    }

    public boolean hasImage(String imageLoc) {
        return this.contents.hasKey(imageLoc);
    }

    public String[] getImages() {
        List<String> contentList = this.contents.keys();
        return contentList.toArray(new String[0]);
    }
}    