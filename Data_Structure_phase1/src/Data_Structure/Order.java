package Data_Structure;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Order {

    private int orderId;
    private Customer customer;
    private LinkedList<Product> products = new LinkedList<>();
    private LinkedList<Integer> quantities = new LinkedList<>();
    private Date orderDate;
    private double totalPrice;
    private String status; // Pending, Shipped, Delivered, Canceled

    // Constructor
    public Order(int orderId, Customer customer, Date date) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = date;
        this.status = "Pending";
        this.totalPrice = 0.0;
    } // end constructor

    // Getters/Setters
    public int getOrderId() { return orderId; } // end getOrderId
    public Customer getCustomer() { return customer; } // end getCustomer
    public Date getOrderDate() { return orderDate; } // end getOrderDate
    public LinkedList<Product> getProducts() { return products; } // end getProducts
    public double getTotalPrice() { return totalPrice; } // end getTotalPrice
    public String getStatus() { return status; } // end getStatus
    public void setStatus(String newStatus) { this.status = newStatus; } // end setStatus

    public void addProduct(Product p, int qty) {
        if (p == null || qty <= 0) return;

        products.insert(p);
        quantities.insert(qty);
        totalPrice += p.getPrice() * qty;
    } // end addProduct

    public void cancel() {
        if (!status.equals("Canceled")) {
            status = "Canceled";
            System.out.println("Order #" + orderId + " has been canceled.");
        } else {
            System.out.println("Order #" + orderId + " is already canceled.");
        } // end if-else
    } // end cancel

    public void updateStatus(String newStatus) {
        if (newStatus == null) return;
        status = newStatus;
        System.out.println("Order #" + orderId + " status updated to: " + status);
    } // end updateStatus

    public void printOrderDetails() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + (customer != null ? customer.getName() : "N/A"));
        System.out.println("Date: " + (orderDate != null ? df.format(orderDate) : "N/A"));
        System.out.println("Status: " + status);
        System.out.println("Items:");

        products.findFirst();
        quantities.findFirst();

        while (true) {
            Product p = products.retrieve();
            int qty = quantities.retrieve();

            System.out.println("  - " + p.getName() + " x" + qty + " = " + (p.getPrice() * qty));

            if (products.last() || quantities.last()) break;

            products.findNext();
            quantities.findNext();
        } // end while

        System.out.println("Total: " + totalPrice);
    } // end printOrderDetails


    public static Order searchById(LinkedList<Order> list, int targetId) {
        if (list.empty()) return null;

        list.findFirst();
        while (true) {
            Order o = list.retrieve();
            if (o.getOrderId() == targetId) return o;

            if (list.last()) break;
            list.findNext();
        } // end while
        return null;
    } // end searchById

    public static void printBetweenDates(LinkedList<Order> list, Date start, Date end) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Orders between " + df.format(start) + " and " + df.format(end));
        boolean any = false;

        if (list.empty()) {
            System.out.println("No orders found in this range.");
            return;
        } // end if

        list.findFirst();
        while (true) {
            Order o = list.retrieve();
            Date d = o.getOrderDate();

            if (!d.before(start) && !d.after(end)) {
                System.out.println(o);
                any = true;
            } // end if

            if (list.last()) break;
            list.findNext();
        } // end while

        if (!any) System.out.println("No orders found in this range.");
    } // end printBetweenDates

    public static boolean addProductToOrderById(LinkedList<Order> orders, LinkedList<Product> products,int orderId, int productId, int qty) {
       
        Order o = searchById(orders, orderId);
        if (o == null) return false;

        Product p = Product.searchById(products, productId);
        if (p == null) return false;

        o.addProduct(p, qty);
        return true;
    } // end addProductToOrderById

    

    public String toString() {
        return "Order{" +
                "id=" + orderId +
                ", customerId=" + (customer != null ? customer.getCustomerId() : "null") +
                ", date=" + orderDate +
                ", total=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    } // end toString
} // end Order class
