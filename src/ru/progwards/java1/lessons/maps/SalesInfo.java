package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SalesInfo {

    private static Map<String, Double> goodsList;
    private static Map<String, AbstractMap.SimpleEntry<Double, Integer>> customersList;

    public static int loadOrders(String fileName) {
        goodsList = new TreeMap<String, Double>();
        customersList = new TreeMap<String, AbstractMap.SimpleEntry<Double, Integer>>();
        int output = 0;
        try (FileReader r = new FileReader(fileName); Scanner sc = new Scanner(r)) {
            while (sc.hasNext()) {
                output += handleEntry(sc.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(fileName + ":\n" + e.getMessage());
        }
        return output;
    }


    private static int handleEntry(String str) {
        String[] entries = str.split(",");
        if (entries.length == 4) {
            try {
                String name = entries[0].trim();
                String item = entries[1].trim();
                int cnt = Integer.parseInt(entries[2].trim());
                double sum = Double.parseDouble(entries[3].trim());
                goodsList.put(item, goodsList.containsKey(item) ? goodsList.get(item) + sum : sum);
                if (customersList.containsKey(name)) {
                    AbstractMap.SimpleEntry<Double, Integer> interimList = customersList.get(name);
                    sum += interimList.getKey();
                    cnt += interimList.getValue();
                }
                customersList.put(name, new AbstractMap.SimpleEntry<Double, Integer>(sum, cnt));
                return 1;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return 0;
    }

    public static Map<String, Double> getGoods() {
        return goodsList;
    }

    public static Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        return customersList;
    }


}