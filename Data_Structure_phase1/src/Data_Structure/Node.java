package Data_Structure;

public class Node<T> {
    public T data;
    public Node<T> next;

    public Node() {
        data = null;
        next = null;
    } // end default constructor

    public Node(T val) {
        data = val;
        next = null;
    } // end parameterized constructor
} // end Node class
