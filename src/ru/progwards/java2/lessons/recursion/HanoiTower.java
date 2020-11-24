package ru.progwards.java2.lessons.recursion;

import java.util.*;

public class HanoiTower {
    private int size;
    private int pos;
    private int prongsQuantity;
    private List<Deque<String>> prongs;
    private boolean onTrace = false;

    public HanoiTower(int size, int pos) {
        this.size = size;
        this.pos = pos;
        prongsQuantity = 3;
        prongs = new ArrayList<>();

        for (int i = 0; i < prongsQuantity; i++) {
            prongs.add(new ArrayDeque<>());
            if (i == pos) {
                for (int j = size; j >= 1; j--)
                    prongs.get(i).push("<00" + j + ">");
            }
        }
    }

    public void move(int from, int to, int size) {
        if (size == 1) {
            shiftUpperRing(from, to);
            if (onTrace)
                print();
            return;
        }

        int supportingProng = supportingProng(from, to);
        move(from, supportingProng, size - 1);
        shiftUpperRing(from, to);

        if (onTrace)
            print();
        move(supportingProng, to, size - 1);
    }

    void print() {
        for (int i = 0; i < size; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < prongs.size(); j++) {
                String[] array = buildArray(prongs.get(j));
                stringBuilder.append(array[i] + " ");
            }
            System.out.println(stringBuilder.toString());
        }
        System.out.println("=================");
    }

    void setTrace(boolean on) {
        onTrace = on;
    }

    int supportingProng(int from, int to) {
        return 3 - from - to;
    }

    void shiftUpperRing(int from, int to) {
        String elem = prongs.get(from).pop();
        prongs.get(to).push(elem);
    }

    String[] buildArray(Deque<String> deque) {
        String[] array = new String[size];
        if (deque.isEmpty())
            Arrays.fill(array, "  I  ");
        else {
            int cnt = 0;
            int distinction = size - deque.size();
            for (int i = 0; i < array.length; i++) {
                ++cnt;
                if (cnt > distinction) {
                    for (String str : deque)
                        array[i++] = str;
                    break;
                } else
                    array[i] = "  I  ";
            }
        }
        return array;
    }

    public static void main(String[] args) {
        HanoiTower tower = new HanoiTower(3, 0);
        tower.print();
        tower.setTrace(true);
        tower.move(0, 1, 3);
    }
}
