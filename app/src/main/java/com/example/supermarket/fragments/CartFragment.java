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
import com.example.supermarket.fragments.adapters.CartRecyclerViewAdapter;
import com.example.supermarket.modelViews.CartViewModel;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.PreferencesManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    public static final int NUMBER_OF_COLUMNS = 2;
    @BindView(R.id.cart_recycler_view)
    RecyclerView cartRecyclerView;

    private HomeActivity homeActivity;
    private View mView;
    private CartRecyclerViewAdapter cartRecyclerViewAdapter;

    @Inject
    public CartViewModel cartViewModel;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Creating Cart View...");

        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_cart, container, false);

        // HomeActivity
        homeActivity = (HomeActivity) getActivity();

        // ButterKnife
        ButterKnife.bind(this, mView);

        // Dagger
        ((App) homeActivity.getApplication()).getComponent().inject(this);

        // ViewModel
        cartViewModel.setDataBaseHelper(new DataBaseHelper(homeActivity));

        // IntiCartRecyclerView
        initCartRecyclerView();

        return mView;
    }

    private void initCartRecyclerView() {

        Log.i(TAG, "initCartRecyclerView: initializing ..");
        List<Item> cartItem = cartViewModel.getAllCartItemOfUser(PreferencesManager.getInstance(homeActivity).getUserId());

        cartRecyclerViewAdapter = new CartRecyclerViewAdapter(homeActivity, cartItem);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, LinearLayoutManager.VERTICAL);

        // Set Layout and Adapter
        cartRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        cartRecyclerView.setAdapter(cartRecyclerViewAdapter);
    }
}
