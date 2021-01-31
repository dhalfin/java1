package ru.progwards.java2.lessons.gc;

import com.google.common.collect.TreeMultimap;

import java.util.Map;
import java.util.TreeMap;

public class Heap {
    private byte[] bytes;

    private TreeMultimap<Integer, Integer> freeBlocks;
    private TreeMap<Integer, Integer> busyBlocks;
    private Map.Entry<Integer, Integer> current;
    private int quantityCompact;

    Heap(int maxHeapSize) {
        freeBlocks = TreeMultimap.create();
        busyBlocks = new TreeMap<>();
        bytes = new byte[maxHeapSize];
        freeBlocks.put(bytes.length, 0);
        quantityCompact = 0;
    }
}
