package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class FiboMapCache {
    private Map<Integer, BigDecimal> fiboCache;
    boolean cacheOn;

    public FiboMapCache(boolean cacheOn) {
        this.cacheOn = cacheOn;
        fiboCache = new HashMap<>();
    }

    public BigDecimal resultFibo(int n) {
        BigDecimal fib1 = new BigDecimal(1);
        BigDecimal fib2 = new BigDecimal(1);
        for (int i = 2; i < n; i++) {
            BigDecimal fibNext = fib1.add(fib2);
            fib1 = fib2;
            fib2 = fibNext;
        }
        return fib2;
    }

    public BigDecimal fiboNumber(int n) {
        try {
            if (cacheOn) {
                if (fiboCache.containsKey(n))
                    return fiboCache.get(n);
                fiboCache.put(n, resultFibo(n));
                return fiboCache.get(n);
            }
        } catch (Throwable t) {
            System.out.println(t);
        }
        return resultFibo(n);
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

