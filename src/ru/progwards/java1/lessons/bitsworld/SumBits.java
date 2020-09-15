package ru.progwards.java1.lessons.bitsworld;

public class SumBits {

    public static int sumBits(byte value) {

        byte result = 0;
        if (value < 0) {
            result++;
            value &= 0b0111_1111;
        }
        while (value != 0) {
            result += value & 0b1;
            value >>= 1;
        }
        return result;
    }
}
