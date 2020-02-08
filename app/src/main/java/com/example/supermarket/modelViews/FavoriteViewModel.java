package com.example.supermarket.modelViews;

import android.util.Log;

import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.models.pojo.Favorite;
import com.example.supermarket.models.pojo.Item;

import java.util.List;

public class FavoriteViewModel {

    private static final String TAG = "FavoriteViewModel";

    private DataBaseHelper dataBaseHelper;

    public FavoriteViewModel() {


    }

    public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    public void putToFavorite(Favorite favorite) {
        Log.i(TAG, "putToFavorite: " + favorite.toString());
        if (dataBaseHelper.createFavorite(favorite) == -1)
            Log.w(TAG, "putToFavorite: FAILED TO ADD TO Favorite !");

    }

    public void removeFromFavorite(Favorite favorite) {

        Log.i(TAG, "removeFromFavorite: REMOVING..." + favorite.toString());
        dataBaseHelper.removeFavorite(favorite);
    }

    public List<Item> getUsersFavorites(int userId) {
        Log.i(TAG, "getUsersFavorites: Gitting all the good stuff!");

        List<Item> items = dataBaseHelper.getAllFavoriteByUserId(userId);

        items.stream().forEach(i -> i.setLoved(true));
        return items;
    }
}
