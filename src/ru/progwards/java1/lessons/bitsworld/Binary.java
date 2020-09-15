package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    byte num;

    public Binary(byte num) {
        this.num = num;
    }

    public String toString() {
        String res = "";

        for (int i = 0; i < 8; i++) {
            int resInt = num & 0b1;
            res = resInt + res;
            num >>= 1;
        }
        return res;
    }
}
