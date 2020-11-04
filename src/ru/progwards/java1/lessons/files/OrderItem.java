package ru.progwards.java1.lessons.files;

public class OrderItem {
    public String googsName = "";
    public int count = 0;
    public double price = 0;

    public OrderItem(String[] itemOfOrder) {
        this.googsName = itemOfOrder[0].trim();
        this.count = Integer.parseInt(itemOfOrder[1].trim());
        this.price = Integer.parseInt(itemOfOrder[2].trim());
    }
}