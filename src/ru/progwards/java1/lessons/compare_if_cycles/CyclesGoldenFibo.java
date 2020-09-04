package ru.progwards.java1.lessons.compare_if_cycles;

public class CyclesGoldenFibo {

    public static boolean containsDigit(int number, int digit) {
        if (number == 0 && digit == 0) return true;
        for (int i = number; i > 0; i /= 10) {
            if (i % 10 == digit) return true;
        }
        return false;
    }

    public static int fiboNumber(int n) {
        int fib1 = 0;
        int fib2 = 1;
        int x;
        int i = 1;
        while (i++ < n) {
            x = fib2;
            fib2 += fib1;
            fib1 = x;
        }
        return fib2;
    }

    public static boolean isGoldenTriangle(int a, int b, int c) {
        float x = 0;
        if (a == b) x = (float)a / c;
        else if (b == c) x = (float)b / a;
        else if (a == c) x = (float)a / b;
        return x > 1.61703f && x < 1.61903f;
    }

    public static void main(String[] args) {

        System.out.println("The first 15 Fibonacci numbers are: ");
        for (int i = 1; i <= 15; i++)
            System.out.print(fiboNumber(i) + " ");

        System.out.println("\nGolden triangles with the lengths of the base and edges are: ");
        for (int a = 1; a <= 100; a++)
            for (int c = 1; c <= 100; c++)
                if (isGoldenTriangle(a, c, c))
                    System.out.println(a + ", " + c + ", " + c);
    }
}
