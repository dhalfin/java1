package ru.progwards.java1.lessons.basics;

public class Astronomy {

    public static void main(String[] args) {
        System.out.println(earthSquare() + "\n"
                        + mercurySquare() + "\n"
                        + jupiterSquare() + "\n"
                        + earthVsMercury() + "\n"
                        + earthVsJupiter());
    }
    public static Double sphereSquare(Double r) {
        final double PI = 3.14;
        return 4 * PI * Math.pow(r, 2);
    }

    public static Double earthSquare() {
        return sphereSquare(6371.2);
    }

    public static Double mercurySquare() {
        return sphereSquare(2439.7);
    }

    public static Double jupiterSquare() {
        return sphereSquare(71492.0);
    }

    public static Double earthVsMercury() {
        return earthSquare()/mercurySquare();
    }

    public static Double earthVsJupiter() {
        return earthSquare()/jupiterSquare();
    }


}
