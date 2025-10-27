package Data_Structure;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;

    public LinkedList() {
        head = current = null;
    } // end constructor

    public boolean empty() {
        return head == null;
    } // end empty

    public boolean full() {
        return false; // linked list never full
    } // end full

    public void findFirst() {
        current = head;
    } // end findFirst

    public void findNext() {
        current = current.next;
    } // end findNext

    public boolean last() {
        return current.next == null;
    } // end last

    public T retrieve() {
        return current.data;
    } // end retrieve

    public void update(T val) {
        current.data = val;
    } // end update

    public void insert(T val) {
        Node<T> tmp;

        if (empty()) {
            current = head = new Node<T>(val);
        } else {
            tmp = current.next;
            current.next = new Node<T>(val);
            current = current.next;
            current.next = tmp;
        } // end else
    } // end insert

    public void remove() {
        if (current == head) {
            head = head.next;
        } else {
            Node<T> tmp = head;
            while (tmp.next != current) {
                tmp = tmp.next;
            } // end while
            tmp.next = current.next;
        } // end else

        if (current.next == null)
            current = head;
        else
            current = current.next;
    } // end remove

    
} // end LinkedList class
