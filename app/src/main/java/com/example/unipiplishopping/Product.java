package com.example.unipiplishopping;
public class Product {
    public String id;
    public String title;
    public String description;
    public String releaseDate;
    public double price;
    public String location;

    // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    public Product() {}
        public Product(String id, String title, String description, String releaseDate, String price, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = Double.parseDouble(price.replace(".","").replace(",","."));
        this.location = location;
    }
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getReleaseDate() {return releaseDate;}
    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

}
