package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackCalc {

    static Deque<Double> stack = new ArrayDeque<>();

    public void push(double value) {
        stack.push(value);
    }

    public double pop() {
        return stack.pop();
    }

    public void add() {
        //Double val = pop() + pop();
        push(pop() + pop());
    }

    public void sub() {
//        Double firstVal = pop();
//        Double secondVal = pop();
        push(pop() - pop());
    }

    public void mul() {
        push(pop() * pop());
    }

    public void div() {
        push(pop() / pop());
    }
}
