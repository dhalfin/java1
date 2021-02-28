package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BiDirList<T> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return getIterator();
    }

    class ListItem<T> {

        private T item;
        private ListItem<T> following;
        private ListItem<T> preceding;

        ListItem(T item) {
            this.item = item;
        }

        T getItem() {
            return item;
        }

        void setFollowing(ListItem<T> item) {
            following = item;
        }

        void setPreceding(ListItem<T> item) {
            preceding = item;
        }

        ListItem<T> getFollowing() {
            return following;
        }

        ListItem<T> getPreceding() {
            return preceding;
        }

    }

    private ListItem<T> head;
    private ListItem<T> tail;
    private int size;

    ListItem<T> getHead() {
        return head;
    }

    ListItem<T> getTail() {
        return tail;
    }

    public void addLast(T item) {
        ListItem<T> li = new ListItem<T>(item);
        if (head == null) {
            head = li;
        } else {
            tail.setFollowing(li);
            li.setPreceding(tail);
        }
        tail = li;
        size++;
    }

    public void addFirst(T item) {
        ListItem<T> li = new ListItem<T>(item);
        if (head == null) {
            head = li;
            tail = li;
        } else {
            head.setPreceding(li);
            li.setFollowing(head);
            head = li;
        }
        size++;
    }

    public void remove(T item) {
        ListItem<T> current = getHead();
        while (current != null) {
            if (item.equals(current.getItem())) {
                ListItem<T> prevItem = current.getPreceding();
                if (prevItem != null) {
                    prevItem.setFollowing(current.getFollowing());
                } else {
                    head = current.getFollowing();
                }
                ListItem<T> nextItem = current.getFollowing();
                if (nextItem != null) {
                    nextItem.setPreceding(current.getPreceding());
                } else {
                    tail = current.getPreceding();
                }
                size--;
                return;
            }
            current = current.getFollowing();
        }
        throw new NoSuchElementException();
    }

    public T at(int i) {
        if (i >= size() || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        ListItem<T> current = getHead();
        while (current != null) {
            if (counter == i) {
                return current.getItem();
            }
            current = current.getFollowing();
            counter++;
        }
        return null;
    }

    public int size() { // получить количество элементов
        return size;
    }

    public static <T> BiDirList<T> from(T[] array) {
        BiDirList<T> list = new BiDirList<>();
        for (T elem : array) {
            list.addLast(elem);
        }
        return list;
    }

    public static <T> BiDirList<T> of(T... array) { //  конструктор из массива
        return BiDirList.from(array);
    }

    public void toArray(T[] array) {
        ListItem<T> temp = head;
        int i = 0;
        while (temp != null) {
            array[i] = temp.item;
        }
    }

//    public T[] toArray(T[] array) {
//        int sizeOfList = size();
//        if (array.length < sizeOfList) {
//            array = (T[]) new Object[sizeOfList];
//        }
//        ListItem<T> elem = getHead();
//        for (int i = 0; i < array.length; i++) {
//            if (i > sizeOfList) {
//                array[i] = null;
//            } else {
//                array[i] = elem.getItem();
//                elem = elem.getFollowing();
//            }
//        }
//        return array;
//    }

    public Iterator<T> getIterator() {
        return new Iterator<T>() {
            private ListItem<T> currentListItem = getHead();
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                ListItem<T> listItemToReturn = currentListItem;
                currentListItem = currentListItem.getFollowing();
                index++;
                return listItemToReturn.getItem();
            }
        };
    }

    public static void main(String[] args) {

        BiDirList<Integer> list = new BiDirList<>();
        for (int i = 0; i < 15; i++) {
            list.addLast(i);
        }

        for (Integer i : list) {
            System.out.println(i);
        }
        System.out.println();
        list.remove(list.getHead().getItem());
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}