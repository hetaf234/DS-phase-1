package Data_Structure;

public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews = new LinkedList<>();

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int s) { this.stock = s; }
    public LinkedList<Review> getReviews() { return reviews; }

    public void addReview(Review r) {
        if (r != null && r.getProductId() == this.productId) reviews.add(r);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) return 0;
        double sum = 0;
        Node<Review> cur = reviews.getHead();
        while (cur != null) {
            sum += cur.getData().getRating();
            cur = cur.getNext();
        }
        return sum / reviews.size();
    }
}
