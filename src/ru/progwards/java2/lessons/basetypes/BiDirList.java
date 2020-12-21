package ru.progwards.java2.lessons.basetypes;

import java.util.*;

public class BiDirList<T> implements Iterable<T> {

    public static class Node<T> {
        T item;
        Node preceding;
        Node following;

        Node(Node<T> preceding, T item, Node<T> following) {
            this.item = item;
            this.preceding = preceding;
            this.following = following;
        }
    }

    int size;
    Node<T> firstNode;
    Node<T> lastNode;

    BiDirList() {
        size = 0;
        firstNode = null;
        lastNode = null;
    }

    public void addLast(T item) {
        Node<T> node = new Node(lastNode, item, null);
        if (lastNode != null) {
            lastNode.following = node;
        } else {
            firstNode = node;
        }
        lastNode = node;
        size++;
    }

    public void addFirst(T item) {
        Node<T> node = new Node(null, item, firstNode);
        if (firstNode != null) {
            firstNode.preceding = node;
        } else {
            lastNode = node;
        }
        firstNode = node;
        size++;
    }

    public void remove(T item) {
        Node<T> node = firstNode;
        while (node != null) {
            if (node.item == item) {
                if (node.preceding == null) {
                    firstNode = node.following;
                } else {
                    node.preceding.following = node.following;
                }

                if (node.following == null) {
                    lastNode = node.preceding;
                } else {
                    node.following.preceding = node.preceding;
                }
                size--;
            }
            node = node.following;
        }
    }

    public T at(int i) {
        if (i < 0 || i >= size) {
            return null;
        }
        int j = 0;
        Node<T> node = firstNode;
        while (node != null && j != i) {
            node = node.following;
            j++;
        }
        return node.item;
    }

    public int size() {
        return size;
    }

    public static <T> BiDirList<T> from(T[] array) {
        BiDirList<T> result = new BiDirList<T>();
        for (T item : array) {
            result.addLast(item);
        }
        return result;
    }

    public static <T> BiDirList<T> of(T... array) {
        BiDirList<T> list = new BiDirList<T>();
        for (T item : array) {
            list.addLast(item);
        }
        return list;
    }

    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        int i = 0;
        Node<T> node = firstNode;
        while (node != null) {
            array[i++] = node.item;
            node = node.following;
        }
        return array;
    }

    public Iterator<T> getIterator() {
        return new ForwardIterator();
    }

    public Iterator<T> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<T> {
        private Node<T> lastReturned;

        ForwardIterator() {
            lastReturned = null;
        }

        public boolean hasNext() {
            return lastReturned == null || lastReturned.following != null;
        }

        public T next() {
            lastReturned = lastReturned == null ? firstNode : lastReturned.following;
            return lastReturned.item;
        }
    }

    public static void main(String[] args) {
        BiDirList<Integer> l = BiDirList.of(3, 1, 4, 6);
        for (Integer i : l) {
            System.out.println(i);
        }
    }
}