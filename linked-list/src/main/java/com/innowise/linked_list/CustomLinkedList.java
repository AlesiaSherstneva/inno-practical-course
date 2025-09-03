package com.innowise.linked_list;

import java.util.NoSuchElementException;

/**
 * An implementation of the {@link CustomList} interface using linked list.
 *
 * @param <E> {@inheritDoc}
 */
public class CustomLinkedList<E> implements CustomList<E> {
    /** Reference to the first node of the list. */
    private Node<E> head;

    /** Reference to the last node of the list. */
    private Node<E> tail;

    /** The number of elements in the list. */
    private int size;

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     *
     * @param element {@inheritDoc}
     */
    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);

        if (head == null) {
            setAloneNode(newNode);
        } else {
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    /**
     * {@inheritDoc}
     *
     * @param element {@inheritDoc}
     */
    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);

        if (tail == null) {
            setAloneNode(newNode);
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    /**
     * {@inheritDoc}
     *
     * @param index {@inheritDoc}
     * @param element {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc} ({@code index < 0 || index > size()})
     */
    @Override
    public void add(int index, E element) {
        checkNewNodeIndex(index);

        if (index == 0) {
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else {
            int tempIndex = 0;
            Node<E> tempNode = head;

            while (tempIndex < index - 1) {
                tempNode = tempNode.next;
                tempIndex++;
            }

            Node<E> newNode = new Node<>(element);
            newNode.next = tempNode.next;
            tempNode.next = newNode;

            size++;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws NoSuchElementException {@inheritDoc}
     */
    @Override
    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty!");
        }
        return head.value;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws NoSuchElementException {@inheritDoc}
     */
    @Override
    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException("The list is empty!");
        }
        return tail.value;
    }

    /**
     * {@inheritDoc}
     *
     * @param index {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchElementException {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc} ({@code index < 0 || index >= size()})
     */
    @Override
    public E get(int index) {
        checkExistingNodeIndex(index);

        if (index == 0) {
            return getFirst();
        } else if (index == size - 1) {
            return getLast();
        } else {
            int tempIndex = 0;
            Node<E> tempNode = head;

            while (tempIndex < index) {
                tempNode = tempNode.next;
                tempIndex++;
            }

            return tempNode.value;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws NoSuchElementException {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty!");
        }

        Node<E> firstNode = head;
        if (head == tail) {
            setNullNodes();
        } else {
            head = head.next;
        }

        size--;

        return firstNode.value;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws NoSuchElementException {@inheritDoc}
     */
    @Override
    public E removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("The list is empty!");
        }

        Node<E> lastNode = tail;
        if (tail == head) {
            setNullNodes();
        } else {
            Node<E> tempNode = head;
            while (tempNode.next != tail) {
                tempNode = tempNode.next;
            }
            tail = tempNode;
            tail.next = null;
        }

        size--;

        return lastNode.value;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws NoSuchElementException {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc} ({@code index < 0 || index >= size()})
     */
    @Override
    public E remove(int index) {
        checkExistingNodeIndex(index);

        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<E> tempNode = head;
            for (int i = 0; i < index - 1; i++) {
                tempNode = tempNode.next;
            }

            Node<E> retrievedNode = tempNode.next;
            tempNode.next = retrievedNode.next;

            size--;

            return retrievedNode.value;
        }
    }

    private void setAloneNode(Node<E> firstNode) {
        head = firstNode;
        tail = firstNode;
    }

    private void setNullNodes() {
        head = null;
        tail = null;
    }

    private void checkNewNodeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, size of list: %d", index, size));
        }
    }

    private void checkExistingNodeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, size of list: %d", index, size));
        }
    }

    /**
     * Represents a node in the linked list, containing an element and a reference to the next node.
     */
    private static class Node<E> {
        E value;
        Node<E> next;

        private Node(E value) {
            this.value = value;
        }
    }
}