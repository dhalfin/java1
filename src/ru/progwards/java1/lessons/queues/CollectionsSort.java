package ru.progwards.java1.lessons.queues;

import java.util.*;

public class CollectionsSort {
    //static final int ELEM_COUNT = 5_000_000;

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
    }

    public static Collection<String> compareSort() {
        TreeSet<Sorted> sortedMethods = new TreeSet<>();
//        Random rnd = new Random();
//        List<Integer> list = new ArrayList<Integer>();
//        for (int i = 0; i < ELEM_COUNT; i++) {
//            list.add(rnd.nextInt());
//        }

        Collection<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10_000; i++) {
            list.add(i);
        }

        long timeMySort = System.currentTimeMillis();
        mySort(list);
        timeMySort = System.currentTimeMillis() - timeMySort;
        sortedMethods.add(new Sorted("mySort", timeMySort));

        long timeMinSort = System.currentTimeMillis();
        minSort(list);
        timeMinSort = System.currentTimeMillis() - timeMinSort;
        sortedMethods.add(new Sorted("minSort", timeMinSort));

        long timeCollSort = System.currentTimeMillis();
        collSort(list);
        timeCollSort = System.currentTimeMillis() - timeCollSort;
        sortedMethods.add(new Sorted("collSort", timeCollSort));

        ArrayList<String> result = new ArrayList(sortedMethods);

        System.out.println("myTime = " + timeMySort);
        System.out.println("MinTime = " + timeMinSort);
        System.out.println("CollSort = " + timeCollSort);
        return result;
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
            if (this.time == o.time) {
                return this.name.compareTo(o.name);
            }
            return Long.compare(this.time, o.time);
        }
    }

    public static void main(String[] args) {
        System.out.println(compareSort());
    }
}
