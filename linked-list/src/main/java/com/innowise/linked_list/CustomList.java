package com.innowise.linked_list;

import java.util.NoSuchElementException;

/**
 * This interface represents a generic list of elements. Provides basic operations for
 * adding, retrieving and removing elements.
 *
 * @param <E> the type of elements
 */
public interface CustomList<E> {
    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements
     */
    int size();

    /**
     * Inserts the specified element at the beginning of the list.
     *
     * @param element the element to be inserted
     */
    void addFirst(E element);

    /**
     * Appends the specified element to the end of the list.
     *
     * @param element the element to be added
     */
    void addLast(E element);

    /**
     * Inserts the specified element at the specified position in the list.
     *
     * @param index the index of the specified position
     * @param element the element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    void add(int index, E element);

    /**
     * Returns the first element of the list.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    E getFirst();

    /**
     * Returns the last element of the list.
     *
     * @return the last element
     * @throws NoSuchElementException if the list is empty
     */
    E getLast();

    /**
     * Returns the element at the specified position in the list.
     *
     * @param index the index of the specified position
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws NoSuchElementException if the list is empty
     */
    E get(int index);

    /**
     * Removes and returns the first element from the list.
     *
     * @return the first element that was removed
     * @throws NoSuchElementException if the list is empty
     */
    E removeFirst();

    /**
     * Removes and returns the last element from the list.
     *
     * @return the last element that was removed
     * @throws NoSuchElementException if the list is empty
     */
    E removeLast();

    /**
     * Removes and returns the element from the specified position in the list.
     *
     * @param index the index of the specified position
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws NoSuchElementException if the list is empty
     */
    E remove(int index);
}