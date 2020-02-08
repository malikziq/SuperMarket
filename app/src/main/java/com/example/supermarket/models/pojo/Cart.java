package com.example.supermarket.models.pojo;

public class Cart {
    private int userId;
    private int goodId;
    private int amount;

    public Cart() {
    }

    public Cart(int userId, int goodId, int amount) {
        this.userId = userId;
        this.goodId = goodId;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", goodId=" + goodId +
                ", amount=" + amount +
                '}';
    }
}


