package Data_Structure;

import java.util.Date;

public class Order {
    private int orderId;
    private Customer customer;
    private LinkedList<Product> products = new LinkedList<>();
    private LinkedList<Integer> quantities = new LinkedList<>();
    private Date orderDate;
    private double totalPrice;

    public Order(int orderId, Customer customer, Date date) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = date;
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Date getOrderDate() { return orderDate; }

    public void addProduct(Product p, int qty) {
        if (p == null || qty <= 0) return;
        products.add(p);
        quantities.add(qty);
        totalPrice += p.getPrice() * qty;
    }

    public LinkedList<Product> getProducts() { return products; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", customerId=" + (customer!=null?customer.getCustomerId():"null")
               + ", date=" + orderDate + ", total=" + totalPrice + "}";
    }
}
