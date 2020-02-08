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
import com.example.supermarket.modelViews.FavoriteViewModel;
import com.example.supermarket.models.pojo.Favorite;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.PreferencesManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment implements GoodRecyclerViewAdapter.OnGoodListener {
    private static final String TAG = "FavoritesFragment";
    public static final int NUMBER_OF_COLUMNS = 2;

    @BindView(R.id.favorite_recycler_view)
    RecyclerView favoriteRecyclerView;

    private View mView;
    private HomeActivity homeActivity;
    private GoodRecyclerViewAdapter goodRecyclerViewAdapter;

    private DataBaseHelper dataBaseHelper;
    @Inject
    public FavoriteViewModel favoriteViewModel;

    private List<Item> favoriteItemList;

    public FavoritesFragment() {
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
        mView = inflater.inflate(R.layout.fragment_favorites, container, false);

        Log.i(TAG, "onCreateView: Creating FavoriteFragment...");

        homeActivity = (HomeActivity) getActivity();
        // ButterKnife
        ButterKnife.bind(this, mView);
        // Dagger
        ((App) homeActivity.getApplication()).getComponent().inject(this);

        // ModelView
        dataBaseHelper = new DataBaseHelper(homeActivity);
        favoriteViewModel.setDataBaseHelper(dataBaseHelper);

        // Initialize RecyclerView
        initRecyclerView();

        return mView;
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView: Favorite RecyclerView...");

        favoriteItemList = favoriteViewModel.getUsersFavorites(PreferencesManager.getInstance(homeActivity).getUserId());

        goodRecyclerViewAdapter = new GoodRecyclerViewAdapter(homeActivity, favoriteItemList, this);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, LinearLayoutManager.VERTICAL);
        favoriteRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        favoriteRecyclerView.setAdapter(goodRecyclerViewAdapter);
    }

    @Override
    public void onGoodClick(int goodId) {
        Log.i(TAG, "onGoodClick: Liked...");


    }

    @Override
    public void onGoodDisLike(int goodId) {
        favoriteViewModel.removeFromFavorite(
                new Favorite(PreferencesManager
                        .getInstance(homeActivity).getUserId()
                        , goodId));

        // TODO find a better way to remove items from recycler view
        initRecyclerView();

    }

    @Override
    public void onCartClick(int goodId) {

    }

}
