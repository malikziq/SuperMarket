package com.example.supermarket.models.pojo;

public class Favorite {
    private int userId;
    private int goodId;

    public Favorite() {
    }

    public Favorite(int userId, int goodId) {
        this.userId = userId;
        this.goodId = goodId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "userId=" + userId +
                ", goodId=" + goodId +
                '}';
    }
}
