package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;
import java.util.Arrays;

public class ArrayInteger {
    byte[] digits;

    ArrayInteger(int n) {
        digits = new byte[n];
    }

    void fromInt(BigInteger value) {
        int size = value.toString().length();
        for (int i = 0; i < size; i++) {
            this.digits[i] = (value.mod(BigInteger.valueOf(10))).byteValue();
            value = value.divide(BigInteger.valueOf(10));
        }
    }

    BigInteger toInt() {
        String str = "";
        for (int i = this.digits.length - 1; i >= 0; i--) {
            str += digits[i] + "";
        }
        BigInteger output = new BigInteger(str);
        return output;
    }

    boolean add(ArrayInteger num) {

        if (num.digits.length > this.digits.length) {
            Arrays.fill(this.digits, (byte) 0);
            return false;
        }

        byte res;
        byte resSecond = 0;


        for (int i = 0; i < this.digits.length; i++) {
            if (i < num.digits.length) {
                res = (byte) ((this.digits[i] + num.digits[i]) % 10 + resSecond);
                resSecond = (byte) ((this.digits[i] + num.digits[i]) / 10);
            } else {
                res = (byte) ((this.digits[i] + resSecond) % 10);
                System.out.print(i + " res = " + res);

                resSecond = (byte) ((this.digits[i] + resSecond) / 10);
                if (resSecond > 0) {
                    this.digits[i] = res;
                    continue;
                }
            }
            this.digits[i] = res;
        }
        if (resSecond > 0) {
            Arrays.fill(this.digits, (byte) 0);
            return false;
        }
        return true;
    }
}
