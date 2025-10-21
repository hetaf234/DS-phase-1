package Data_Structure;

public class Customer {

    // ------------------- Attributes -------------------
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Order> myOrders = new LinkedList<>();
    private LinkedList<Review> myReviews = new LinkedList<>();

    // ------------------- Constructor -------------------
    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    } // end constructor

    // ------------------- Getters/Setters -------------------
    public int getCustomerId() { return customerId; } // end getCustomerId
    public String getName() { return name; } // end getName
    public String getEmail() { return email; } // end getEmail
    public LinkedList<Order> getMyOrders() { return myOrders; } // end getMyOrders
    public LinkedList<Review> getMyReviews() { return myReviews; } // end getMyReviews
    public void setName(String newName) { this.name = newName; } // end setName
    public void setEmail(String newEmail) { this.email = newEmail; } // end setEmail

    // ------------------- Per-customer Operations -------------------
    public void placeOrder(Order order) {
        if (order != null) { myOrders.add(order); } // end if
    } // end placeOrder

    public void addReview(Review review) {
        if (review != null) { myReviews.add(review); } // end if
    } // end addReview

    public void viewOrderHistory() {
        if (myOrders.isEmpty()) {
            System.out.println("No orders found for customer: " + name);
            return;
        } // end if
        System.out.println("Order history for " + name + ":");
        Node<Order> cur = myOrders.getHead();
        while (cur != null) {
            System.out.println(cur.getData());
            cur = cur.getNext();
        } // end while
    } // end viewOrderHistory

    public void printMyReviews() {
        if (myReviews.isEmpty()) {
            System.out.println("No reviews by " + name);
            return;
        } // end if
        System.out.println("Reviews by " + name + ":");
        Node<Review> cur = myReviews.getHead();
        while (cur != null) {
            Review r = cur.getData();
            System.out.println("  Product#" + r.getProductId() +
                    " rating=" + r.getRating() +
                    " comment='" + r.getComment() + "'");
            cur = cur.getNext();
        } // end while
    } // end printMyReviews

    // ------------------- Static Operations on Lists (class-level) -------------------

    // Linear search by ID
    public static Customer searchById(LinkedList<Customer> list, int id) {
        Node<Customer> cur = list.getHead();
        while (cur != null) {
            if (cur.getData().getCustomerId() == id) { return cur.getData(); } // end if
            cur = cur.getNext();
        } // end while
        return null;
    } // end searchById

    // Linear search by Name
    public static Customer searchByName(LinkedList<Customer> list, String targetName) {
        Node<Customer> cur = list.getHead();
        while (cur != null) {
            if (cur.getData().getName().equalsIgnoreCase(targetName)) { return cur.getData(); } // end if
            cur = cur.getNext();
        } // end while
        return null;
    } // end searchByName

    // Print all orders for a given customer ID (shortcut for “extractReviewsByCustomer” style)
    public static void printReviewsByCustomerId(LinkedList<Customer> list, int cid) {
        Customer c = searchById(list, cid);
        if (c == null) { System.out.println("Customer not found"); return; } // end if
        c.printMyReviews();
    } // end printReviewsByCustomerId

    // Remove a customer by ID (clears their references), returns true if removed
    public static boolean removeById(LinkedList<Customer> list, int cid) {
        Customer c = searchById(list, cid);
        if (c == null) { return false; } // end if
        // clear reviews and orders lists (aggregation cleanup)
        Node<Review> rv = c.getMyReviews().getHead();
        while (rv != null) { c.getMyReviews().remove(rv.getData()); rv = c.getMyReviews().getHead(); } // end while
        Node<Order> od = c.getMyOrders().getHead();
        while (od != null) { c.getMyOrders().remove(od.getData()); od = c.getMyOrders().getHead(); } // end while
        list.remove(c);
        return true;
    } // end removeById

    // Print all customers (quick overview)
    public static void printAll(LinkedList<Customer> list) {
        System.out.println("Customers (" + list.size() + "):");
        Node<Customer> c = list.getHead();
        while (c != null) {
            Customer cc = c.getData();
            System.out.println(cc.getCustomerId() + " - " + cc.getName() + " " + cc.getEmail());
            c = c.getNext();
        } // end while
    } // end printAll

    // ------------------- Display -------------------
    public String toString() {
        return "Customer{id=" + customerId +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", orders=" + myOrders.size() + '}';
    } // end toString
} // end Customer class
