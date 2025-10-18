package Data_Structure;
import java.io.Serializable;

public class LinkedList<T> implements Serializable {
    private Node<T> head, tail;
    private int size;

    public void add(T data) {
        Node<T> n = new Node<>(data);
        if (head == null) head = tail = n;
        else { tail.setNext(n); tail = n; }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.getNext();
        return cur.getData();
    }

    public void set(int index, T newData) {
        if (index < 0 || index >= size) return;
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.getNext();
        cur.setData(newData);
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size) return;
        if (index == 0) {
            head = head.getNext();
            if (head == null) tail = null;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index - 1; i++) prev = prev.getNext();
            Node<T> del = prev.getNext();
            prev.setNext(del.getNext());
            if (del == tail) tail = prev;
        }
        size--;
    }

    public boolean remove(T data) { // uses equals()
        Node<T> cur = head, prev = null;
        while (cur != null) {
            if ((data == null && cur.getData() == null) ||
                (data != null && data.equals(cur.getData()))) {
                if (prev == null) head = cur.getNext();
                else prev.setNext(cur.getNext());
                if (cur == tail) tail = prev;
                size--;
                return true;
            }
            prev = cur; cur = cur.getNext();
        }
        return false;
    }

    public void clear() { head = tail = null; size = 0; }
    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
    public Node<T> getHead() { return head; }

    public void printAll() {
        Node<T> cur = head;
        while (cur != null) { System.out.println(cur.getData()); cur = cur.getNext(); }
    }
}
