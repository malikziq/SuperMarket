package com.example.supermarket.fragments;

import android.os.Bundle;
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
import com.example.supermarket.fragments.adapters.CustomerRecyclerViewAdapter;
import com.example.supermarket.modelViews.UsersModelView;
import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.DataBaseHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomerFragment extends Fragment implements CustomerRecyclerViewAdapter.OnCustomerListner {

    private static final String TAG = "CustomerFragment";

    @BindView(R.id.customer_recycler_view)
    RecyclerView customerRecyclerView;

    private AdminActivity adminActivity;
    private View mView;

    @Inject
    UsersModelView usersModelView;

    private CustomerRecyclerViewAdapter customerRecyclerViewAdapter;
    private List<User> customers;

    public CustomerFragment() {
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
        mView = inflater.inflate(R.layout.fragment_customer, container, false);

        adminActivity = (AdminActivity) getActivity();

        ButterKnife.bind(this, mView);

        ((App) adminActivity.getApplication()).getComponent().inject(this);

        usersModelView.setDataBaseHelper(new DataBaseHelper(adminActivity));
        customers = usersModelView.getAllCustomers();

        initCustomerRecyclerView();
        return mView;
    }

    private void initCustomerRecyclerView() {

        customerRecyclerViewAdapter = new CustomerRecyclerViewAdapter(adminActivity, customers, this);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

        customerRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        customerRecyclerView.setAdapter(customerRecyclerViewAdapter);
    }

    @Override
    public void onRemoveClick(int userId) {
        usersModelView.deleteCustomer(userId);
    }
}
