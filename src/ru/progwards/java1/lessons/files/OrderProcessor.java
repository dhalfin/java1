package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class OrderProcessor {
    public String startPath;
    List<OrderItem> orderItemList;
    List<Order> orderList = new ArrayList<>();
    public int failedFile = 0;
    Instant fileDate;

    public OrderProcessor(String startPath) {
        this.startPath = startPath;
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {

        try {
            Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    OrderList(start, finish, shopId, path);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return failedFile;
    }

    private boolean checkOrderDate(LocalDate start, LocalDate finish, String shopId, Path path) throws IOException {
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        fileDate = Instant.parse(Files.getAttribute(path, "lastModifiedTime").toString());
        String str = "glob:**/???-??????-????.csv";
        if (shopId != null) {
            str = "glob:**/" + shopId + "-??????-????.csv";
        }
        PathMatcher pm = FileSystems.getDefault().getPathMatcher(str);
        if (pm.matches(path)) {
            if (start == null && fileDate.isBefore(
                    Instant.from(ZonedDateTime.of(finish, LocalTime.of(
                            23, 59, 59, 000000),
                            ZoneId.systemDefault())))) {
                return true;
            }
            if (finish == null && fileDate.isAfter(
                    Instant.from(ZonedDateTime.of(start, LocalTime.of(
                            0, 0, 0, 000000),
                            ZoneId.systemDefault())))) {
                return true;
            }
            if (fileDate.isAfter(Instant.from(ZonedDateTime.of(
                    start, LocalTime.of(0, 0, 0, 000000),
                    ZoneId.systemDefault()))) && fileDate.isBefore(Instant.from(ZonedDateTime.of(finish, LocalTime.of(
                    23, 59, 59, 000000), ZoneId.systemDefault())))) {
                return true;
            }
        }
        failedFile++;
        return false;
    }

    private List<Order> OrderList(LocalDate start, LocalDate finish, String shopId, Path pathOrderFile) throws IOException {
        if (checkOrderDate(start, finish, shopId, pathOrderFile)) {
            orderItemList = new ArrayList<>();
            List<String> good = new ArrayList<>(sortGoods(Files.readAllLines(pathOrderFile)));
            for (String str : good) {
                String[] itemOfOrder = str.split(",");
                orderItemList.add(new OrderItem(itemOfOrder));
            }
            orderList.add(new Order(pathOrderFile, orderItemList));
        }
        return orderList;
    }

    public List<String> sortGoods(List<String> goodsList) {
        ArrayList<String> goodsArrList = new ArrayList<>(goodsList);
        String str1;
        String str2;
        for (int j = 0; j < goodsArrList.size(); j++) {
            for (int i = j + 1; i < goodsArrList.size(); i++) {
                if (goodsArrList.get(j).compareTo(goodsArrList.get(i)) > 0) {
                    str1 = goodsArrList.get(j);
                    str2 = goodsArrList.get(i);
                    goodsArrList.remove(j);
                    goodsArrList.add(j, str2);
                    goodsArrList.remove(i);
                    goodsArrList.add(i, str1);
                }
            }
        }
        return goodsArrList;
    }

    public List<Order> sortOrders(List<Order> ordersToSort) {
        ArrayList<Order> ordersArrayList = new ArrayList<>();
        Order order;
        for (int j = 0; j < ordersToSort.size(); j++) {
            for (int i = j + 1; i < ordersToSort.size(); i++) {
                if (ordersToSort.get(j).datetime.isAfter((ordersToSort.get(i).datetime))) {
                    order = ordersToSort.get(j);
                    ordersToSort.add(j, ordersToSort.get(i));
                    ordersToSort.add(i, order);
                }
            }
        }
        return ordersToSort;
    }

    public List<Order> process(String shopId) {
        List<Order> ordersToSort = new ArrayList<>();
        for (Order order : orderList) {
            if (shopId != null && order.shopId == shopId) {
                ordersToSort.add(order);
                continue;
            }
            if (shopId != null && order.shopId != shopId) {
                continue;
            }
            ordersToSort.add(order);
        }
        return sortOrders(ordersToSort);
    }

    public Map<String, Double> statisticsByShop() {
        Map<String, Double> mapSales = new TreeMap<>();
        for (Order order : orderList) {
            if (mapSales.containsKey(order.shopId)) {
                mapSales.put(order.shopId, mapSales.get(order.shopId) + order.sum);
                continue;
            }
            mapSales.putIfAbsent(order.shopId, order.sum);
        }
        return mapSales;
    }

    public Map<String, Double> statisticsByGoods() {
        Map<String, Double> mapGoods = new TreeMap<>();
        for (Order order : orderList) {
            for (OrderItem item : order.items) {
                if (mapGoods.containsKey(item.googsName)) {
                    mapGoods.put(item.googsName, mapGoods.get(item.googsName) + item.count * item.price);
                    System.out.println(item.googsName + "   " + item.count * item.price);
                    continue;
                }
                mapGoods.putIfAbsent(item.googsName, item.price * item.count);
            }
        }
        return mapGoods;
    }

    public Map<LocalDate, Double> statisticsByDay() {
        Map<LocalDate, Double> salesByDay = new TreeMap<>();
        for (Order order : orderList) {
            if (salesByDay.containsKey(LocalDate.from(order.datetime))) {
                salesByDay.put(LocalDate.from(order.datetime), salesByDay.get(order.datetime) + order.sum);
                continue;
            }
            salesByDay.putIfAbsent(LocalDate.from(order.datetime), order.sum);
        }
        return salesByDay;
    }
}



