package ru.progwards.java1.lessons.queues;

public class Calculate {

    static StackCalc stack = new StackCalc();

    public static double calculation1() {
        stack.push(2.2);
        stack.push(12.1);
        stack.push(3);
        stack.add();
        stack.mul();
        return stack.pop();
    }

    public static double calculation2() {
        stack.push(13.001);
        stack.push(9.2);
        stack.sub();
        stack.push(2);
        stack.mul();
        stack.push(87);
        stack.add();
        stack.push(19);
        stack.push(3.33);
        stack.sub();
        stack.mul();
        stack.push(24);
        stack.push(737.22);
        stack.add();
        stack.push(55.6);
        stack.push(12.1);
        stack.sub();
        stack.div();
        stack.add();
        return stack.pop();
    }
}
