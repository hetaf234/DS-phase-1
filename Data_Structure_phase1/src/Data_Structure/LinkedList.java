package Data_Structure;



    public class LinkedList<T>{
    	private Node<T> head;
    	private Node<T> current;
    
    
    
    public LinkedList() {
    	head=current=null;
    }
    
    public boolean empty() {
    	return head==null;
    }
    
    public boolean full() {
    	return false;
    }

   public void findFirst() {
	   current=head;
   }
   public void findNext() {
	   current=current.next;
   }
   
   public boolean last() {
	   return current.next==null;
   }
   
    public T retrieve() {
    	return current.data;
    }

   public void update(T val) {
	   current.data=val;
   }
    
    public void insert(T val) {
    	
    	Node<T> tmp;
    	if(empty()) {
    		current=head=new Node<T>(val);
    	} else {
    		tmp=current.next;
    		current.next=new Node<T>(val);
    		current= current.next;
    		current.next=tmp;
    	}//else
    	
    	
    }//end of insert 
   
    public void remove() {
    	if(current==head)
    		head=head.next;
    	else {
    		Node<T> tmp=head;
    		while(tmp.next!=current)
    			tmp=tmp.next;
    		tmp.next=current.next;
    	}//else
    		
    }//end of remove 
   
   
    
    }//end of LinkedList class 