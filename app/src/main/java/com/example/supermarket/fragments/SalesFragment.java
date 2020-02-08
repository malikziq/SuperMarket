package com.example.supermarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.supermarket.R;
import com.example.supermarket.activities.HomeActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.fragments.adapters.GoodRecyclerViewAdapter;
import com.example.supermarket.modelViews.GoodsModelView;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.utils.DataBaseHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesFragment extends Fragment implements GoodRecyclerViewAdapter.OnGoodListener {
    private static final String TAG = "SalesFragment";
    public static final int NUMBER_OF_COLUMNS = 2;

    @BindView(R.id.sales_recycler_view)
    RecyclerView salesRecyclerView;

    @Inject
    GoodsModelView goodsModelView;

    private HomeActivity homeActivity;
    private View mView;
    private GoodRecyclerViewAdapter goodRecyclerViewAdapter;

    public SalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sales, container, false);

        // get HomeActivity
        homeActivity = (HomeActivity) getActivity();

        //Bind ButterKnife
        ButterKnife.bind(this, mView);

        // Dagger
        ((App) homeActivity.getApplication()).getComponent().inject(this);

        // Set DataBaseHelper to ModelView
        goodsModelView.setDataBaseHelper(new DataBaseHelper(homeActivity));

        // Sales Recycler View stuff!
        initRecyclerView();

        return mView;
    }

    private void initRecyclerView() {

        List<Item> sales = goodsModelView.getSalesGoods();
        sales.forEach(s -> Log.i(TAG, "initRecyclerView: " + s.getId() + " title " + s.getTitle() + " rate: " + s.getRating()));

        goodRecyclerViewAdapter = new GoodRecyclerViewAdapter(homeActivity, sales, this);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, LinearLayoutManager.VERTICAL);
        salesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        salesRecyclerView.setAdapter(goodRecyclerViewAdapter);
    }

    @Override
    public void onGoodClick(int goodId) {

    }

    @Override
    public void onGoodDisLike(int goodId) {

    }

    @Override
    public void onCartClick(int goodId) {

    }
}
