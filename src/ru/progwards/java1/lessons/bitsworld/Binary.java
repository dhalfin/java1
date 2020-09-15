package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    byte num;

    public Binary(byte num) {
        this.num = num;
    }

    public String toString() {
        int n = num & 0x7F_FF_FF_FF;
        String result = num < 0 ? "1" : "0";
        for (int i = 1; i < 8; i++) {
            result += (n & 0x40_00_00_00) == 0x40_00_00_00 ? "1" : "0";
            n <<= 1;
        }
        return result;
    }
}
