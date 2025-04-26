package org.example.shop.model;

import java.sql.Timestamp;

public class Cart {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private java.sql.Timestamp addedAt;
    private String productName;
    private int price;

    public Cart(int id, int userId, int productId, int quantity, Timestamp addedAt, String productName, int price) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.productName = productName;
        this.price = price;
    }

    public Cart(int userId, int productId, int quantity, Timestamp addedAt, String productName, int price) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.productName = productName;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
