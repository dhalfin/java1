package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleInfo {
    public static boolean isTriangle(int a, int b, int c) {
        if (a > b) {
            if (a > c) return a < b + c;
        } else {
            if (b > c) return b < c + c;
        }
        return c < a + b;
    }


    public static boolean isRightTriangle(int a, int b, int c) {
        if (a > b) {
            if (a > c) return a * a == b * b + c * c;
        } else {
            if (b > c) return b * b == a * a + c * c;
        }
        return c * c == a * a + b * b;
    }

    public static boolean isIsoscelesTriangle(int a, int b, int c) {
        return a == b || b == c || a == c;
    }
}