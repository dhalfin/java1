package ru.progwards.java2.lessons.basetypes;

public class StringKey implements HashValue {

    private String key;

    StringKey(String key) {
        this.key = key;
    }

    private long RSHash(String str) {
        long b = 688551;
        long a = 93623;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = unsignedInt(hash * a + str.charAt(i));
            a = unsignedInt(a * b);
        }
        return hash;
    }

    private static long unsignedInt(long num) {
        return num % Integer.MAX_VALUE;
    }

    @Override
    public int getHash() {
        return (int) RSHash(key);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StringKey)) {
            return false;
        }
        StringKey that = (StringKey) o;
        return this.key.equals(that.key);
    }
}