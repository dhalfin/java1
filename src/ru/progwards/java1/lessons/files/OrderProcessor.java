package ru.progwards.java1.lessons.files;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
    String startPath;

    public OrderProcessor(String startPath) {
        this.startPath = startPath;
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        return 0;
    }

    public List<Order> process(String shopId) {
        return null;
    }

    public Map<String, Double> statisticsByShop() {
        return null;
    }

    public Map<String, Double> statisticsByGoods() {
        return null;
    }

    public Map<LocalDate, Double> statisticsByDay() {
        return null;
    }

}

class Order {

    public String shopId;
    public String orderId;
    public String customerId;
    public LocalDateTime dateTime;
    public List<OrderItem> items;
    public double sum;
}

class OrderItem {

    public String goodsName;
    public int count;
    public double price;
}
