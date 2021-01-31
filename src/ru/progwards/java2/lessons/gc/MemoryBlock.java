package ru.progwards.java2.lessons.gc;

public class MemoryBlock {
    public int pointer;
    public int size;

    public MemoryBlock(int pointer, int length) {
        this.pointer = pointer;
        this.size = length;
    }
}
