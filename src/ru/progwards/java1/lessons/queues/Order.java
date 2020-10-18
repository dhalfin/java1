package ru.progwards.java1.lessons.queues;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private double sum;
    private int num;

    static List<Order> orderList = new ArrayList<>();

    public Order(double sum) {
        this.sum = sum;
        orderList.add(this);
        num = orderList.indexOf(this) + 1;
    }

    public double getSum() {
        return sum;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Order { " +
                "sum = " + sum +
                ", num = " + num +
                '}';
    }
}
