package ru.progwards.java2.lessons.recursion;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GoodsWithLambda {
    private List<Goods> goodsList;

    public GoodsWithLambda() {
        goodsList = new ArrayList<>();
    }

    void setGoods(List<Goods> list) {
        Instant date = Instant.now();
        Collections.addAll(list,
                new Goods("Halava", "05M3818R0102DE", 33, 200.57,
                        date.plus(Duration.ofDays(10))),
                new Goods("Chapati", "34K3783R0192AU", 10, 39.99,
                        date.plus(Duration.ofDays(23))),
                new Goods("Gulab Jamun", "14P3808R0102RU", 17, 99.97,
                        date.plus(Duration.ofDays(30))),
                new Goods("Kichiri", "28D3817R9102US", 15, 234.15,
                        date.plus(Duration.ofDays(7))),
                new Goods("Bread Ladoo", "12I3828R0102EU", 85, 67.99,
                        date.plus(Duration.ofDays(29))),
                new Goods("Beetroot Semolina", "33O3418R010RU", 57, 268.2,
                        date.plus(Duration.ofDays(25))),
                new Goods("Sombi (Coconut Rice Pudding)", "97N3458R910DE", 12, 55.95,
                        date.plus(Duration.ofDays(19)))
        );
    }

    List<Goods> sortByName() {
        return goodsList.stream().sorted(Comparator.comparing(o -> o.name)).collect(Collectors.toList());
    }

    List<Goods> sortByNumber() {
        return goodsList.stream()
                .sorted(Comparator.comparing(o -> o.number.toLowerCase())).collect(Collectors.toList());
    }

    List<Goods> sortByPartNumber() {
        return goodsList.stream()
                .sorted(Comparator.comparing(o -> o.number.toLowerCase().substring(0, 3)))
                .collect(Collectors.toList());
    }

    List<Goods> sortByAvailabilityAndNumber() {
        return goodsList.stream()
                .sorted(Comparator.comparing(o -> o.number.toLowerCase()))
                .sorted(Comparator.comparing(o -> o.available)).collect(Collectors.toList());
    }

    List<Goods> expiredAfter(Instant date) {
        return goodsList.stream()
                .sorted(Comparator.comparing(o -> o.expired)).takeWhile(o -> o.expired.isBefore(date))
                .collect(Collectors.toList());
    }

    List<Goods> сountLess(int count) {
        return goodsList.stream()
                .sorted(Comparator.comparing(o -> o.available)).takeWhile(o -> o.available < count)
                .collect(Collectors.toList());

    }

    List<Goods> сountBetween(int count1, int count2) {
        return goodsList.stream()
                .sorted(Comparator.comparingInt(o -> o.available))
                .filter(o -> count1 < o.available && o.available < count2)
                .collect(Collectors.toList());
    }

}