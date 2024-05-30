package entite;

import java.util.Date;

public class Review {
    private int idReview;
    private int rating;
    private String comment;
    private Date date;
    private int idUClient;

    // Constructors
    public Review() {}

    public Review(int idReview, int rating, String comment, Date date, int idUClient) {
        this.idReview = idReview;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.idUClient = idUClient;
    }

    // Getters and Setters
    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdUClient() {
        return idUClient;
    }

    public void setIdUClient(int idUClient) {
        this.idUClient = idUClient;
    }
}


