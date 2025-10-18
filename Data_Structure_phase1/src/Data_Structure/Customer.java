package Data_Structure;

import java.util.Date;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Order> myOrders = new LinkedList<>();
    private LinkedList<Review> myReviews = new LinkedList<>();

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public LinkedList<Order> getMyOrders() { return myOrders; }
    public LinkedList<Review> getMyReviews() { return myReviews; }

    public void placeOrder(Order order) {
        if (order != null) myOrders.add(order);
    }

    public void addReview(Review review) {
        if (review != null) myReviews.add(review);
    }

    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name='" + name + "', email='" + email + "'}";
    }
}
