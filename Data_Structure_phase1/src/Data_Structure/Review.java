
    package Data_Structure;
    
    public class Review{
    	private int reviewId;
    	private int productId;
    	private int customerId;
    	private int rating;
    	private String comment;
    	
    	
    	
    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
			super();
			this.reviewId = reviewId;
			this.productId = productId;
			this.customerId = customerId;
			this.rating = rating;
			this.comment = comment;
		}//constructor 


	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment=comment;
	}
	public int getRating() {
		return rating;
	}
public void setRating(int rating) {
	this.rating=rating;
}

public void editReview(Integer newRating, String newComment) {
	if(newRating!=null) {
		setRating(newRating);
	}
	if(newComment!=null) {
		setComment(newComment);
	}
	
	
}//editReview


@Override
public String toString() {
	return "\n reviewId=" + reviewId + "\n productId=" + productId + "\n customerId=" + customerId + "\n rating="
			+ rating + "\n comment=" + comment;
}








	}//end of review class 
    
    
    
    
    
    
    
    
    
    
    
    
   

 
    

  


    
  