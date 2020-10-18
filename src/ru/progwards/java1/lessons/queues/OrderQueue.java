package ru.progwards.java1.lessons.queues;

import java.util.Comparator;
import java.util.PriorityQueue;

public class OrderQueue {

    static Comparator<Order> comparator = new Comparator<>() {
        @Override
        public int compare(Order o1, Order o2) {
            return Integer.compare(o1.getNum(), o2.getNum());
        }
    };

    static PriorityQueue<Order> FirstClassOrders = new PriorityQueue<>(comparator);
    static PriorityQueue<Order> SecondClassOrders = new PriorityQueue<>(comparator);
    static PriorityQueue<Order> ThirdClassOrders = new PriorityQueue<>(comparator);

    public void add(Order order) {
        if (order.getSum() > 20_000) FirstClassOrders.add(order);
        if (order.getSum() > 10_000 && order.getSum() <= 20_000) SecondClassOrders.add(order);
        if (order.getSum() <= 10_000) ThirdClassOrders.add(order);
    }

    public Order get() {
        if (!FirstClassOrders.isEmpty()) return FirstClassOrders.poll();
        else if (!SecondClassOrders.isEmpty()) return SecondClassOrders.poll();
        else if (!ThirdClassOrders.isEmpty()) return ThirdClassOrders.poll();
        else return null;
    }

}
