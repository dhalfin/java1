package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;


public class StackCalc {

    ArrayDeque<Double> stack = new ArrayDeque<>();

    public void push(double value) {
        stack.push(value);
    }

    public double pop() {
        return stack.pop();
    }

    public void add() {
        Double val = pop() + pop();
        push(val);
    }

    public void sub() {
       Double firstVal = pop();
       Double secondVal = pop();
       push(secondVal - firstVal);
    }

    public void mul() {
        push(pop() * pop());
    }

    public void div() {
        Double firstVal = pop();
        Double secondVal = pop();
        push(secondVal / firstVal);
    }
}
