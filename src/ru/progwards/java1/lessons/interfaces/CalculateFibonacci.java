package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {

    static CacheInfo lastFibo;

    public static class CacheInfo {

        public int n;
        public int fibo;
    }

    public static int fiboNumber(int n) {
        int fiboFirst = 1;
        int fiboSecond = 1;

        if (lastFibo == null)
            lastFibo = new CacheInfo();
        if (n != lastFibo.n) {

            for (int i = 2; i < n; i++) {
                int fiboNext = fiboFirst + fiboSecond;

                fiboFirst = fiboSecond;
                fiboSecond = fiboNext;
            }
            lastFibo.n = n;
            lastFibo.fibo = fiboSecond;
        }
        return lastFibo.fibo;
    }

    public static CacheInfo getLastFibo() {
        return lastFibo;
    }

    public static void clearLastFibo() {
        lastFibo = null;
    }
}
