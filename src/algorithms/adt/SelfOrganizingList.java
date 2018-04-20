 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt;

/**
 * A self-organizing list is a list that reorders its elements based on some
 * self-organizing heuristic to improve average access time.
 * The aim of a self-organizing list is to improve efficiency of linear search
 * by moving more frequently accessed items towards the head of the list.
 *
 * @param <E>
 */
public interface SelfOrganizingList<E> {
    /**
     * Adds an element to the list
     * 
     * @param element element to be stored in the list
     */
    void add(E element);
    
    /**
     * Check if the list is empty
     *
     * @return false if the list is empty, otherwise true
     */
    boolean isEmpty();
    
    /**
     * Gets the number of elements inserted in the list
     *
     * @return the number of elements inserted in the list
     */
    int size();
}
