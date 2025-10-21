package Data_Structure;

public class Product {

    // ------------------- Attributes -------------------
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews = new LinkedList<>(); // list of product reviews

    // ------------------- Constructor -------------------
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    } // end constructor

    // ------------------- Getters & Setters -------------------
    public int getProductId() { return productId; } // end getProductId
    public String getName() { return name; } // end getName
    public double getPrice() { return price; } // end getPrice
    public int getStock() { return stock; } // end getStock
    public LinkedList<Review> getReviews() { return reviews; } // end getReviews
    public void setPrice(double newPrice) { this.price = newPrice; } // end setPrice
    public void setStock(int newStock) { this.stock = newStock; } // end setStock

    // ------------------- Per-product Operations -------------------

    // Add a review for this product
    public void addReview(Review r) {
        if (r != null && r.getProductId() == this.productId) { reviews.add(r); } // end if
    } // end addReview

    // Remove a review by ID (linear)
    public boolean removeReviewById(int reviewId) {
        Node<Review> cur = reviews.getHead();
        while (cur != null) {
            if (cur.getData().getReviewId() == reviewId) {
                reviews.remove(cur.getData());
                return true;
            } // end if
            cur = cur.getNext();
        } // end while
        return false;
    } // end removeReviewById

    // Edit a review by ID
    public boolean editReviewById(int reviewId, Integer newRating, String newComment) {
        Node<Review> cur = reviews.getHead();
        while (cur != null) {
            Review r = cur.getData();
            if (r.getReviewId() == reviewId) {
                if (newRating != null) { r.setRating(newRating); } // end if
                if (newComment != null && newComment.length() > 0) { r.setComment(newComment); } // end if
                return true;
            } // end if
            cur = cur.getNext();
        } // end while
        return false;
    } // end editReviewById

    // Average rating
    public double getAverageRating() {
        if (reviews.isEmpty()) return 0;
        double sum = 0;
        Node<Review> cur = reviews.getHead();
        while (cur != null) {
            sum += cur.getData().getRating();
            cur = cur.getNext();
        } // end while
        return sum / reviews.size();
    } // end getAverageRating

    // Out-of-stock checker
    public boolean isOutOfStock() {
        return stock <= 0;
    } // end isOutOfStock

    // Update price/stock together
    public void updateProduct(double newPrice, int newStock) {
        if (newPrice >= 0) { this.price = newPrice; } // end if
        if (newStock >= 0) { this.stock = newStock; } // end if
    } // end updateProduct

    // Pretty print details + reviews
    public void printDetails() {
        System.out.println("Product: " + name + ", price=" + price + ", stock=" + stock);
        System.out.println("Average rating: " + getAverageRating());
        Node<Review> r = reviews.getHead();
        if (r == null) { System.out.println("No reviews for this product."); } // end if
        while (r != null) {
            Review rev = r.getData();
            System.out.println("Review#" + rev.getReviewId() + " by C" + rev.getCustomerId()
                    + " rating=" + rev.getRating() + " comment=" + rev.getComment());
            r = r.getNext();
        } // end while
    } // end printDetails

    // ------------------- Static Operations on Lists (class-level) -------------------

    // Linear search by ID
    public static Product searchById(LinkedList<Product> list, int id) {
        Node<Product> cur = list.getHead();
        while (cur != null) {
            if (cur.getData().getProductId() == id) { return cur.getData(); } // end if
            cur = cur.getNext();
        } // end while
        return null;
    } // end searchById

    // Linear search by Name (case-insensitive)
    public static Product searchByName(LinkedList<Product> list, String targetName) {
        Node<Product> cur = list.getHead();
        while (cur != null) {
            if (cur.getData().getName().equalsIgnoreCase(targetName)) { return cur.getData(); } // end if
            cur = cur.getNext();
        } // end while
        return null;
    } // end searchByName

    // Top N products by average rating (prints)
    public static void printTopNByAverageRating(LinkedList<Product> list, int n) {
        Product a = null, b = null, c = null; // for n up to 3 (assignment asks top 3)
        double ra = -1, rb = -1, rc = -1;

        Node<Product> cur = list.getHead();
        while (cur != null) {
            Product p = cur.getData();
            double r = p.getAverageRating();
            if (r > ra) { c = b; rc = rb; b = a; rb = ra; a = p; ra = r; }
            else if (r > rb) { c = b; rc = rb; b = p; rb = r; }
            else if (r > rc) { c = p; rc = r; } // end if-chain
            cur = cur.getNext();
        } // end while

        System.out.println("Top " + n + " Products:");
        int count = 0;
        if (a != null && count < n) { System.out.println("1) " + a.getName() + " (" + ra + ")"); count++; } // end if
        if (b != null && count < n) { System.out.println("2) " + b.getName() + " (" + rb + ")"); count++; } // end if
        if (c != null && count < n) { System.out.println("3) " + c.getName() + " (" + rc + ")"); count++; } // end if
    } // end printTopNByAverageRating

    // Common reviewed products by two customers with avg > minAvg (prints)
    public static void printCommonReviewedAbove(LinkedList<Product> list, int c1, int c2, double minAvg) {
        boolean found = false;
        Node<Product> pNode = list.getHead();
        while (pNode != null) {
            Product p = pNode.getData();
            boolean aReviewed = false, bReviewed = false;

            Node<Review> rNode = p.getReviews().getHead();
            while (rNode != null) {
                int cid = rNode.getData().getCustomerId();
                if (cid == c1) aReviewed = true;
                if (cid == c2) bReviewed = true;
                if (aReviewed && bReviewed) break;
                rNode = rNode.getNext();
            } // end while

            if (aReviewed && bReviewed && p.getAverageRating() > minAvg) {
                System.out.println(p.getName() + " (avg=" + p.getAverageRating() + ")");
                found = true;
            } // end if
            pNode = pNode.getNext();
        } // end while

        if (!found) { System.out.println("No common reviewed products above " + minAvg); } // end if
    } // end printCommonReviewedAbove

    // Remove a product by ID (composition → clears its reviews), returns true if removed
    public static boolean removeById(LinkedList<Product> list, int pid) {
        Product target = searchById(list, pid);
        if (target == null) { return false; } // end if
        // clear reviews (composition)
        Node<Review> r = target.getReviews().getHead();
        while (r != null) {
            target.getReviews().remove(r.getData());
            r = target.getReviews().getHead();
        } // end while
        list.remove(target);
        return true;
    } // end removeById

    // Print all products (quick overview)
    public static void printAll(LinkedList<Product> list) {
        System.out.println("Products (" + list.size() + "):");
        Node<Product> p = list.getHead();
        while (p != null) {
            Product pp = p.getData();
            System.out.println(pp.getProductId() + " - " + pp.getName() + " price=" + pp.getPrice() + " stock=" + pp.getStock());
            p = p.getNext();
        } // end while
    } // end printAll

    // ------------------- Display -------------------
    public String toString() {
        return productId + " - " + name + " (price: " + price + ", stock: " + stock + ")";
    } // end toString
} // end Product class
