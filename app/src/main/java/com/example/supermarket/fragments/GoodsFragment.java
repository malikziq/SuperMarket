package com.example.supermarket.fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.supermarket.R;
import com.example.supermarket.activities.HomeActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.fragments.adapters.GoodRecyclerViewAdapter;
import com.example.supermarket.fragments.adapters.GoodTypeAdapter;
import com.example.supermarket.modelViews.CartViewModel;
import com.example.supermarket.modelViews.FavoriteViewModel;
import com.example.supermarket.modelViews.GoodsModelView;
import com.example.supermarket.models.pojo.Cart;
import com.example.supermarket.models.pojo.Favorite;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.models.pojo.TypeSpinnerItem;
import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoodsFragment extends Fragment implements GoodRecyclerViewAdapter.OnGoodListener {

    private static final String TAG = "GoodsFragment";
    public static final int NUMBER_OF_COLUMNS = 2;

    @BindView(R.id.goods_recycler_view)
    RecyclerView goodsRecyclerView;
    @BindView(R.id.type_filter_spinner)
    Spinner typeFilterSpinner;

    @Inject
    public GoodsModelView modelView;

    @Inject
    public FavoriteViewModel favoraiteViewModel;

    @Inject
    public CartViewModel cartViewModel;


    private List<Item> itemsList;
    private ArrayList<TypeSpinnerItem> typeSpinnerItems;

    private View mView;
    private HomeActivity homeActivity;
    private DataBaseHelper dataBaseHelper;

    private GoodRecyclerViewAdapter goodRecyclerViewAdapter;
    private GoodTypeAdapter goodTypeAdapter;

    private String typeFiler = "other";
    private String priceFilter = "-1";
    private String lowHeightFilter = "-1";
    private String highHeightFilter = "-1";

    private String filterPattern = "other,-1,-1,-1";


    public GoodsFragment() {
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
        mView = inflater.inflate(R.layout.fragment_goods, container, false);

        Log.i(TAG, "onCreateView: Creating GoodFragment...");

        // Bind Views
        ButterKnife.bind(this, mView);

        // Set Context HomeActivity
        homeActivity = (HomeActivity) getActivity();

        // Dagger Injection
        ((App) homeActivity.getApplication()).getComponent().inject(this);

        // Set DataBase Connection  to GoodsModelView
        dataBaseHelper = new DataBaseHelper(homeActivity);
        modelView.setDataBaseHelper(dataBaseHelper);
        favoraiteViewModel.setDataBaseHelper(dataBaseHelper);
        cartViewModel.setDataBaseHelper(dataBaseHelper);

        // Initializing
        initRecyclerView();
        initSpinnerTypeFilter();

        return mView;
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView: initializing..");

        // Get All Items from DataBase
        Log.i(TAG, "initRecyclerView: getting all goods!");
        itemsList = modelView.getAllGoods(PreferencesManager.getInstance(homeActivity).getUserId());

        goodRecyclerViewAdapter = new GoodRecyclerViewAdapter(homeActivity, itemsList, this);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, LinearLayoutManager.VERTICAL);
        goodsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        goodsRecyclerView.setAdapter(goodRecyclerViewAdapter);
    }

    private void initSpinnerTypeFilter() {
        Log.i(TAG, "initSpinnerTypeFilter: initializing...");

        // Setting the types with icons for the spinner
        typeSpinnerItems = new ArrayList<>();
        typeSpinnerItems.add(new TypeSpinnerItem("Others", R.drawable.ic_other_food));
        typeSpinnerItems.add(new TypeSpinnerItem("Vegetable", R.drawable.ic_vegetables));
        typeSpinnerItems.add(new TypeSpinnerItem("Fruit", R.drawable.ic_fruit));
        typeSpinnerItems.add(new TypeSpinnerItem("Dairy", R.drawable.ic_dairies));

        goodTypeAdapter = new GoodTypeAdapter(homeActivity, typeSpinnerItems);
        typeFilterSpinner.setAdapter(goodTypeAdapter);

        typeFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "onItemSelected: CLICKED...");
                TypeSpinnerItem typeSpinnerItem = (TypeSpinnerItem) adapterView.getItemAtPosition(i);
                setFilter(0, typeSpinnerItem.getType(), "","");
                if (goodRecyclerViewAdapter.getFilter() != null)
                    goodRecyclerViewAdapter.getFilter().filter(filterPattern);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onGoodClick(int goodId) {
        Log.i(TAG, "onGoodClick: " + goodId + " Adding to user " + PreferencesManager.getInstance(homeActivity).getUserId() + " Favorites");

        favoraiteViewModel.putToFavorite(
                new Favorite(PreferencesManager
                        .getInstance(homeActivity).getUserId()
                        , goodId));
    }

    @Override
    public void onGoodDisLike(int goodId) {
        Log.i(TAG, "onGoodDisLike: " + goodId + " Adding to user " + PreferencesManager.getInstance(homeActivity).getUserId() + " Favorites");

        favoraiteViewModel.removeFromFavorite(
                new Favorite(PreferencesManager
                        .getInstance(homeActivity).getUserId()
                        , goodId));
    }

    @Override
    public void onCartClick(int goodId) {
        showCartDialog(goodId);
    }

    public void showCartDialog(int goodId) {

        Log.i(TAG, "showCartDialog: pop ...");

        EditText amountEt;
        Button addBt, cancelBt;

        Dialog dialog = new Dialog(homeActivity);
        dialog.setContentView(R.layout.good_amount_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        amountEt = dialog.findViewById(R.id.amout_et);
        addBt = dialog.findViewById(R.id.add_dilog_bt);
        cancelBt = dialog.findViewById(R.id.cancel_dilog_bt);

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: Adding...");

                if (!amountEt.getText().toString().equals("")) {
                    int amo = Integer.parseInt(amountEt.getText().toString());
                    Log.i(TAG, "onClick: amount= " + amo);
                    cartViewModel.addToCart(new Cart(PreferencesManager.getInstance(homeActivity).getUserId(), goodId, amo));
                    dialog.dismiss();
                } else
                    amountEt.setError("Fill this please!");
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void setFilter(int filterType, String low, String high, String price) {

        String[] tempPattern = filterPattern.split(",");

        switch (filterType) {
            case 0:
                tempPattern[0] = low;
                break;

            case 1:

                Log.i(TAG, "setFilter: l: " + low + " h: " + high + " p: " + price);
                if (low.equals(""))
                    low = "-1";
                if (high.equals(""))
                    high = "-1";
                if (price.equals(""))
                    price = "-1";

                tempPattern[1] = low;
                tempPattern[2] = high;
                tempPattern[3] = price;
                break;

            default:
        }

        filterPattern = TextUtils.join(",", tempPattern);
    }

    public void showFilterGoodDialog() {

        Log.i(TAG, "showCartDialog: pop ...");

        EditText highEt, lowEt, priceEt;
        Button accpetBt, cancelBtn;


        Dialog dialog = new Dialog(homeActivity);
        dialog.setContentView(R.layout.goods_filter_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        highEt = dialog.findViewById(R.id.filter_dialog_high_hight);
        lowEt = dialog.findViewById(R.id.filter_dialog_low_hight);
        priceEt = dialog.findViewById(R.id.filter_dialog_price);

        accpetBt = dialog.findViewById(R.id.filter_dialog_accept_bt);
        cancelBtn = dialog.findViewById(R.id.filter_dialog_cancel_bt);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        accpetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilter(1, lowEt.getText().toString(), highEt.getText().toString(), priceEt.getText().toString());
                goodRecyclerViewAdapter.getFilter().filter(filterPattern);
                dialog.dismiss();
            }
        });


        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @OnClick(R.id.good_filter_iv_bt)
    public void onViewClicked() {
        showFilterGoodDialog();
    }
}
