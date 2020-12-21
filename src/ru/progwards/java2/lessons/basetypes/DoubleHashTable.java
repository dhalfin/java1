package ru.progwards.java2.lessons.basetypes;

import java.util.*;


interface HashValue {
    int getHash();
}


class IntValue implements HashValue {
    private final int v;

    public IntValue(int value) {
        v = value;
    }

    public static IntValue of(int value) {
        return new IntValue(value);
    }

    public String toString() {
        return "{" + v + '}';
    }

    public int getHash() {
        return v;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return v == intValue.v;
    }
}


interface DoubleHashFunction {
    int apply(int hash, int size, int coll);
}

public class DoubleHashTable<K extends HashValue, V>
        extends Dictionary<K, V> {


    static class PrimeNumber {
        static List<Integer> list;
        private static int lastChecked;

        static {
            list = new ArrayList<Integer>();
            list.addAll(Arrays.asList(2, 3, 6, 7, 11));
            lastChecked = 12;
        }

        static private void fillArray(int fillTo) {
            for (int num = lastChecked + 1; num <= fillTo; num++) {
                for (int i : list) {
                    if (num % i == 0) break;
                    if (i * i > num) {
                        list.add(num);
                        break;
                    }
                }
            }
            lastChecked = fillTo;
        }

        static int getHigher(int num) {
            int fillTo = (int) Math.sqrt(num + Math.sqrt(num));
            fillArray(fillTo);
            boolean found = false;
            while (!found) {
                num++;
                found = true;
                for (int i : list) {
                    if (num % i == 0) {
                        found = i == num;
                        break;
                    }
                }
            }
            return num;
        }
    }


    static class Entry<K extends HashValue, V> {

        private V value;
        final private K key;
        final private int hash;

        Entry(int hash, K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }


    private Object[] store;
    private boolean[] removed;
    private int sizeOfStore;
    private int increasePer;
    private int collisionsPer = 10;
    private int size;
    private int limit;
    private DoubleHashFunction doubleHashFunction;

    DoubleHashTable() {
        sizeOfStore = 100;
        increasePer = 100;
        initialize();
    }

    DoubleHashTable(int sizeOfStore) {
        this.sizeOfStore = sizeOfStore;
        increasePer = 100;
        initialize();
    }

    DoubleHashTable(int sizeOfStore, int increasePer) {
        this.sizeOfStore = sizeOfStore;
        this.increasePer = increasePer;
        initialize();
    }

    private void initialize() {
        sizeOfStore = PrimeNumber.getHigher(sizeOfStore);
        store = new Object[sizeOfStore];
        removed = new boolean[sizeOfStore];
        size = 0;
        limit = sizeOfStore * collisionsPer / 100;
        doubleHashFunction = (hash, size, collisions) -> {
            double d = 0.6180339887d * ((hash + collisions * hash) & 0x7FFFFFFF);
            int outcome = (int) ((d - Math.floor(d)) * size);
            return outcome;
        };
    }

    public void setCalculateFunction(DoubleHashFunction function) {
        if (size == 0) {
            doubleHashFunction = function;
        } else {
            throw new IllegalStateException("Table has values");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Enumeration<K> keys() {
        return this.<K>getEnumeration(KEYS);
    }

    public Enumeration<V> elements() {
        return this.<V>getEnumeration(VALUES);
    }

    protected void rehashWithIncrement() {
        sizeOfStore = sizeOfStore + sizeOfStore * increasePer / 100;
        rehash();
    }

    protected void rehash() {
        DoubleHashTable table = new DoubleHashTable(sizeOfStore, increasePer);
        table.setCalculateFunction(doubleHashFunction);

        for (int i = store.length; i-- > 0; ) {
            Entry<K, V> e = (Entry<K, V>) store[i];
            if (e != null) {
                table.put(e.key, e.value);
            }
        }

        sizeOfStore = table.sizeOfStore;
        store = table.store;
        removed = table.removed;
        limit = table.limit;
    }

    public V put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        int hash = key.getHash();
        int collisions = 0;
        while (true) {
            if (collisions > limit) {
                rehashWithIncrement();
                collisions = 0;
            }
            int index = doubleHashFunction.apply(hash, sizeOfStore, collisions++);
            Entry<K, V> e = (Entry<K, V>) store[index];
            if (e == null) {
                store[index] = new Entry<>(hash, key, value);
                removed[index] = false;
                size++;
                return null;
            } else if ((e.hash == hash) && e.key.equals(key)) {
                V oldValue = e.value;
                e.value = value;
                size++;
                return oldValue;
            }
        }
    }

    public V add(K key, V value) {
        return put(key, value);
    }

    public V get(Object key) {
        int hash = ((K) key).getHash();
        int collisions = 0;
        while (true) {
            if (collisions > limit) {
                return null;
            }
            int index = doubleHashFunction.apply(hash, sizeOfStore, collisions++);
            Entry<K, V> e = (Entry<K, V>) store[index];
            if (e == null) {
                if (!removed[index]) return null;
            } else if ((e.hash == hash) && e.key.equals(key)) {
                return e.value;
            }
        }
    }

    public V remove(Object key) {
        int hash = ((K) key).getHash();
        int collisions = 0;
        while (true) {
            if (collisions > limit) {
                return null;
            }
            int index = doubleHashFunction.apply(hash, sizeOfStore, collisions++);
            Entry<K, V> e = (Entry<K, V>) store[index];
            if (e == null) {
                if (!removed[index]) {
                    return null;
                }
            } else if ((e.hash == hash) && e.key.equals(key)) {
                store[index] = null;
                removed[index] = true;
                size--;
                return e.value;
            }
        }
    }

    public V change(K key1, K key2) {
        V value = remove(key1);
        put(key2, value);
        return value;
    }

    private <T> Enumeration<T> getEnumeration(int type) {
        if (size == 0) {
            return Collections.emptyEnumeration();
        } else {
            return new Enumerator<>(type, false);
        }
    }

    private <T> Iterator<T> getIterator(int type) {
        if (size == 0) {
            return Collections.emptyIterator();
        } else {
            return new Enumerator<>(type, true);
        }
    }

    public Iterator<Entry<K, V>> getIterator() {
        return getIterator(ENTRIES);
    }


    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;


    private class Enumerator<T> implements Enumeration<T>, Iterator<T> {
        final Object[] table = DoubleHashTable.this.store;
        final boolean[] deleted = DoubleHashTable.this.removed;
        int storageIndex = -1;
        int count = 0;
        int allCount = DoubleHashTable.this.size;
        int storageSize = DoubleHashTable.this.sizeOfStore;
        final int type;


        final boolean iterator;

        Enumerator(int type, boolean iterator) {
            this.type = type;
            this.iterator = iterator;
        }

        public boolean hasMoreElements() {
            return count < allCount;
        }

        public T nextElement() {
            for (int i = storageIndex + 1; i < storageSize; i++) {
                Entry<?, ?> e = (Entry<?, ?>) table[i];
                if (e != null) {
                    storageIndex = i;
                    count++;
                    return type == KEYS ? (T) e.key : (type == VALUES ? (T) e.value : (T) e);
                }
            }
            throw new NoSuchElementException("Hashtable Enumerator");
        }

        public boolean hasNext() {
            return hasMoreElements();
        }

        public T next() {
            return nextElement();
        }

        public void remove() {
            if (!iterator) throw new UnsupportedOperationException();
            store[storageIndex] = null;
            deleted[storageIndex] = true;
        }
    }


}