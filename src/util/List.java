package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<E> implements Iterable<E> {
    private E[] elements; // Generic array to hold elements
    private int size; // Number of elements in the array
    private static final int NOT_FOUND = -1;
    private static final int INITIAL_CAPACITY = 4;

    /**
     * Constructs an empty list with an initial capacity of 4.
     */
    public List() {
        elements = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Finds the index of the specified element in the array.
     * 
     * @param element The element to search for.
     * @return The index of the element if found, -1 otherwise.
     */
    private int find(E element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the size of the elements array by doubling its capacity.
     */
    private void grow() {
        // Manually create a new array with double the capacity and copy elements over
        E[] newElements = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i]; // Copy each element manually
        }
        elements = newElements; // Assign the new array to the elements field
    }

    /**
     * Checks if the list contains a specific element.
     * 
     * @param element The element to check for.
     * @return true if the list contains the element, false otherwise.
     */
    public boolean contains(E element) {
        return find(element) != NOT_FOUND;
    }

    /**
     * Adds a new element to the list. If the array is full, its capacity is
     * increased.
     * 
     * @param element The element to add to the list.
     */
    public void add(E element) {
        if (size == elements.length) {
            grow(); // Resize the array if needed
        }
        elements[size++] = element;
    }

    /**
     * Removes an element from the list. The array elements are shifted to fill the
     * gap.
     * 
     * @param element The element to remove from the list.
     */
    public void remove(E element) {
        int index = find(element); // Find the index of the element
        if (index != NOT_FOUND) {
            // Shift elements to the left to fill the gap
            for (int i = index; i < size - 1; i++) {
                elements[i] = elements[i + 1]; // Shift left
            }
            elements[--size] = null; // Clear the last element
        } else {
            throw new NoSuchElementException("Element not found: " + element);
        }
    }

    /**
     * Gets the number of elements in the list.
     * 
     * @return The number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retrieves an element at a given index.
     * 
     * @param index The index of the element to retrieve.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public E get(int index) {
        if (index >= 0 && index < size) {
            return elements[index];
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    /**
     * Replaces the element at the specified index with the provided element.
     * 
     * @param index   The index to replace.
     * @param element The new element.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void set(int index, E element) {
        if (index >= 0 && index < size) {
            elements[index] = element;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    /**
     * Returns the index of the given element, or -1 if not found.
     * 
     * @param element The element to find.
     * @return The index of the element, or -1 if not found.
     */
    public int indexOf(E element) {
        return find(element);
    }

    /**
     * Returns an iterator for the list.
     * 
     * @return An iterator for the list.
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Private class implementing the iterator for the List.
     */
    private class ListIterator implements Iterator<E> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return elements[currentIndex++];
        }
    }
}
