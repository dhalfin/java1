package ru.progwards.java2.lessons.gc;

import java.util.*;

public class Heap {

    private int heapSize;
    private byte[] bytes;
    ArrayList<MemoryBlock> unusedBlocks;
    TreeMap<Integer, MemoryBlock> usedBlocks;

    public Heap(int maxHeapSize) {
        heapSize = maxHeapSize;
        bytes = new byte[maxHeapSize];
        unusedBlocks = new ArrayList<>();
        unusedBlocks.add(new MemoryBlock(0, maxHeapSize));
        usedBlocks = new TreeMap<>(Integer::compare);
    }

    public int malloc(int size) throws OutOfMemoryException {
        int ptr = distributeForOneBlock(size);
        if (ptr == -1) {
            compact();
            ptr = distributeForOneBlock(size);
            if (ptr == -1) {
                throw new OutOfMemoryException();
            }
        }
        return ptr;
    }

    private int distributeForOneBlock(int size) {
        int indOfMBlock = byteSearch(unusedBlocks, size);
        if (indOfMBlock == -1) {
            return -1;
        }

        MemoryBlock mBlock = unusedBlocks.get(indOfMBlock);
        if (mBlock.size == size) {
            usedBlocks.put(mBlock.pointer, mBlock);
            unusedBlocks.remove(indOfMBlock);
            return mBlock.pointer;
        }

        MemoryBlock used = new MemoryBlock(mBlock.pointer, size);
        usedBlocks.put(used.pointer, used);
        mBlock.pointer = mBlock.pointer + size;
        mBlock.size = mBlock.size - size;
        if (mBlock.size == 0) {
            unusedBlocks.remove(indOfMBlock);
        } else {
            unusedBlocks.sort(Comparator.comparingInt(block -> block.size));
        }
        return used.pointer;
    }

    private int byteSearch(List<MemoryBlock> list, int size) {
        int index = -1;
        int first = 0;
        int last = list.size() - 1;
        while (first <= last) {
            int mid = (first + last) / 2;
            MemoryBlock block = list.get(mid);
            if (block.size < size) {
                first = mid + 1;
            } else if (block.size > size) {
                last = mid - 1;
                index = mid;
            } else {
                index = mid;
                break;
            }
        }
        return index;
    }

    public void free(int ptr) throws InvalidPointerException {
        MemoryBlock block = usedBlocks.get(ptr);
        if (block == null) {
            throw new InvalidPointerException();
        }
        unusedBlocks.add(block);
        unusedBlocks.sort(Comparator.comparingInt(b -> b.size));
        usedBlocks.remove(ptr);
    }

    public void defrag() {
        ListIterator<MemoryBlock> listIterator = unusedBlocks.listIterator();
        MemoryBlock prevBlock = listIterator.next();
        MemoryBlock currBlock;
        while (listIterator.hasNext()) {
            currBlock = listIterator.next();
            if ((prevBlock.pointer + prevBlock.size) == currBlock.pointer) {
                prevBlock.size += currBlock.size;
                listIterator.remove();
            } else {
                prevBlock = currBlock;
            }
        }
    }

    public void compact() {
        if (usedBlocks.size() == 0) {
            unusedBlocks.clear();
            unusedBlocks.add(new MemoryBlock(0, heapSize));
            return;
        }
        MemoryBlock firstBlock = usedBlocks.firstEntry().getValue();
        int diff = firstBlock.pointer;
        if (diff > 0) {
            moveUsedBlock(firstBlock, diff);
        }
        if (usedBlocks.size() == 1) {
            addRemainedMemoryToFreeBlocksList();
            return;
        }

        MemoryBlock prevBlock = firstBlock;
        for (MemoryBlock currentBlock : usedBlocks.values()) {
            diff = currentBlock.pointer - (prevBlock.pointer + prevBlock.size);
            if (diff > 0) {
                moveUsedBlock(currentBlock, diff);
            }
            prevBlock = currentBlock;
        }
        addRemainedMemoryToFreeBlocksList();
    }

    private void moveUsedBlock(MemoryBlock block, int diff) {
        for (int i = block.pointer; i < (block.pointer + block.size); i++) {
            bytes[i - diff] = bytes[i];
        }
        block.pointer -= diff;
    }

    private void addRemainedMemoryToFreeBlocksList() {
        unusedBlocks.clear();
        MemoryBlock lastUsedBlock = usedBlocks.lastEntry().getValue();
        int ptr = lastUsedBlock.pointer + lastUsedBlock.size;
        unusedBlocks.add(new MemoryBlock(ptr, heapSize - ptr));
    }

    public void getBytes(int ptr, int size, byte[] bytes) {
        System.arraycopy(this.bytes, ptr, bytes, 0, size);
    }

    public void setBytes(int ptr, int size, byte[] bytes) {
        System.arraycopy(bytes, 0, this.bytes, ptr, size);
    }
}
