package com.innowise.linked_list;

import java.util.NoSuchElementException;

public class CustomLinkedList<E> implements CustomList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

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

    @Override
    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty!");
        }
        return head.value;
    }

    @Override
    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException("The list is empty!");
        }
        return tail.value;
    }

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

    private static class Node<E> {
        E value;
        Node<E> next;

        private Node(E value) {
            this.value = value;
        }
    }
}