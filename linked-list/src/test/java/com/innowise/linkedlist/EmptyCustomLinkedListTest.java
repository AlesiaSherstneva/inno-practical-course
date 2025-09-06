package com.innowise.linkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmptyCustomLinkedListTest {
    private CustomList<String> emptyList;

    @BeforeEach
    void setUp() {
        emptyList = new CustomLinkedList<>();
    }

    @Test
    void getSizeOfListSuccessfulTest() {
        assertEquals(0, emptyList.size());
    }

    @Test
    void addFirstElementSuccessfulTest() {
        emptyList.addFirst("test");

        assertEquals(1, emptyList.size());
        assertEquals("test", emptyList.get(0));
    }

    @Test
    void addLastElementSuccessfulTest() {
        emptyList.addLast("test");

        assertEquals(1, emptyList.size());
        assertEquals("test", emptyList.get(emptyList.size() - 1));
    }

    @Test
    void getFirstElementFromEmptyListTest() {
        Exception thrownException = assertThrows(NoSuchElementException.class,
                () -> emptyList.getFirst());

        String expectedMessage = "The list is empty!";
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void getLastElementFromEmptyListTest() {
        Exception thrownException = assertThrows(NoSuchElementException.class,
                () -> emptyList.getLast());

        String expectedMessage = "The list is empty!";
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void getAnyElementFromEmptyListTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> emptyList.get(100));

        String expectedMessage = String.format("Index: %d, size of list: %d", 100, emptyList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void removeFirstElementFromEmptyListTest() {
        Exception thrownException = assertThrows(NoSuchElementException.class,
                () -> emptyList.removeFirst());

        String expectedMessage = "The list is empty!";
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void removeTheOnlyOneFirstElementTest() {
        String newElement = "test";
        emptyList.addLast(newElement);

        String retrievedElement = emptyList.removeFirst();

        assertEquals(newElement, retrievedElement);
        assertEquals(0, emptyList.size());
    }

    @Test
    void removeLastElementFromEmptyListTest() {
        Exception thrownException = assertThrows(NoSuchElementException.class,
                () -> emptyList.removeLast());

        String expectedMessage = "The list is empty!";
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void removeTheOnlyOneLastElementTest() {
        String newElement = "test";
        emptyList.addFirst(newElement);

        String retrievedElement = emptyList.removeLast();

        assertEquals(newElement, retrievedElement);
        assertEquals(0, emptyList.size());
    }

    @Test
    void removeAnyElementFromEmptyListTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> emptyList.remove(100));

        String expectedMessage = String.format("Index: %d, size of list: %d", 100, emptyList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }
}