package org.example.shop.model;

import java.sql.Timestamp;

public class BlackList {
    private int id;
    private int userId;
    private Timestamp addedAt;
    private String status;

    public BlackList(int id, int userId, Timestamp addedAt, String status) {
        this.id = id;
        this.userId = userId;
        this.addedAt = addedAt;
        this.status = status;
    }

    public BlackList(int userId, Timestamp addedAt, String status) {
        this.userId = userId;
        this.addedAt = addedAt;
        this.status = status;
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

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
