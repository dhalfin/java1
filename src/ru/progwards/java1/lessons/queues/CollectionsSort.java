package ru.progwards.java1.lessons.queues;

import java.util.*;

public class CollectionsSort {
    public static void mySort(Collection<Integer> data) {
        List<Integer> list = new ArrayList<>(data);

        for (int a = 0; a < list.size(); a++) {
            for (int b = 0; b < list.size(); b++) {
                if (list.get(b) > list.get(a)) {
                    Collections.swap(list, a, b);
                }
            }
        }
        data.clear();
        data.addAll(list);
    }

    public static void minSort(Collection<Integer> data) {
        ArrayDeque<Integer> arrDeque = new ArrayDeque<>();
        while (!data.isEmpty()) {
            arrDeque.add(Collections.min(data));
            data.remove(Collections.min(data));
        }
        data.addAll(arrDeque);
    }

    static void collSort(Collection<Integer> data) {
        ArrayList<Integer> list = new ArrayList<>(data);
        Collections.sort(list);
        data.clear();
        data.addAll(list);
    }

    public static Collection<Integer> createList() {
        Random rnd = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 10_000; i >= 0; i--) {
            list.add(rnd.nextInt());
        }
        return list;
    }

    public static Collection<String> getListNamesMethods(Collection<Sorted> data) {
        List<String> listNameMethod = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            listNameMethod.add(((ArrayList<Sorted>) data).get(i).name);
        }
        return listNameMethod;
    }

    public static Collection<String> compareSort() {
        long start = System.currentTimeMillis();
        mySort(createList());
        long end1 = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        minSort(createList());
        long end2 = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        collSort(createList());
        long end3 = System.currentTimeMillis() - start;

        List<Sorted> listMethod = new ArrayList<>(List.of(
                new Sorted("mySort", end1),
                new Sorted("minSort", end2),
                new Sorted("collSort", end3)));
        Collections.sort(listMethod);
        return getListNamesMethods(listMethod);
    }

    public static class Sorted implements Comparable<Sorted> {
        long time;
        String name;

        public Sorted(String name, long time) {
            this.time = time;
            this.name = name;
        }

        @Override
        public int compareTo(Sorted o) {
            int compareOutput = Long.compare(time, o.time);
            if (compareOutput != 0)
                return compareOutput;
            return name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        System.out.println(compareSort());
    }
}
