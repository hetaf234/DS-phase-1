package Data_Structure;

public class Review {

    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;   // 1–5
    private String comment;

    // Constructor 
    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        setRating(rating);  // ensure valid range
        this.comment = comment;
    } // end constructor

    //Getters
    public int getReviewId() { return reviewId; } // end getReviewId
    public int getProductId() { return productId; } // end getProductId
    public int getCustomerId() { return customerId; } // end getCustomerId
    public int getRating() { return rating; } // end getRating
    public String getComment() { return comment; } // end getComment

    // Setters
    public void setRating(int rating) {
        // rating must stay between 1 and 5
        if (rating < 1) rating = 1;
        if (rating > 5) rating = 5;
        this.rating = rating;
    } // end setRating

    public void setComment(String c) {
        this.comment = c;
    } // end setComment

    

    // Edit this review (update rating and/or comment)
    public void editReview(Integer newRating, String newComment) {
        if (newRating != null) {
            setRating(newRating);
        } // end if
        if (newComment != null && newComment.length() > 0) {
            setComment(newComment);
        } // end if
    } // end editReview


    public String toString() {
        return "Review{id=" + reviewId +
               ", productId=" + productId +
               ", customerId=" + customerId +
               ", rating=" + rating +
               ", comment='" + comment + "'}";
    } // end toString
} // end Review class
