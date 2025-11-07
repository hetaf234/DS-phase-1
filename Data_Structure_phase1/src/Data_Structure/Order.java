
package Data_Structure;
import java.util.Date;
import java.text.SimpleDateFormat;



public class Order{
    
 
   

    private int orderId;
    private Customer customer;
    private LinkedList<Product>products=new LinkedList<>();
  
    private Date orderDate;
    private double totalPrice;
    private String status;
    
    public Order(int orderId, Customer customer, Date orderDate) {
		
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		status="Pending"; //default value when creating an order
		totalPrice=0;
	}//constructor

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LinkedList<Product> getProducts() {
		return products;
	}

	public void setProducts(LinkedList<Product> products) {
		this.products = products;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public void addProduct(Product p) {
		if(p==null) return;
		products.insert(p);
		totalPrice+=p.getPrice();
	}//addProduct
	
    public void cancel() {
    	if(status.equals("Canceled")) 
    		System.out.println("This orderd "+orderId+"  is already cancelled.");
    	else {
    		status="Canceled";
    		System.out.println("This order "+orderId+" has been canceled successfully.");
    	}//else
    		
    }//cancel
    
    public void updateStatus(String newStatus) {
    	if(newStatus==null) return;
    	status=newStatus;
    	System.out.println("this order "+orderId+" status has been updated to:"+status);
    }//updateStatus
    
    public static Order searchById(LinkedList<Order>list, int targetId) {
    	if (list.empty())
    		return null;
    	list.findFirst();
    	while(!list.last()) {
    		Order o=list.retrieve();
    		if(o.getOrderId()==targetId)
    			return o;
    		list.findNext();
    	}//end while
    	Order o=list.retrieve();
		if(o.getOrderId()==targetId)
			return o;
		else
			return null;
    }//searchById
    
  public static void printBetweenDates(LinkedList<Order>list,Date start,Date end) {
	  SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	  System.out.println("Orderd between :"+df.format(start)+" and :" +df.format(end));
	  boolean any=false;
	  if(list.empty()) {
		  System.out.println("No order between those dates");
		  return;
	  }//if
	  list.findFirst();
	  while (!list.last()) {
		  Order o=list.retrieve();
		  Date d=o.getOrderDate();
		  
		  if(!d.before(start)&&!d.after(end)) {
			  System.out.println(o);
			  any=true;
		  }//end if (order found)
		  list.findNext();
	  }//while
	  //last order in the list 
	  Order o=list.retrieve();
	  Date d=o.getOrderDate();
	  
	  if(!d.before(start)&&!d.after(end)) {
		  System.out.println(o);
		  any=true;
	  }//end if (order found)
	  if(!any)
		  System.out.println("No order between those dates");
	  
	  
  }//printBetweenDates

  public static boolean addProductToOrderById(LinkedList<Order> orders, LinkedList<Product>products,int orderId,int productId) {
	  Order o=searchById(orders,orderId);
	  if (o==null) return false;
	  
	  Product p=Product.searchById(products,productId);
	  if(p==null) return false;
	  
	  o.addProduct(p);
	  return true;
  }//addProductToOrderById

  @Override
  public String toString() {
	  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	  
	return "orderId=" + orderId + "\n customer=" + customer + "\n products=" + products + "\n orderDate="
			+ df.format(orderDate) + "\n totalPrice=" + totalPrice + "\n status=" + status ;
  }//tostring

   

  
    
    

    
} // end Order class
