package ru.progwards.java1.lessons.bigints;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigAlgebra {

    static BigDecimal fastPow(BigDecimal num, int pow) {
        if (pow == 0)
            return num;
        else {
            //exponentiation by squaring
            BigDecimal result = BigDecimal.ONE;

            while (pow > 0) {
                if ((pow & 1) == 1) {
                    result = result.multiply(num);
                }
                num = num.multiply(num);
                pow >>= 1;
            }
            return result;
        }
    }

    static BigInteger fibonacci(int n) {
        BigInteger n1 = BigInteger.ONE;
        BigInteger n2 = BigInteger.ONE;

        for (int i = 2; i < n; i++) {
            BigInteger fibNumber = n1.add(n2);
            n1 = n2;
            n2 = fibNumber;
        }
        return n2;
    }
}

