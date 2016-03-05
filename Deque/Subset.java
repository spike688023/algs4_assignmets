import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        //StdOut.println("Input = " + args[0]);
        int count = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            s.enqueue(item);
            //if (s.size() == count)break;
        }
        while (count > 0) {
            if (!s.isEmpty()) StdOut.println(s.dequeue());
            count--;
        }
    }
}
