package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAnalytics {

    private List<Shop> shops;
    private List<Product> products;


    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.products = products;
        this.shops = shops;
    }

    public Set<Product> existInAll() {
        Set<Product> result = new HashSet<>();
        result.addAll(products);
        for (Shop shop : shops) {
            result.retainAll(shop.getProducts());
        }
        return result;
    }

    public Set<Product> existAtListInOne() {
        Set<Product> result = new HashSet<>();
        for (Shop shop : shops) {
            result.addAll(shop.getProducts());
        }
        return result;
    }

    public Set<Product> notExistInShops() {
        Set<Product> result = new HashSet<>();
        Set<Product> productsList = new HashSet<>();
        productsList.addAll(products);
        for (Shop shop : shops) {
            result.addAll(shop.getProducts());
        }
        productsList.removeAll(result);
        return productsList;
    }

    public Set<Product> existOnlyInOne() {
        Set<Product> result = new HashSet<>();
        int cnt = 0;

        for (Product product : products) {
            for (Shop shop : shops) {
                if (shop.getProducts().contains(product)) {
                    cnt++;
                }
                if (cnt > 1) {
                    cnt = 0;
                    break;
                }
            }
            if (cnt == 1) {
                result.add(product);
                cnt = 0;
            }
        }
        return result;
    }

}
