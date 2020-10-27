package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.*;

public class Profiler {

    private static Deque<Long> partitionStartTimeDeque = new ArrayDeque<>();
    private static Deque<String> partitionNameDeque = new ArrayDeque<>();
    private static LinkedHashMap<String, StatisticInfo> partitionMap = new LinkedHashMap<>();

    public static void enterSection(String name) {
        partitionMap.putIfAbsent(name, new StatisticInfo(name));
        partitionStartTimeDeque.push(Instant.now().toEpochMilli());
        partitionNameDeque.push(name);
    }

    public static void exitSection(String name) {
        int period = (int) (Instant.now().toEpochMilli() - partitionStartTimeDeque.pop());
        partitionMap.get(name).count++;
        partitionMap.get(name).fullTime += period;
        partitionMap.get(name).selfTime += period;
        partitionNameDeque.pop();
        if (!partitionNameDeque.isEmpty()) {
            String outerPartitionName = partitionNameDeque.peek();
            partitionMap.get(outerPartitionName).selfTime -= period;
        }
    }

    public static List<StatisticInfo> getStatisticInfo() {
        Comparator<StatisticInfo> comparator = new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        };
        ArrayList<StatisticInfo> list = new ArrayList<>(partitionMap.values());
        list.sort(comparator);
        return list;
    }
}

class StatisticInfo {
    public String sectionName;
    public int fullTime;
    public int selfTime;
    int count;

    StatisticInfo(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public String toString() {
        return "StatisticInfo{" +
                "sectionName='" + sectionName + '\'' +
                ", fullTime=" + fullTime +
                ", selfTime=" + selfTime +
                ", count=" + count +
                '}';
    }
}