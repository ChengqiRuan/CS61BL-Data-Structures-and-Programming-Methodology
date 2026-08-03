package deque;

import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] array;
    private int front;
    private int back;
    private int size;
    public ArrayDeque61B() {
        array = (T[]) new Object[8];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            int realIdx = Math.floorMod(front + pos, array.length);
            T item = array[realIdx];
            pos += 1;
            return item;
        }
    }

    @Override
    public void addFirst(T x) {
        if (this.size() == array.length) {
            this.resizeUp();
        }
        front = Math.floorMod(front - 1, array.length);
        array[front] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (this.size() == array.length) {
            this.resizeUp();
        }
        array[back] = x;
        back = Math.floorMod(back + 1, array.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            int index = Math.floorMod(i + front, array.length);
            returnList.add(array[index]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T value = array[front];
        array[front] = null;
        front = Math.floorMod(front + 1, array.length);
        size--;
        this.resizeDown();
        return value;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        int index = Math.floorMod(back - 1, array.length);
        T value = array[index];
        array[index] = null;
        back = index;
        size--;
        this.resizeDown();
        return value;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        index = Math.floorMod(index + front, array.length);
        return array[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public void resizeUp() {
        int newLength = 2 * array.length;
        T[] newArray = (T[]) new Object[newLength];
        int oldCap = array.length;
        for (int i = 0; i < size; i++) {
            int srcIdx = Math.floorMod(front + i, oldCap);
            newArray[i] = array[srcIdx];
        }
        array = newArray;
        front = 0;
        back = size;
    }

    public void resizeDown() {
        int oldCap = array.length;
        if (oldCap == 8) {
            return;
        }
        if (size * 4 > oldCap) {
            return;
        }
        int newLength = oldCap / 2;
        T[] newArray = (T[]) new Object[newLength];
        for (int i = 0; i < size; i++) {
            int srcIdx = Math.floorMod(front + i, oldCap);
            newArray[i] = array[srcIdx];
        }
        array = newArray;
        front = 0;
        back = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque61B<?> otherDeque)) {
            return false;
        }
        if (this.size() != otherDeque.size()) {
            return false;
        }
        Iterator<T> thisIter = this.iterator();
        Iterator<?> otherIter = otherDeque.iterator();
        while (thisIter.hasNext()) {
            T a = thisIter.next();
            Object b = otherIter.next();
            if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String res = "[";
        Iterator<T> iter = iterator();
        boolean hasPrev = false;
        while (iter.hasNext()) {
            if (hasPrev) {
                res += ", ";
            }
            res += iter.next();
            hasPrev = true;
        }
        res += "]";
        return res;
    }
}
