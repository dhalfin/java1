package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.util.*;

public class OrderProcessor {
    private Path catalog;
    private int amountOfFilesWithErr;
    private Set<Order> setOfOrders;

    public OrderProcessor(String startPath) {
        catalog = Paths.get(startPath);
        amountOfFilesWithErr = 0;
        setOfOrders = new HashSet<>();
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");
        try {
            Files.walkFileTree(catalog, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(path)) {
                        if (isValidNameFile(path.getFileName().toString())) {
                            addToSetOfOrders(path, start, finish, shopId);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path path, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return amountOfFilesWithErr;
    }

    public List<Order> process(String shopId) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : setOfOrders) {
            if (shopId == null)
                orderList.add(order);
            else if (order.shopId.equals(shopId))
                orderList.add(order);
        }

        Comparator<Order> comparator = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.datetime.compareTo(o2.datetime);
            }
        };

        orderList.sort(comparator);
        return orderList;
    }

    public Map<String, Double> statisticsByShop() {
        Map<String, Double> map = new TreeMap<>();
        for (Order order : setOfOrders) {
            if (map.containsKey(order.shopId)) {
                double sumByShop = map.get(order.shopId) + order.sum;
                map.put(order.shopId, sumByShop);
            }
            map.putIfAbsent(order.shopId, order.sum);
        }
        return map;
    }

    public Map<String, Double> statisticsByGoods() {
        Map<String, Double> map = new TreeMap<>();
        for (Order order : setOfOrders) {
            for (int i = 0; i < order.items.size(); i++) {
                String productName = order.items.get(i).googsName;
                double sumByGoods = order.items.get(i).price * order.items.get(i).count;
                if (map.containsKey(productName)) {
                    double newSumBuy = map.get(productName) + sumByGoods;
                    map.put(productName, newSumBuy);
                }
                map.putIfAbsent(productName, sumByGoods);
            }
        }
        return map;
    }

    public Map<LocalDate, Double> statisticsByDay() {
        Map<LocalDate, Double> map = new TreeMap<>();
        for (Order order : setOfOrders) {
            LocalDate localDate = LocalDate.from(order.datetime);
            if (map.containsKey(localDate)) {
                double sumByDay = map.get(localDate) + order.sum;
                map.put(localDate, sumByDay);
            }
            map.putIfAbsent(localDate, order.sum);
        }
        return map;
    }

    public void addToSetOfOrders(Path path, LocalDate start, LocalDate finish, String shopId) {
        try {
            String content = Files.readString(path);
            boolean notContainsError = !content.contains("Error");
            boolean isNotEmpty = !content.isEmpty();
            LocalDate localDate = LocalDate.from(getLocalDateTime(Files.getLastModifiedTime(path)));
            String fileName = path.getFileName().toString();
            String shopIdFile = getSubString(fileName, 0, 3);
            if (start == null && finish == null) {
                if (shopId == null) {
                    if (notContainsError && isNotEmpty)
                        setOfOrders.add(createOrder(path));
                    else {
                        ++amountOfFilesWithErr;
                        Files.writeString(path, "");
                    }
                } else if (shopIdFile.equals(shopId)) {
                    if (notContainsError && isNotEmpty)
                        setOfOrders.add(createOrder(path));
                    else {
                        ++amountOfFilesWithErr;
                        Files.writeString(path, "");
                    }
                }
            } else if (start == null) {
                if (localDate.isBefore(finish) || localDate.equals(finish)) {
                    if (shopId == null) {
                        if (notContainsError && isNotEmpty)
                            setOfOrders.add(createOrder(path));
                        else {
                            ++amountOfFilesWithErr;
                            Files.writeString(path, "");
                        }
                    } else if (shopIdFile.equals(shopId)) {
                        if (notContainsError && isNotEmpty)
                            setOfOrders.add(createOrder(path));
                        else {
                            ++amountOfFilesWithErr;
                            Files.writeString(path, "");
                        }
                    }
                }
            } else if (finish == null) {
                if (localDate.isAfter(start) || localDate.equals(start)) {
                    if (shopId == null) {
                        if (notContainsError && isNotEmpty)
                            setOfOrders.add(createOrder(path));
                        else {
                            ++amountOfFilesWithErr;
                            Files.writeString(path, "");
                        }
                    } else if (shopIdFile.equals(shopId)) {
                        if (notContainsError && isNotEmpty)
                            setOfOrders.add(createOrder(path));
                        else {
                            ++amountOfFilesWithErr;
                            Files.writeString(path, "");
                        }
                    }
                }
            } else if ((localDate.isAfter(start) && localDate.isBefore(finish)) ||
                    localDate.equals(start) || localDate.equals(finish)) {
                if (shopId == null) {
                    if (notContainsError && isNotEmpty)
                        setOfOrders.add(createOrder(path));
                    else {
                        ++amountOfFilesWithErr;
                        Files.writeString(path, "");
                    }
                } else if (shopIdFile.equals(shopId)) {
                    if (notContainsError && isNotEmpty)
                        setOfOrders.add(createOrder(path));
                    else {
                        ++amountOfFilesWithErr;
                        Files.writeString(path, "");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Order createOrder(Path path) {
        Order order = new Order();
        try {
            String fileName = path.getFileName().toString();
            List<String> stringList = Files.readAllLines(path);
            order.shopId = getSubString(fileName, 0, 3);
            order.orderId = getSubString(fileName, 4, 10);
            order.customerId = getSubString(fileName, 11, 15);
            order.datetime = getLocalDateTime(Files.getLastModifiedTime(path));
            order.items = generateListOrderItem(stringList);
            order.sum = getSumBuy(generateListOrderItem(stringList));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public boolean isValidNameFile(String str) {
        return str.matches("[A-Z0-9]{3}-[A-Z0-9]{6}-[0-9]{4}[.][a-z]{3}");
    }

    public OrderItem generateOrderItem(String str) {
        str = str.replace(", ", ",");
        String[] strArr = str.split(",");
        OrderItem orderItem = new OrderItem();
        orderItem.googsName = strArr[0];
        orderItem.count = Integer.parseInt(strArr[1]);
        orderItem.price = Double.parseDouble(strArr[2]);
        return orderItem;
    }

    public List<OrderItem> generateListOrderItem(List<String> list) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            orderItemList.add(generateOrderItem(list.get(i)));
        }
        Comparator<OrderItem> comparator = new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem o1, OrderItem o2) {
                return o1.googsName.compareTo(o2.googsName);
            }
        };
        orderItemList.sort(comparator);
        return orderItemList;
    }

    public double getSumBuy(List<OrderItem> orderItemList) {
        double result = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            int count = orderItemList.get(i).count;
            double price = orderItemList.get(i).price;
            result += count * price;
        }
        return result;
    }

    public String getSubString(String str, int fromInt, int toInt) {
        return str.substring(fromInt, toInt);
    }

    public LocalDateTime getLocalDateTime(FileTime fileTime) {
        Instant instant = Instant.ofEpochMilli(fileTime.toMillis());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }


}