package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.*;

public class Profiler {

    private static Deque<Long> sectionStartTimeDeque = new ArrayDeque<>();
    private static Deque<String> sectionNameDeque = new ArrayDeque<>();
    private static LinkedHashMap<String, StatisticInfo> sectionMap = new LinkedHashMap<>();

    public static void enterSection(String name) {
        sectionMap.putIfAbsent(name, new StatisticInfo(name));
        sectionStartTimeDeque.push(Instant.now().toEpochMilli());
        sectionNameDeque.push(name);
    }

    public static void exitSection(String name) {
        int period = (int) (Instant.now().toEpochMilli() - sectionStartTimeDeque.pop());
        sectionMap.get(name).count++;
        sectionMap.get(name).fullTime += period;
        sectionMap.get(name).selfTime += period;
        sectionNameDeque.pop();
        if (!sectionNameDeque.isEmpty()) {
            String externalSectionName = sectionNameDeque.peek();
            sectionMap.get(externalSectionName).selfTime -= period;
        }
    }

    public static List<StatisticInfo> getStatisticInfo() {
        Comparator<StatisticInfo> comparator = new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        };
        ArrayList<StatisticInfo> list = new ArrayList<>(sectionMap.values());
        list.sort(comparator);
        return list;
    }

    static class StatisticInfo {
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
}