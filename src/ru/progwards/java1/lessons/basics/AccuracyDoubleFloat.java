package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {

    public static void main(String[] args) {
        System.out.println(calculateAccuracy(6371.2));
    }
    public static double volumeBallDouble(double radius) {
        final double PI = 3.14;
        return 4/3 * PI * Math.pow(radius,3);
    }

    public static float volumeBallFloat(float radius) {
        final float PI = 3.14F;
        return (float) (4/3 * PI * Math.pow(radius,3));
    }

    public static double calculateAccuracy(double radius) {
        final double R = radius;
        return volumeBallDouble(R) - volumeBallFloat((float) R);
    }
}
