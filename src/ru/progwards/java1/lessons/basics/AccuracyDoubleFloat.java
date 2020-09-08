package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {

    final static double PI = 3.14;
    final static double EARTH_RADIUS = 6_371.2;

    public static void main(String[] args) {
        System.out.println("The volume, if the radius is equal to 1 in type DOUBLE is " + volumeBallDouble(1));
        System.out.println("The volume, if the radius is equal to 1 in type FLOAT is " + volumeBallFloat(1));
        System.out.println("The volume of planet Earth in type DOUBLE is " + volumeBallDouble(EARTH_RADIUS));
        System.out.println("The volume of planet Earth in type FLOAT is " + volumeBallFloat((float)EARTH_RADIUS));
        System.out.println("Calculation accuracy is " + calculateAccuracy(EARTH_RADIUS));
    }
    public static double volumeBallDouble(double radius) {
       return PI * radius * radius * radius * 4 / 3;
    }

    public static float volumeBallFloat(float radius) {
        return (float) PI * radius * radius * radius * 4 / 3;
    }

    public static double calculateAccuracy(double radius) {
        return volumeBallDouble(radius) - volumeBallFloat((float) radius);
    }
}
