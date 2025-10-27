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
        if (order != null) { myOrders.insert(order); } // end if
    } // end placeOrder

    public void addReview(Review review) {
        if (review != null) { myReviews.insert(review); } // end if
    } // end addReview

    public void viewOrderHistory() {
        if (myOrders.empty()) {
            System.out.println("No orders found for customer: " + name);
            return;
        } // end if

        System.out.println("Order history for " + name + ":");

        myOrders.findFirst();
        while (true) {
            Order o = myOrders.retrieve();
            System.out.println(o);

            if (myOrders.last()) break;
            myOrders.findNext();
        } // end while
    } // end viewOrderHistory

    public void printMyReviews() {
        if (myReviews.empty()) {
            System.out.println("No reviews by " + name);
            return;
        } // end if

        System.out.println("Reviews by " + name + ":");

        myReviews.findFirst();
        while (true) {
            Review r = myReviews.retrieve();
            System.out.println("  Product#" + r.getProductId() +
                    " rating=" + r.getRating() +
                    " comment='" + r.getComment() + "'");

            if (myReviews.last()) break;
            myReviews.findNext();
        } // end while
    } // end printMyReviews

    // ------------------- Static Operations on Lists (class-level) -------------------

    public static Customer searchById(LinkedList<Customer> list, int id) {
        if (list.empty()) return null;

        list.findFirst();
        while (true) {
            Customer c = list.retrieve();
            if (c.getCustomerId() == id) return c;

            if (list.last()) break;
            list.findNext();
        } // end while
        return null;
    } // end searchById

    public static Customer searchByName(LinkedList<Customer> list, String targetName) {
        if (list.empty()) return null;

        list.findFirst();
        while (true) {
            Customer c = list.retrieve();
            if (c.getName().equalsIgnoreCase(targetName)) return c;

            if (list.last()) break;
            list.findNext();
        } // end while
        return null;
    } // end searchByName

    public static void printReviewsByCustomerId(LinkedList<Customer> list, int cid) {
        Customer c = searchById(list, cid);
        if (c == null) {
            System.out.println("Customer not found");
            return;
        } // end if
        c.printMyReviews();
    } // end printReviewsByCustomerId

    public static boolean removeById(LinkedList<Customer> list, int cid) {
        Customer c = searchById(list, cid);
        if (c == null) return false;

        // clear reviews (aggregation cleanup)
        while (!c.getMyReviews().empty()) {
            c.getMyReviews().findFirst();
            c.getMyReviews().remove();
        } // end while

        // clear orders
        while (!c.getMyOrders().empty()) {
            c.getMyOrders().findFirst();
            c.getMyOrders().remove();
        } // end while

        list.findFirst();
        while (true) {
            Customer cur = list.retrieve();
            if (cur == c) {
                list.remove();
                return true;
            } // end if
            if (list.last()) break;
            list.findNext();
        } // end while
        return false;
    } // end removeById

   

    public String toString() {
        return "Customer{id=" + customerId +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' + "}";
    } // end toString
} // end Customer class
