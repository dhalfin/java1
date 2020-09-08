package ru.progwards.java1.lessons.classes;

public class ComplexNum {
    int a;
    int b;
    int aNewOne;
    int bNewOne;

    public ComplexNum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + "+" + b + "i";
    }

    public ComplexNum add(ComplexNum num) {
        aNewOne = a + num.a;
        bNewOne = b + num.b;
        return new ComplexNum(aNewOne, bNewOne);
    }

    public ComplexNum sub(ComplexNum num) {
        return new ComplexNum(a - num.a, b - num.b);
    }

    public ComplexNum mul(ComplexNum num) {
        aNewOne = a * num.a - b * num.b;
        bNewOne = b * num.a + a * num.b;
        return new ComplexNum(aNewOne, bNewOne);
    }

    public ComplexNum div(ComplexNum num) {
        aNewOne = (a * num.a + b * num.b) / (num.a * num.a + num.b * num.b);
        bNewOne = (b * num.a - a * num.b) / (num.a * num.a + num.b * num.b);
        return new ComplexNum(aNewOne, bNewOne);
    }


}
