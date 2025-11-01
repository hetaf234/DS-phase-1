package Data_Structure;

public class Product {

    // Attributes 
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews = new LinkedList<>(); // list of product reviews

    // Constructor
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    } // end constructor

    // Getters & Setters 
    public int getProductId() { return productId; } // end getProductId
    public String getName() { return name; } // end getName
    public double getPrice() { return price; } // end getPrice
    public int getStock() { return stock; } // end getStock
    public LinkedList<Review> getReviews() { return reviews; } // end getReviews
    public void setPrice(double newPrice) { this.price = newPrice; } // end setPrice
    public void setStock(int newStock) { this.stock = newStock; } // end setStock

    // Per-product Operations
    public void addReview(Review r) {
        if (r != null && r.getProductId() == this.productId) {
            reviews.insert(r);
        } // end if
    } // end addReview

    public boolean removeReviewById(int reviewId) {
        if (reviews.empty()) return false;

        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            if (r.getReviewId() == reviewId) {
                reviews.remove();
                return true;
            } // end if

            if (reviews.last()) break;
            reviews.findNext();
        } // end while
        return false;
    } // end removeReviewById

    public boolean editReviewById(int reviewId, Integer newRating, String newComment) {
        if (reviews.empty()) return false;

        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            if (r.getReviewId() == reviewId) {
                if (newRating != null) r.setRating(newRating);
                if (newComment != null && newComment.length() > 0) r.setComment(newComment);
                return true;
            } // end if

            if (reviews.last()) break;
            reviews.findNext();
        } // end while
        return false;
    } // end editReviewById

    public double getAverageRating() {
        if (reviews.empty()) return 0;

        double sum = 0;
        int count = 0;

        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            sum += r.getRating();
            count++;

            if (reviews.last()) break;
            reviews.findNext();
        } // end while
        return count == 0 ? 0 : sum / count;
    } // end getAverageRating

    public boolean isOutOfStock() {
        return stock <= 0;
    } // end isOutOfStock

    public void updateProduct(double newPrice, int newStock) {
        if (newPrice >= 0) this.price = newPrice;
        if (newStock >= 0) this.stock = newStock;
    } // end updateProduct

    public void printDetails() {
        System.out.println("Product: " + name + ", price=" + price + ", stock=" + stock);
        System.out.println("Average rating: " + getAverageRating());

        if (reviews.empty()) {
            System.out.println("No reviews for this product.");
            return;
        } // end if

        reviews.findFirst();
        while (true) {
            Review rev = reviews.retrieve();
            System.out.println("Review#" + rev.getReviewId() + " by C" + rev.getCustomerId()
                    + " rating=" + rev.getRating() + " comment=" + rev.getComment());

            if (reviews.last()) break;
            reviews.findNext();
        } // end while
    } // end printDetails


    public static Product searchById(LinkedList<Product> list, int id) {
        if (list.empty()) return null;

        list.findFirst();
        while (true) {
            Product p = list.retrieve();
            if (p.getProductId() == id) return p;

            if (list.last()) break;
            list.findNext();
        } // end while
        return null;
    } // end searchById

   

    public static void printTopNByAverageRating(LinkedList<Product> list, int n) {
        Product a = null, b = null, c = null;
        double ra = -1, rb = -1, rc = -1;

        list.findFirst();
        while (true) {
            Product p = list.retrieve();
            double r = p.getAverageRating();

            if (r > ra) {          // if higher than best so far
                c = b; rc = rb;    // shift old 2nd to 3rd
                b = a; rb = ra;    // shift old 1st to 2nd
                a = p; ra = r;     // set new 1st
            }
            else if (r > rb) {     // second best
                c = b; rc = rb;
                b = p; rb = r;
            }
            else if (r > rc) {     // third best
                c = p; rc = r;
            }

            if (list.last()) break;
            list.findNext();
       
        } // end while

        System.out.println("Top " + n + " Products:");
        int count = 0;
        if (a != null && count < n) { System.out.println("1) " + a.getName() + " (" + ra + ")"); count++; }
        if (b != null && count < n) { System.out.println("2) " + b.getName() + " (" + rb + ")"); count++; }
        if (c != null && count < n) { System.out.println("3) " + c.getName() + " (" + rc + ")"); count++; }
    } // end printTopNByAverageRating

    public static void printCommonReviewedAbove(LinkedList<Product> list, int c1, int c2, double minAvg) {
        boolean found = false;
        if (list.empty()) {
            System.out.println("No products available.");
            return;
        } // end if

        list.findFirst();
        while (true) {
            Product p = list.retrieve();
            boolean aReviewed = false, bReviewed = false;

            LinkedList<Review> rList = p.getReviews();
            if (!rList.empty()) {
                rList.findFirst();
                while (true) {
                    Review r = rList.retrieve();
                    int cid = r.getCustomerId();
                    if (cid == c1) aReviewed = true;
                    if (cid == c2) bReviewed = true;
                    if (aReviewed && bReviewed) break;

                    if (rList.last()) break;
                    rList.findNext();
                } // end while
            } // end if

            if (aReviewed && bReviewed && p.getAverageRating() > minAvg) {
                System.out.println(p.getName() + " (avg=" + p.getAverageRating() + ")");
                found = true;
            } // end if

            if (list.last()) break;
            list.findNext();
        } // end while

        if (!found)
            System.out.println("No common reviewed products above " + minAvg);
    } // end printCommonReviewedAbove

    public static boolean removeById(LinkedList<Product> list, int pid) {
        Product target = searchById(list, pid);
        if (target == null) return false;

        LinkedList<Review> revs = target.getReviews();
        while (!revs.empty()) {
            revs.findFirst();
            revs.remove();
        } // end while

        list.findFirst();
        while (true) {
            Product p = list.retrieve();
            if (p == target) {
                list.remove();
                return true;
            } // end if
            if (list.last()) break;
            list.findNext();
        } // end while
        return false;
    } // end removeById

   

    public String toString() {
        return productId + " - " + name + " (price: " + price + ", stock: " + stock + ")";
    } // end toString
    
} // end Product class
