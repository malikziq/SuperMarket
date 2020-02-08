package com.example.supermarket.modelViews;

import android.util.Log;

import com.example.supermarket.models.pojo.Cart;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.utils.DataBaseHelper;

import java.util.List;

public class CartViewModel {
    private static final String TAG = "CartViewModel";

    private DataBaseHelper dataBaseHelper;

    public CartViewModel() {
    }

    public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    public List<Item> getAllCartItemOfUser(int userId) {
        Log.i(TAG, "getAllCartItemOfUser: getting...");
        return dataBaseHelper.getAllCartItemByUserId(userId);
    }

    public void addToCart(Cart cart){
        Log.i(TAG, "addToCart: adding " + cart.toString());
        dataBaseHelper.createCart(cart);
    }
}
