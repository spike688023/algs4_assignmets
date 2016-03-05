/******************************************************************************
 *  Compilation:  javac LinkedStack.java
 *  Execution:    java LinkedStack < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A generic stack, implemented using a linked list. Each stack
 *  element is of type Item.
 *  
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java LinkedStack < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int N;          // size of the stack
    private Node first;     // top of stack
    private Node last;     // end of stack

    // helper linked list class
    private class Node {
        private Item item;
        private Node next = null;
        private Node prenext = null;
    }

    /**
     * Initializes an empty stack.
     */
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return N;
    }

    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Null pointer");
        //if (N == 0) Deque.Deque();
        Node tempfirst = first;
        first = new Node();
        first.item = item;
        first.next = tempfirst;
        if (first.next == null) last = first; 
        N++;
        if (N == 2) last.prenext = first;
        if (N > 2) tempfirst.prenext = first;
        //if (N >= 2) tempfirst.prenext = first;
    }

    /**
     * add the item to the end 
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Null pointer");
        //if (N == 0) Deque.Deque();
        Node templast = last;
        last = new Node();
        last.item = item;
        last.prenext = templast;
        last.next = null;
        if (last.prenext == null) first = last; 
        N++;
        if (N == 2) first.next = last;
        if (N > 2) templast.next = last;
        //if (N >= 2) templast.next = last;
    }

    /**
     * remove and return the item from the front 
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        if (first != null) first.prenext = null;            // delete first node
        N--;
        if (N == 0) last = first;
        return item;                   // return the saved item
    }

    /**
     * remove and return the item from the end 
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;        // save item to return
        last = last.prenext;            // delete first node
        if (last != null) last.next = null;            // delete first node
        N--;
        if (N == 0) first = last;
        return item;                   // return the saved item
    }


    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        //public ListIterator();
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException("Unsupport!!");  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Element");
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }



    /**
     * Unit tests the <tt>Deque</tt> data type.
     */
    public static void main(String[] args) {
        Deque<String> s = new Deque<String>();
      while (!StdIn.isEmpty()) {
          String item = StdIn.readString();
          if (!item.equals("-")) s.addFirst(item);
          else if (!s.isEmpty()) StdOut.print(s.removeLast() + " ");
      }
      StdOut.println("(" + s.size() + " left on queue )");
    //s.isEmpty();
    //s.isEmpty();
    //s.addFirst("2");
    //s.removeFirst();
    }
}

