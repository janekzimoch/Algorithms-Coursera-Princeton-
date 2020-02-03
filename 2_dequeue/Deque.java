import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    private class Node {
        Item value;
        Node parent;
        Node next;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.value = item;
        first.next = oldFirst;
        first.parent = null;
        size++;

        if (last == null) last = first;  // special case when empty
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.value = item;
        last.next = null;

        oldLast.next = last;
        last.parent = oldLast;
        size++;


        if (first == null) first = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item removed_value = first.value;
        first = first.next;
        size--;

        return removed_value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item removed_value = last.value;
        last = last.parent;
        last.next = null;
        size--;

        return removed_value;
    }

    private class FrontToBackIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item value = current.value;
            current = current.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new FrontToBackIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        StdOut.println(test.isEmpty());

        test.addFirst(4);
        test.addFirst(3);
        test.addLast(6);
        test.addLast(7);
        test.addLast(8);
        test.addLast(9);
        test.addLast(10);
        StdOut.println(test.removeFirst());
        StdOut.println(test.removeLast());
        StdOut.println("----");
        for (int s : test) {
            StdOut.println(s);
        }
        StdOut.println(test.size());
        StdOut.println(test.isEmpty());
    }
}
