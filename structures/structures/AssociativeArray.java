package structures;

import static java.lang.reflect.Array.newInstance;
import java.util.ArrayList;
import java.util.List;

/**
 * A basic implementation of Associative Arrays with keys of type K and values
 * of type V.
 * Associative Arrays store key/value pairs and permit you to look up values by
 * key.
 *
 * @author Jonathan Wang
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  /**
   * A list to store image locations associated with keys.
   */
  private List<String> imageLocs;

  public String name;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(), DEFAULT_CAPACITY);
    this.size = 0;
    this.imageLocs = new ArrayList<>();
  }

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clonedArray = new AssociativeArray<>();

    for (int i = 0; i < this.size; i++) {
      clonedArray.set(this.pairs[i].key, this.pairs[i].value);
    }

    return clonedArray;
  }

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String contents = "{ ";

    for (int i = 0; i < size; i++) {
      if (i > 0) {
        contents += ", ";
      }

      contents += this.pairs[i].key.toString() + ": " + this.pairs[i].value.toString();
    }

    contents += " }";
    return contents;
  }

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to get(key) will
   * return value.
   */
  public void set(K key, V value) {
    for (int i = 0; i < size; i++) {
      if (this.pairs[i].key.equals(key)) {
        this.pairs[i].value = value;
        return;
      }
    }

    // If key doesn't exist, add a new pair
    if (size == pairs.length) {
      expand(); // Expand if necessary
    }

    pairs[size++] = new KVPair<>(key, value);
    imageLocs.add((String) key);
  }

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException when the key does not appear in the associative
   *                              array.
   */
  public V get(K key) throws KeyNotFoundException {
    for (int i = 0; i < size; i++) {
      if (this.pairs[i].key.equals(key)) {
        return this.pairs[i].value;
      }
    }
    throw new KeyNotFoundException();
  }

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    for (int i = 0; i < size; i++) {
      if (this.pairs[i].key.equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Remove the key/value pair associated with a key. Future calls to get(key)
   * will throw an
   * exception. If the key does not appear in the associative array, does nothing.
   */
  /**
 * Remove the key/value pair associated with a key. Future calls to get(key)
 * will throw an exception. If the key does not appear in the associative
 * array, does nothing.
 */
public void remove(K key) {
  if (key == null) {
    throw new IllegalArgumentException("Key cannot be null");
  }

  int index = -1;  // Initialize index to -1, indicating that the key was not found

  for (int i = 0; i < size; i++) {
    if (this.pairs[i].key.equals(key)) {
      index = i;  // Update index if the key is found
      break;
    }
  }

  if (index == -1) {
    // Print a message indicating the key was not found
    System.out.println("Key not found: " + key);
    return;  // Return early if the key is not found
  }

  pairs[index] = pairs[size - 1]; // Move the last element to the removed position
  pairs[size - 1] = null; // Set the last element to null to avoid duplicate reference
  size--;
  imageLocs.remove(index); 
} // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  }

  public List<String> keys() {
    List<String> keyList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      keyList.add(this.pairs[i].key.toString()); // Assuming keys are strings; adjust as needed
    }
    return keyList;
  }

  /**
   * Get the list of image locations associated with keys.
   *
   * @return The list of image locations.
   */
  public List<String> getImageLocs() {
    List<String> locList = this.keys();

    return locList; // change to return an arr
  }

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  }

  /**
   * Find the index of the first entry in `pairs` that contains key. If no such
   * entry is found,
   * throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < size; i++) {
      if (pairs[i].key.equals(key)) {
        return i;
      }
    }
    throw new KeyNotFoundException(); // Throw an exception if the key is not found
  } // find(K)

} // class AssociativeArray
