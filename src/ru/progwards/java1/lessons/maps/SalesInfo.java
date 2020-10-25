package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SalesInfo {

    ArrayList<String[]> arrList = new ArrayList<>();
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
        Map<String, Double> goodsList = new TreeMap<>();
        for (int i = 0; i < arrList.size(); i++) {
            String[] str = arrList.get(i);
            if (goodsList.containsKey(arrList.get(i)[1])) {
                goodsList.put(str[1], goodsList.get(str[1]) + Double.parseDouble(str[3]));
            }
            goodsList.putIfAbsent(str[1], Double.parseDouble(str[3]));
        }
        return goodsList;
    }

    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> customersList = new TreeMap<>();
        for (int i = 0; i < arrList.size(); i++) {
            String[] str = arrList.get(i);
            if (customersList.containsKey(str[0])) {
                AbstractMap.SimpleEntry simpleEntry = customersList.get(str[0]);
                customersList.put(str[0], new AbstractMap.SimpleEntry<Double, Integer>((double) simpleEntry.getKey()
                        + Double.parseDouble(str[3]), ((int) simpleEntry.getValue() + Integer.parseInt(str[2]))));
            }
            customersList.putIfAbsent(str[0], new AbstractMap.SimpleEntry<>(Double.parseDouble(str[3]),
                    Integer.parseInt(str[2])));
        }
        return customersList;
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



