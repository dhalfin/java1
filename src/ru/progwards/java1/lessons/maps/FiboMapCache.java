package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;


public class FiboMapCache {

    private Map<Integer, BigDecimal> fiboCache = new HashMap<Integer, BigDecimal>();
    boolean cacheOn;

    public FiboMapCache(boolean cacheOn) {
        this.cacheOn = cacheOn;
    }

    public BigDecimal fiboNumber(int n) {
        if (cacheOn) {
            if (fiboCache.containsKey(n)) {
                return fiboCache.get(n);
            } else {
                BigDecimal dm = fibonacci(n);
                fiboCache.put(n, dm);
                return dm;
            }
        } else {
            return fibonacci(n);
        }
    }

    private static BigDecimal fibonacci(int n) {
        BigInteger n0 = new BigInteger("1");
        BigInteger n1 = new BigInteger("1");
        BigInteger n2 = new BigInteger("0");

        if (n < 0) {
            return new BigDecimal("0");
        } else if (n == 1 || n == 2) {
            return new BigDecimal("1");
        } else {
            for (int i = 3; i <= n; i++) {
                n2 = n0.add(n1);
                n0 = n1;
                n1 = n2;
            }
            return new BigDecimal(n2);
        }
    }

    public void clearCahe() {
        fiboCache = null;
    }

    public static void test() {
        FiboMapCache f;

        long start = currentTimeMillis();
        f = new FiboMapCache(false);
        for (int i = 1; i <= 1000; i++) f.fiboNumber(i);
        System.out.println("fiboNumber cacheOn=" + false + " время выполнения "
                + (currentTimeMillis() - start) + " мсек");

        start = currentTimeMillis();
        f = new FiboMapCache(true);
        for (int i = 1; i <= 1000; i++) f.fiboNumber(i);
        System.out.println("fiboNumber cacheOn=" + true + " время выполнения "
                + (currentTimeMillis() - start) + " мсек");
    }
}

