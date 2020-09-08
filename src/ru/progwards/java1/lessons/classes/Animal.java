package ru.progwards.java1.lessons.classes;

public class Animal {

    private double weight = 1d;
    double foodCoeff = 0.02;

    private AnimalKind animalKind = AnimalKind.ANIMAL;
    private FoodKind foodKind = FoodKind.UNKNOWN;

    public Animal(double weight) {
        this.weight = weight;
    }

    public AnimalKind getKind() {
        return animalKind;
    }

    public FoodKind getFoodKind() {
        return foodKind;
    }

    public String toString() {
        return "I am " + getKind() + ", eat " + getFoodKind();
    }

    public String toStringFull() {
        return "I am " + getKind() + ", eat " + getFoodKind() + " " + calculateFoodWeight();
    }

    public double getWeight() {
        return weight;
    }

    public double getFoodCoeff() {
        return foodCoeff;
    }

    public double calculateFoodWeight() {
        return getWeight() * getFoodCoeff();
    }


}
