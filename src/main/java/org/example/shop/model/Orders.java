package org.example.shop.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Orders {
    private int id;
    private int userId;
    private Timestamp orderDate;
    private int totalPrice;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }





    public Orders(int id, int userId, Timestamp orderDate, int totalPrice, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;

    }


    public Orders(int id, int userId, Timestamp orderDate, int totalPrice) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Orders(int userId, Timestamp orderDate, int totalPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
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

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
