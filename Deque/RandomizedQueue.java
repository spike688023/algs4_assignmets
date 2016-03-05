/*************************************************************************
 *  Compilation:  javac ResizingArrayStack.java
 *  Execution:    java ResizingArrayStack < input.txt
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt
 *  
 *  Stack implementation with a resizing array.
 *
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java ResizingArrayStack < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N = 0;        // number of elements on stack

    // create an empty stack
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }

    public boolean isEmpty() { return N == 0; }
    public int size()        { return N;      }



    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }

    // push a new item onto the stack
    public void enqueue(Item item) {
        if (N == a.length) resize(2*a.length);    // double size of array if necessary
        if (item == null) throw new NullPointerException("Null pointer");
        a[N++] = item;                            // add item
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int index = StdRandom.uniform(N); 
        return a[index];
    }

    // remove and return a random item 
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int index = StdRandom.uniform(N); 
        //StdRandom.shuffle(a);
        //Item item = a[index];
        //Item temp = (Item[]) new Object[1];
        //temp = a[N-1];
        Item item = a[index];
        a[index] = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
      //Item item = a[N-1];
      //a[N-1] = null;                              // to avoid loitering
      //N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }


    public Iterator<Item> iterator()  { return new LIFOIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class LIFOIterator implements Iterator<Item> {
        private int i = N;
        private int[] arr = new int[N];
        public LIFOIterator() {
            for (int j = 0; j < N; j++) arr[j] = j;
            StdRandom.shuffle(arr);
        }
        //StdRandom.shuffle(a);
        public boolean hasNext()  { return i > 0;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[arr[--i]];
        }
    }



   /***********************************************************************
    * Test routine.
    **********************************************************************/
    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.enqueue(item);
            else if (!s.isEmpty()) StdOut.print(s.dequeue() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
    }
}
