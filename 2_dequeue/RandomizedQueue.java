import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int ind = 0;
    private int capacity = 2;


    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return ind == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return ind;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // increase size of an array when it
        if (ind >= capacity) {
            capacity *= 2;
            resize();
        }

        s[ind] = item;
        ind++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        Item return_item = null;
        int index = StdRandom.uniform(ind);
        return_item = s[index];
        s[index] = s[ind - 1];
        s[ind - 1] = null;
        ind--;

        if (ind < capacity * 0.25) {
            capacity *= 0.5;
            resize();
        }

        return return_item;
    }

    private void resize() {
        Item[] s_old = s;
        s = (Item[]) new Object[capacity];
        int ind_2 = 0;
//        StdOut.println("----");
        for (int i = 0; i < ind; i++) {

//            StdOut.println(s_old[i]);

            s[ind_2] = s_old[i];
            ind_2++;
        }
        ind = ind_2;
//        StdOut.println("----");

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        Item return_item = null;
        while (return_item == null) {
            int index = StdRandom.uniform(ind);
            return_item = s[index];
        }

        return return_item;
    }

    // IMPLEMENT A SWAPING METHOD SUCH THAT YOU
    // DON'T HAVE ANT NULL SPOTS AFTER REMOVING AN ITEM FROM AN INDEX 'I'


    private class RandomIterator implements Iterator<Item> {

        private Item[] sortedArray;
        private int index;

        public RandomIterator() {
            index = 0;
            sortedArray = (Item[]) new Object[ind];
            for (int i = 0; i < ind; i++) {
                sortedArray[i] = s[i];
            }

            StdRandom.shuffle(sortedArray);
        }

        public boolean hasNext() {
            return index < ind;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return sortedArray[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();

        // TEST - ENQUEUE & ITERATOR
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        for (int s : test) {
            StdOut.println(s);
        }
        StdOut.println("$$$$");

        // TEST - DEQUE
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println("----");
        for (int s : test) {
            StdOut.println(s);
        }
        StdOut.println("%%%%%%%");


        // TEST - IS EMPTY
        StdOut.println(test.isEmpty());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.isEmpty());
        StdOut.println("$$$$");


        // TEST - SAMPLE
        for (int s : test) {
            StdOut.println(s);
        }
        StdOut.println("----");
        test.enqueue(1);
        test.enqueue(2);
        StdOut.println(test.sample());
        StdOut.println(test.sample());
        StdOut.println(test.sample());
        StdOut.println("----");
        for (int s : test) {
            StdOut.println(s);
        }
        StdOut.println("$$$$");
    }


}

