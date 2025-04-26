package org.example.shop.model;

public class Products {
    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private String image_url;

    public Products(int id, String name, String description, int price, int quantity, String image_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image_url = image_url;
    }

    public Products(String name, String description, int price, int quantity, String image_url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
