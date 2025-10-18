package Data_Structure;

import java.io.Serializable;

public class Node<T> implements Serializable {
    private T data;
    Node<T> next;

    public Node(T data) {
    	this.data = data;
    	}
    
    public T getData() {
    	return data; 
    	}
    
    public Node<T> getNext() {
    	return next; 
    	}
    public void setData(T data) {
    this.data = data; 
    }
    
    public void setNext(Node<T> next){ 
    	this.next = next; 
    	}
    public String toString() {
    	return String.valueOf(data); 
    }
    
 
    
}
