package Data_Structure;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Order {

    // ------------------- Attributes -------------------
    private int orderId;
    private Customer customer;
    private LinkedList<Product> products = new LinkedList<>();
    private LinkedList<Integer> quantities = new LinkedList<>();
    private Date orderDate;
    private double totalPrice;
    private String status; // Pending, Shipped, Delivered, Canceled

    // ------------------- Constructor -------------------
    public Order(int orderId, Customer customer, Date date) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = date;
        this.status = "Pending";
        this.totalPrice = 0.0;
    } // end constructor

    // ------------------- Getters/Setters -------------------
    public int getOrderId() { return orderId; } // end getOrderId
    public Customer getCustomer() { return customer; } // end getCustomer
    public Date getOrderDate() { return orderDate; } // end getOrderDate
    public LinkedList<Product> getProducts() { return products; } // end getProducts
    public double getTotalPrice() { return totalPrice; } // end getTotalPrice
    public String getStatus() { return status; } // end getStatus
    public void setStatus(String newStatus) { this.status = newStatus; } // end setStatus

    // ------------------- Per-order Operations -------------------
    public void addProduct(Product p, int qty) {
        if (p == null || qty <= 0) return;
        products.add(p);
        quantities.add(qty);
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
        Node<Product> pNode = products.getHead();
        Node<Integer> qNode = quantities.getHead();
        while (pNode != null && qNode != null) {
            Product p = pNode.getData();
            int qty = qNode.getData();
            System.out.println("  - " + p.getName() + " x" + qty + " = " + (p.getPrice() * qty));
            pNode = pNode.getNext();
            qNode = qNode.getNext();
        } // end while
        System.out.println("Total: " + totalPrice);
    } // end printOrderDetails

    // ------------------- Static Operations on Lists (class-level) -------------------

    // Linear search by ID
    public static Order searchById(LinkedList<Order> list, int targetId) {
        Node<Order> cur = list.getHead();
        while (cur != null) {
            if (cur.getData().getOrderId() == targetId) { return cur.getData(); } // end if
            cur = cur.getNext();
        } // end while
        return null;
    } // end searchById

    // Print orders between two dates (inclusive)
    public static void printBetweenDates(LinkedList<Order> list, Date start, Date end) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Orders between " + df.format(start) + " and " + df.format(end));
        boolean any = false;
        Node<Order> cur = list.getHead();
        while (cur != null) {
            Order o = cur.getData();
            Date d = o.getOrderDate();
            if (!d.before(start) && !d.after(end)) {
                System.out.println(o);
                any = true;
            } // end if
            cur = cur.getNext();
        } // end while
        if (!any) { System.out.println("No orders found in this range."); } // end if
    } // end printBetweenDates

    // Add a product to an existing order by IDs (returns true if success)
    public static boolean addProductToOrderById(LinkedList<Order> orders,
                                                LinkedList<Product> products,
                                                int orderId, int productId, int qty) {
        Order o = searchById(orders, orderId);
        if (o == null) { return false; } // end if
        Product p = Product.searchById(products, productId);
        if (p == null) { return false; } // end if
        o.addProduct(p, qty);
        return true;
    } // end addProductToOrderById

    // Print all orders (quick overview)
    public static void printAll(LinkedList<Order> list) {
        System.out.println("Orders (" + list.size() + "):");
        Node<Order> o = list.getHead();
        while (o != null) {
            System.out.println(o.getData());
            o = o.getNext();
    } // end while
    } // end printAll

    // ------------------- Display -------------------
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
