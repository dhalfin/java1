package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    public String shopId = "";
    public String orderId = "";
    public String customerId = "";
    public LocalDateTime datetime;
    public List<OrderItem> items;
    public double sum = 0;


    public Order(Path fileOrder, List<OrderItem> orderItemList) throws IOException {
        this.shopId = fileOrder.getFileName().toString().substring(0, 3);
        this.orderId = fileOrder.getFileName().toString().substring(5, 10);
        this.customerId = fileOrder.getFileName().toString().substring(12, 16);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        this.datetime = LocalDateTime.from(dtf.parse(Files.getAttribute(fileOrder, "lastModifiedTime").toString()));
        this.items = orderItemList;
        for (OrderItem item : items) {
            this.sum += item.count * item.price;
        }
    }
}

