package com.innowise.linkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilledCustomLinkedListTest {
    private CustomList<Integer> filledList;

    @BeforeEach
    void setUp() {
        filledList = new CustomLinkedList<>();
        for (int i = 0; i < 5; i++) {
            filledList.add(i, i);
        }
    }

    @Test
    void getSizeOfListSuccessfulTest() {
        assertEquals(5, filledList.size());
    }


    @Test
    void addFirstElementSuccessfulTest() {
        filledList.addFirst(10);

        assertEquals(6, filledList.size());
        assertEquals(10, filledList.get(0));
    }

    @Test
    void addLastElementSuccessfulTest() {
        filledList.addLast(10);

        assertEquals(6, filledList.size());
        assertEquals(10, filledList.get(filledList.size() - 1));
    }

    @Test
    void addElementByIndexSuccessfulTest() {
        filledList.add(3, 10);

        assertEquals(6, filledList.size());
        assertEquals(10, filledList.get(3));
    }

    @Test
    void addElementByNegativeIndexTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> filledList.add(-1, 10));

        String expectedMessage = String.format("Index: %d, size of list: %d", -1, filledList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void addElementByTooBigIndexTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> filledList.add(filledList.size() + 1, 10));

        String expectedMessage = String.format("Index: %d, size of list: %d", filledList.size() + 1, filledList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void getFirstElementSuccessfulTest() {
        assertEquals(0, filledList.getFirst());
    }

    @Test
    void getLastElementSuccessfulTest() {
        assertEquals(4, filledList.getLast());
    }

    @Test
    void getElementByIndexSuccessfulTest() {
        assertEquals(3, filledList.get(3));
    }

    @Test
    void getElementByNegativeIndexTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> filledList.get(-1));

        String expectedMessage = String.format("Index: %d, size of list: %d", -1, filledList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void getElementByTooBigIndexTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> filledList.get(filledList.size()));

        String expectedMessage = String.format("Index: %d, size of list: %d", filledList.size(), filledList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void removeFirstElementSuccessfulTest() {
        int retrievedElement = filledList.removeFirst();

        assertEquals(0, retrievedElement);
        assertEquals(4, filledList.size());
    }

    @Test
    void removeLastElementSuccessfulTest() {
        int retrievedElement = filledList.removeLast();

        assertEquals(4, retrievedElement);
        assertEquals(4, filledList.size());
    }

    @Test
    void removeElementByIndexSuccessfulTest() {
        int retrievedElement = filledList.remove(2);

        assertEquals(2, retrievedElement);
        assertEquals(4, filledList.size());
    }

    @Test
    void removeFirstElementByIndexSuccessfulTest() {
        int retrievedElement = filledList.remove(0);

        assertEquals(0, retrievedElement);
        assertEquals(4, filledList.size());
    }

    @Test
    void removeLastElementByIndexSuccessfulTest() {
        int retrievedElement = filledList.remove(filledList.size() - 1);

        assertEquals(4, retrievedElement);
        assertEquals(4, filledList.size());
    }

    @Test
    void removeElementByNegativeIndexTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> filledList.remove(-1));

        String expectedMessage = String.format("Index: %d, size of list: %d", -1, filledList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void removeElementByTooBigIndexTest() {
        Exception thrownException = assertThrows(IndexOutOfBoundsException.class,
                () -> filledList.remove(filledList.size()));

        String expectedMessage = String.format("Index: %d, size of list: %d", filledList.size(), filledList.size());
        assertEquals(expectedMessage, thrownException.getMessage());
    }
}