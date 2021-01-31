package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleHashTable<K extends HashValue, V> implements Iterable<TableItem<K, V>> {

    private Object[] table;
    private int size;
    private final int STAGE_VALUE = 10;

    DoubleHashTable() {
        table = new Object[101];
        size = 0;
    }

    private int getHash(int key) {
        return key % table.length;
    }

    private int getHashForStage(int key) {
        double A = (Math.pow(5, 0.5) - 1) / 2;
        double d = key * A;
        int N = 610;
        return (int) (N * (d - Math.floor(d)));
    }

    public void add(K key, V item) throws Exception {
        innerAdd(new TableItem<K, V>(key, item));
    }

    private void innerAdd(TableItem<K, V> item) throws Exception {
        K key = item.getKey();
        int i = getHash(key.getHash());
        int stage = getHashForStage(key.getHash());
        stage = stage == 0 ? STAGE_VALUE : stage;
        int collisionCnt = 0;
        while (true) {
            if (i >= table.length) {
                i = i % table.length;
            }
            if (table[i] != null && key.equals(((TableItem<K, V>) table[i]).getKey()) &&
                    !((TableItem<K, V>) table[i]).isRemoved) {
                throw new Exception("Key exists");
            }
            if (table[i] == null || ((TableItem<K, V>) table[i]).isRemoved)
                break;
            collisionCnt++;
            if (collisionCnt * 100 / table.length >= 10 || size >= table.length) {
                extendTable();
                i = getHash(key.getHash());
            } else {
                i += stage;
            }
        }
        table[i] = item;
        size++;
    }

    private void extendTable() throws Exception {
        Object[] oldTable = table;
        table = new Object[PrimeNumber.getNearestPrime(table.length * 2)];
        size = 0;
        for (Object o : oldTable) {
            if (o != null && !((TableItem<K, V>) o).isRemoved) {
                TableItem<K, V> item = (TableItem<K, V>) o;
                innerAdd(item);
            }
        }
    }

    public V get(K key) {
        int index = getHash(key.getHash());
        int step = getHashForStage(key.getHash());
        int startIndex = index;
        do {
            if (index >= table.length)
                index = index % table.length;
            if (table[index] == null) {
                return null;
            } else {
                if (key.equals(((TableItem<K, V>) table[index]).getKey())) {
                    if (((TableItem<K, V>) table[index]).isRemoved) {
                        return null;
                    } else {
                        return ((TableItem<K, V>) table[index]).getItem();
                    }
                }
            }
            index += step;
        } while (index != startIndex);
        return null;
    }

    public void remove(K key) {
        int index = getHash(key.getHash());
        int step = getHashForStage(key.getHash());
        int startIndex = index;
        do {
            if (index >= table.length)
                index = index % table.length;
            if (table[index] == null) {
                return;
            } else {
                if (key.equals(((TableItem<K, V>) table[index]).getKey())) {
                    if (((TableItem<K, V>) table[index]).isRemoved) {
                        return;
                    } else {
                        ((TableItem<K, V>) table[index]).isRemoved = true;
                        size--;
                        return;
                    }
                }
            }
            index += step;
        } while (index != startIndex);
    }

    public void change(K key1, K key2) throws Exception {
        V itemValue = get(key1);
        add(key2, itemValue);
        remove(key1);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<TableItem<K, V>> iterator() {
        return getIterator();
    }

    class HashTableIterator implements Iterator<TableItem<K, V>> {

        private int currTableInd;
        private int number;

        HashTableIterator() {
            currTableInd = 0;
            while (currTableInd < table.length && (table[currTableInd] == null ||
                    ((TableItem<K, V>) table[currTableInd]).isRemoved)) {
                currTableInd++;
            }
            number = 0;
        }

        @Override
        public boolean hasNext() {
            return number < size();
        }

        @Override
        public TableItem<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            TableItem<K, V> tableItemToReturn = (TableItem<K, V>) table[currTableInd];
            currTableInd++;
            while (currTableInd < table.length && (table[currTableInd] == null ||
                    ((TableItem<K, V>) table[currTableInd]).isRemoved)) {
                currTableInd++;
            }
            number++;
            return tableItemToReturn;
        }
    }

    public Iterator<TableItem<K, V>> getIterator() {
        return new HashTableIterator();
    }

    public static void main(String[] args) throws Exception {

        DoubleHashTable<IntKey, String> table = new DoubleHashTable<>();
        for (int i = 0; i < 10; i++) {
            table.add(new IntKey(i), "i=" + i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(table.get(new IntKey(i)));
        }
        System.out.println();

        for (TableItem item : table) {
            System.out.println(item);
        }
        System.out.println();

        Iterator<TableItem<IntKey, String>> iterator = table.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            table.remove(new IntKey(i));
            System.out.println(table.size());
        }
    }
}
