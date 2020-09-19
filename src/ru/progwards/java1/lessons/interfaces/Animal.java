package ru.progwards.java1.lessons.interfaces;

public class Animal implements FoodCompare, Comparable, CompareWeight {

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

    public double getFood1kgPrice() {
        switch (getFoodKind()) {
            case HAY:
                return 20;
            case CORN:
                return 50;
            case UNKNOWN:
                return 0;
        }
        return 0;
    }

    public double getFoodPrice() {
        return calculateFoodWeight() * getFood1kgPrice();
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        Animal animal = (Animal) smthHasWeigt;
        if (getWeight() < animal.getWeight())
            return CompareResult.LESS;
        else if (getWeight() == animal.getWeight())
            return CompareResult.EQUAL;
        else
            return CompareResult.GREATER;
    }

    @Override
    public int compareFoodPrice(Animal animal) {
        return Double.compare(getFoodPrice(), animal.getFoodPrice());
    }

    @Override
    public int compareTo(Object o) {
        if (o == this) return 0;
        if (!(o instanceof Animal)) throw new RuntimeException("Object o is not inherited from Animal class");
        double weightOfAnimal = this.getWeight();
        double weightOfObjO = ((Animal) o).getWeight();
        if (weightOfAnimal == weightOfObjO) return 0;
        if (weightOfAnimal > weightOfObjO) return 1;
        return -1;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) return true;
        if (anObject == null || getClass() != anObject.getClass())
            return false;
        Animal animal = (Animal) anObject;
        return Double.compare(getWeight(), animal.getWeight()) == 0;
    }


}
