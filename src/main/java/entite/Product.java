package entite;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.DbConnect;

import java.sql.SQLException;
import java.util.ArrayList;

public class Product {
    private int id;
    private int id_category;
    private String name_category;
    private String name;
    private String image;
    private float price;

    private float calory;

    private ImageView imageView;
    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(String url) {
        Image imgUrl = new Image(url);
        this.imageView = new ImageView(imgUrl);
        this.imageView.setFitWidth(100);
        this.imageView.setFitHeight(100);
    }


    public float getCalory() {
        return calory;
    }

    public int getId() {
        return id;
    }

    public int getId_category() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    public float getPrice() {
        return price;
    }

    public String getName_category() {
        return name_category;
    }

    public String setName_category(int id_category) throws SQLException {
        var result = DbConnect.queryReturn("categories");
        String name = "not found";
        while (result.next()){
            if (result.getInt(1)== id_category){
                name = result.getString("name");
            }
        }
        return name;
    }

    public Product(int id, int id_category, String name, String image, float price,float calory) throws SQLException {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.image = image;
        this.price = price;
        this.name_category = setName_category(id_category);
        this.calory = calory;
        //setImageView(image); //Set imageview to display in tableview
    }

}
