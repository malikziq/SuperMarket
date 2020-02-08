package com.example.supermarket.modelViews;

import android.util.Log;

import com.example.supermarket.fragments.GetStartedFragment;
import com.example.supermarket.models.ItemsModel;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.utils.DataBaseHelper;

import java.util.List;
import java.util.stream.Collectors;

public class GoodsModelView {

    private static final String TAG = "GoodsModelView";

    private GetStartedFragment getStartedFragment;
    private ItemsModel itemsModel;
    private List<Item> items;
    private DataBaseHelper dataBaseHelper;

    public GoodsModelView(ItemsModel itemsModel) {
        this.itemsModel = itemsModel;
        itemsModel.setModelView(this);
    }

    public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    public void setGetStartedFragment(GetStartedFragment getStartedFragment) {
        this.getStartedFragment = getStartedFragment;
    }

    public void getStarted() {
        Log.i(TAG, "getStarted: Let's start ->");

        itemsModel.getConnectionAndAllItems();
    }

    public void successFullConnection(List<Item> items) {
        Log.i(TAG, "successFullConnection: itemsList length = " + items.size());

        for (Item item :
                items) {
            dataBaseHelper.createGood(item);
        }
        getStartedFragment.setLoginFragment();
    }

    public void faluierConnection() {

        Log.i(TAG, "Failure Connection: ");
        getStartedFragment.noConnection();
    }

    public List<Item> getAllGoods(int userId) {
        Log.i(TAG, "getAllGoods: Starting ...");
        List<Item> items = dataBaseHelper.getAllItems();
        List<Item> favItems = dataBaseHelper.getAllFavoriteByUserId(userId);

        for (Item i :
                favItems) {
            Log.i(TAG, "getAllGoods: " + i.getTitle());

            items.stream()
                    .filter(item -> item.getId() == i.getId())
                    .findFirst()
                    .get()
                    .setLoved(true);
        }

        return items;
    }

    public List<Item> getSalesGoods() {
        Log.i(TAG, "getSalesGoods: Getting Sales Items...");

        List<Item> items = dataBaseHelper.getAllItems();

        return items.stream().filter(i -> i.getRating() >= 4).collect(Collectors.toList());
    }
}
