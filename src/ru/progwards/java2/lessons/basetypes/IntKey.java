package ru.progwards.java2.lessons.basetypes;

public class IntKey implements HashValue {

    private int key;

    IntKey(int key) {
        this.key = key;
    }

    @Override
    public int getHash() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntKey)) {
            return false;
        }
        IntKey that = (IntKey) o;
        return this.key == that.key;
    }

    @Override
    public String toString() {
        return Integer.toString(key);
    }
}