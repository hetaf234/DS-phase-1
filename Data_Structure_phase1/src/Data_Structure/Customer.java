
package Data_Structure;

public class Customer{
private int customerId;
private String name;
private String email;
private LinkedList<Order> myOrders=new LinkedList<>();
private LinkedList<Review> myReviews=new LinkedList<>();
  
    
    
    public Customer(int customerId, String name, String email) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
	}//end of constructor

    
    
    public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public LinkedList<Order> getMyOrders() {
		return myOrders;
	}



	public void setMyOrders(LinkedList<Order> myOrders) {
		this.myOrders = myOrders;
	}



	public LinkedList<Review> getMyReviews() {
		return myReviews;
	}



	public void setMyReviews(LinkedList<Review> myReviews) {
		this.myReviews = myReviews;
	}


	public void placeOrder(Order order) {
		if(order!=null)
			myOrders.insert(order);
	}//placeOrder
	
	public void addReview(Review review) {
		if(review!=null)
			myReviews.insert(review);
	}//addReview
	
	public void viewOrderHistory() {
		if(myOrders.empty()) {
			System.out.println("No orders found for customer: "+name);
		return;
		}//end if
		System.out.println("Order history for :"+ name);
		myOrders.findFirst();
		while (!myOrders.last()) {
			Order o=myOrders.retrieve();
			System.out.println(o);
			myOrders.findNext();
		}//end while
		//print the last order 
		Order o=myOrders.retrieve();
		System.out.println(o);
		
	}//viewOrderHistory


public void printMyReviews() {
	if(myReviews.empty()) {
		System.out.println("No reviews found for customer: "+name);
	return;
	}//end if
	System.out.println("Reviews by :"+ name);
	myReviews.findFirst();
	while (!myReviews.last()) {
		Review r=myReviews.retrieve();
		System.out.println(r);
		myReviews.findNext();
	}//end while
	//print the last Review 
	Review r=myReviews.retrieve();
	System.out.println(r);
}//printMyReviews

   

    public static Customer searchById(LinkedList<Customer> list, int id) {
    	if(list.empty())
    		return null;
    	
    	list.findFirst();
    	while(!list.last()) {
    		Customer c=list.retrieve();
    		if(c.getCustomerId()==id)
    			return c;
    		list.findNext();
    	}//while
    	//last Customer
    	Customer c=list.retrieve();
		if(c.getCustomerId()==id)
			return c;
	
    	//not found 
		return null;
    	
    }//Customer searchById
    
    public static void printReviewsByCustomerId(LinkedList<Customer>list, int cid) {
    	Customer c= searchById(list,cid);
    	if (c==null) {
    		System.out.println("Customer Not Found");
    		return;
    	}// end if 
    		c.printMyReviews();
    }//printReviewsByCustomerId



	@Override
	public String toString() {
		return "Customer customerId=" + customerId + "\n name=" + name + "\n email=" + email ;
	}

    
    

   

    

   

    
   
} // end Customer class
