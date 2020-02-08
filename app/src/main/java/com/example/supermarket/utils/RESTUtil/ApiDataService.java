package com.example.supermarket.utils.RESTUtil;

import com.example.supermarket.models.pojo.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiDataService {

    @GET("5dcfef932f000080003f1f34")
    public Call<List<Item>> getAllItems();
}
