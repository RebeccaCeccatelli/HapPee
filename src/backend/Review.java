package backend;

import java.util.ArrayList;

public class Review {
    private String text;
    private ArrayList<Photo> photos;
    private float rating;

    public Review(String text, ArrayList<Photo> photos, float rating){
        this.text = text;
        this.photos = photos;
        this.rating = rating;
    }

    public void modifyReviewText(String newText) {
        this.text = newText;
    }

    //TODO modify and adapt to interface and views
    public void showReview(){
        System.out.println(text);
        System.out.println(rating);
        //TODO add show photos
    }

}
