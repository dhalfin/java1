package ru.progwards.java1.lessons.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator<T> implements Iterator<T> {
    private T[][] array;
    private int i, j = 0;

    MatrixIterator(T[][] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return i < array.length && j < array[i].length;
    }

    @Override
    public T next() {
        if (i == array.length)
            throw new NoSuchElementException("Элемент отсутствует!");
        if (j == array[i].length - 1) {
            int currentJValue = j;
            j = 0;
            return array[i++][currentJValue];
        } else {
            return array[i][j++];
        }
    }
}