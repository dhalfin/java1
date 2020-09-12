package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class Eratosthenes {

    private boolean[] sieve;

    public Eratosthenes(int N) {
        sieve = new boolean[N];
        Arrays.fill(sieve,true);
        sift();
    }

    private void sift() {

        for (int i = 2; i < sieve.length; i++) {
            if (sieve[i]) {
                for (int j = 2 * i; j < sieve.length; j += i) {
                    sieve[j] = false;
                }
            }
        }
    }

    public boolean isSimple(int n) {
        return sieve[n];
    }

    public static void main(String[] args) {
        Eratosthenes eratosthenes = new Eratosthenes(15);
        System.out.println(Arrays.toString(eratosthenes.sieve));
        System.out.println(eratosthenes.isSimple(11));
    }

}
