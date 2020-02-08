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
import com.example.supermarket.activities.AdminActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.fragments.adapters.CartRecyclerViewAdapter;
import com.example.supermarket.modelViews.CartViewModel;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.utils.DataBaseHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomerCartFragment extends Fragment {

    private static final String TAG = "CustomerCartFragment";
    public static final int NUMBER_OF_COLUMNS = 2;
    @BindView(R.id.customer_cart_recycler_view)
    RecyclerView customerCartRecyclerView;

    private AdminActivity adminActivity;
    private View mView;

    @Inject
    CartViewModel cartViewModel;

    private List<Item> items;
    private CartRecyclerViewAdapter cartRecyclerViewAdapter;

    private int userId;


    public CustomerCartFragment() {
        // Required empty public constructor
    }

    public CustomerCartFragment(int userId){
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_customer_cart, container, false);

        adminActivity = (AdminActivity) getActivity();

        ButterKnife.bind(this, mView);

        ((App) adminActivity.getApplication()).getComponent().inject(this);
        cartViewModel.setDataBaseHelper(new DataBaseHelper(adminActivity));
        Log.i(TAG, "onCreateView: " + userId);
        items = cartViewModel.getAllCartItemOfUser(userId);

        initCartRecyclerView();

        return mView;
    }

    private void initCartRecyclerView() {

        Log.i(TAG, "initCartRecyclerView: initializing ..");

        cartRecyclerViewAdapter = new CartRecyclerViewAdapter(adminActivity, items);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, LinearLayoutManager.VERTICAL);

        // Set Layout and Adapter
        customerCartRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        customerCartRecyclerView.setAdapter(cartRecyclerViewAdapter);
    }

}
