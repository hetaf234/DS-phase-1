package Data_Structure;

public class Review {
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;      // 1–5
    private String comment;

    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        setRating(rating);
        this.comment = comment;
    }

    public int getReviewId() { return reviewId; }
    public int getProductId() { return productId; }
    public int getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public void setRating(int rating) {
        if (rating < 1) rating = 1;
        if (rating > 5) rating = 5;
        this.rating = rating;
    }
    public void setComment(String c) { this.comment = c; }

    @Override
    public String toString() {
        return "Review{id=" + reviewId + ", productId=" + productId +
               ", customerId=" + customerId + ", rating=" + rating +
               ", comment='" + comment + "'}";
    }
}
