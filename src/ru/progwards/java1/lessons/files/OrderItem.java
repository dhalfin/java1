package ru.progwards.java1.lessons.files;

public class OrderItem {
    public String googsName;
    public int count;
    public double price;

    @Override
    public String toString() {
        return "OrderItem{" +
                "googsName='" + googsName + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}