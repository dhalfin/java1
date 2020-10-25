package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SalesInfo {

    static Map<Integer, Purchase> ordersMap = new TreeMap<>();

    private static class Purchase {
        private String name;
        private String item;
        private int quantity;
        private double sum;

        Purchase(String name, String item, int quantity, double sum) {
            this.name = name;
            this.item = item;
            this.quantity = quantity;
            this.sum = sum;
        }

        public String getName() {
            return name;
        }

        public String getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getSum() {
            return sum;
        }


        public int loadOrders(String fileName) {
            try (FileReader reader = new FileReader(fileName)) {
                int cnt = 0;
                Scanner sc = new Scanner(reader);
                while (sc.hasNextLine()) {
                    String str = sc.nextLine();
                    str = str.replace(", ", ",");
                    String[] strArray = str.split(",");
                    if (strArray.length == 4 &&
                            isDigit(strArray[2]) &&
                            isDigit(strArray[3])) {
                        cnt++;
                        ordersMap.put(cnt, new Purchase(strArray[0], strArray[1],
                                Integer.valueOf(strArray[2]), Double.valueOf(strArray[3])));
                    }
                }
                return cnt;
            } catch (IOException e) {
                System.out.println(e);
                return 0;
            }
        }

        public Map<String, Double> getGoods() {
            Map<String, Double> treeMap = new TreeMap<>();
            for (Map.Entry<Integer, Purchase> entry : ordersMap.entrySet()) {
                String goods = entry.getValue().getItem();
                double sum = entry.getValue().getSum();
                if (treeMap.containsKey(goods)) {
                    sum += treeMap.get(goods);
                }
                treeMap.put(goods, sum);
            }
            return treeMap;
        }

        public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
            Map<String, AbstractMap.SimpleEntry<Double, Integer>> treeMap = new TreeMap<>();
            for (Map.Entry<Integer, Purchase> entry : ordersMap.entrySet()) {
                String name = entry.getValue().getName();
                double sum = entry.getValue().getSum();
                int quantity = entry.getValue().getQuantity();
                if (treeMap.containsKey(name)) {
                    sum += treeMap.get(name).getKey();
                    quantity += treeMap.get(name).getValue();
                }
                treeMap.put(name, new AbstractMap.SimpleEntry<>(sum, quantity));
            }
            return treeMap;
        }

        private boolean isDigit(String str) {
            StringBuilder newStr = new StringBuilder();
            for (char ch : str.toCharArray()) {
                if (!Character.isAlphabetic(ch)) {
                    newStr.append(ch);
                }
            }
            return str.length() == newStr.length();
        }
    }
}


