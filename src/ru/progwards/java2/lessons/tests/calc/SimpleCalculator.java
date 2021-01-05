package ru.progwards.java2.lessons.tests.calc;

public class SimpleCalculator {
    public int sum(int val1, int val2) {
        long result = (long) val1 + val2;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
            throw new ArithmeticException();
        return val1 + val2;
    }

    public int diff(int val1, int val2) {
        long result = (long) val1 - val2;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
            throw new ArithmeticException();
        return val1 - val2;
    }

    public int mult(int val1, int val2) {
        long result = (long) val1 * val2;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
            throw new ArithmeticException();
        return val1 * val2;
    }

    public int div(int val1, int val2) {
        if (val2 == 0)
            throw new ArithmeticException();
        return val1 / val2;
    }
}
