package com.example.supermarket.models;

import android.util.Log;

import com.example.supermarket.utils.RESTUtil.ApiDataService;
import com.example.supermarket.utils.RESTUtil.RetrofitClientInstance;
import com.example.supermarket.modelViews.GoodsModelView;
import com.example.supermarket.models.pojo.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsModel {

    private static final String TAG = "ItemsModel";

    private GoodsModelView modelView;

    private static List<Item> items;


    public ItemsModel() {
    }

    public void  setModelView(GoodsModelView modelView){
        this.modelView = modelView;
    }

    public void getConnectionAndAllItems() {
        Log.i(TAG, "getConnectionAndAllItems: Getting retrofit service...");
        ApiDataService service = RetrofitClientInstance
                .getRetrofitInstance()
                .create(ApiDataService.class);

        Log.i(TAG, "getConnectionAndAllItems: Calling getConnectionAndAllItems service...");
        Call<List<Item>> call = service.getAllItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                // TODO : check if the list is Empty
                Log.i(TAG, "onResponse: Successful");
                items = response.body();
                modelView.successFullConnection(items);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.i(TAG, "onFailure: Falied to get Items!!" + t.getMessage());
                modelView.faluierConnection();
            }
        });
    }


    public List<Item> getItems() {
        return items;
    }
}
