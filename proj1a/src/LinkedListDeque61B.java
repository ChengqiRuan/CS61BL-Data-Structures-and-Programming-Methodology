import java.util.Iterator;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size = 0;

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node curr;

        public LinkedListDequeIterator() {
            curr = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return curr != sentinel;
        }

        @Override
        public T next() {
            T item = curr.item;
            curr = curr.next;
            return item;
        }
    }

    public class Node {
        private Node prev;
        private T item;
        private Node next;


        public Node(Node prev, T item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    public LinkedListDeque61B() {
        this.sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node origin = this.sentinel.next;
        Node newNode = new Node(sentinel, x, sentinel.next);
        sentinel.next = newNode;
        origin.prev = newNode;
        this.size++;
    }

    @Override
    public void addLast(T x) {
        Node origin = sentinel.prev;
        Node newNode = new  Node(sentinel.prev, x, sentinel);
        origin.next = newNode;
        sentinel.prev = newNode;
        this.size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node curr = this.sentinel.next;
        while (curr != sentinel) {
            returnList.add(curr.item);
            curr = curr.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        Node curr = sentinel;
        if (curr.next == sentinel && curr.prev == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node firstNode = sentinel.next;
        T res = firstNode.item;
        sentinel.next = firstNode.next;
        sentinel.next.prev = sentinel;
        size--;
        return res;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node lastNode = sentinel.prev;
        T res = lastNode.item;
        sentinel.prev = lastNode.prev;
        sentinel.prev.next = sentinel;
        size--;
        return res;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        } else {
            Node curr = sentinel;
            for (int i = 0; i <= index; i++) {
                curr = curr.next;
            }
            return curr.item;
        }
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        return helper(sentinel.next, index);

    }
    public T helper(Node curr, int remain) {
        if (remain == 0) {
            return curr.item;
        }
        return helper(curr.next, remain - 1);
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
