package com.example.supermarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.supermarket.R;
import com.example.supermarket.activities.MainActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.modelViews.GoodsModelView;
import com.example.supermarket.utils.DataBaseHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetStartedFragment extends Fragment {
    private static final String TAG = "GetStartedFragment";
    @BindView(R.id.determinateBar)
    ProgressBar determinateBar;

    private View mView;
    private MainActivity mainActivity;
    private DataBaseHelper dataBaseHelper;

    @Inject
    GoodsModelView goodsModelView;

    public GetStartedFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Started, Inflating..");
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_get_started, container, false);

        // To access MainActivity's Methods
        mainActivity = (MainActivity) getActivity();

        // Dagger Injection
        ((App) mainActivity.getApplication()).getComponent().inject(this);
        goodsModelView.setGetStartedFragment(this);

        // ButterKnife Views Binding
        ButterKnife.bind(this, mView);


        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Creating dataBaseHelper...");
        dataBaseHelper = new DataBaseHelper(mainActivity);

        Log.i(TAG, "onResume: setting modelView databaseHelper..");
        goodsModelView.setDataBaseHelper(dataBaseHelper);
    }

    @OnClick({R.id.get_started_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_started_button:

                // TODO show progress bar
                determinateBar.setVisibility(View.VISIBLE);

                Log.i(TAG, "onViewClicked: GetStartedButton Clicked.");
                goodsModelView.getStarted();
                break;

        }
    }

    public void setLoginFragment() {
        // TODO shut the progress bar
        determinateBar.setVisibility(View.GONE);
        mainActivity.setLoginFragment();
    }

    public void noConnection() {
        determinateBar.setVisibility(View.GONE);
        Toast.makeText(mainActivity, "Failed to connect", Toast.LENGTH_SHORT).show();
    }

}
