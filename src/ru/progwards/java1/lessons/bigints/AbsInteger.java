package ru.progwards.java1.lessons.bigints;

abstract class AbsInteger {

    AbsInteger() {
    }

    @Override
    public String toString() {
        return null;
    }

    public int getValue() {
        return 0;
    }


    public
    static AbsInteger add(AbsInteger num1, AbsInteger num2) {
        int result = num1.getValue() + num2.getValue();
        if (result >= Byte.MIN_VALUE && result <= Byte.MAX_VALUE) {
            return new ByteInteger((byte) result);
        }
        if (result >= Short.MIN_VALUE && result <= Short.MAX_VALUE) {
            return new ShortInteger((short) result);
        }
        return new IntInteger(result);
    }

}
