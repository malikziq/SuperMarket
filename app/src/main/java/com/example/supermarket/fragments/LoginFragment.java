package com.example.supermarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.supermarket.R;
import com.example.supermarket.activities.MainActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.modelViews.UsersModelView;
import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.PreferencesManager;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    @BindView(R.id.login_email_et)
    EditText loginEmailEt;
    @BindView(R.id.login_password_et)
    TextInputLayout loginPasswordEt;
    @BindView(R.id.remember_me_cb)
    CheckBox rememberMeCb;

    private View mView;
    private MainActivity mainActivity;

    @Inject
    UsersModelView usersModelView;

    private User user;

    public LoginFragment() {
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
        Log.i(TAG, "onCreateView: Started, Inflating...");
        mView = inflater.inflate(R.layout.fragment_login, container, false);

        // ButterKnife Binding
        ButterKnife.bind(this, mView);
        // To access MainActivity's Methods
        mainActivity = (MainActivity) getActivity();

        // Dagger
        ((App) mainActivity.getApplication()).getComponent().inject(this);

        usersModelView.setDataBaseHelper(new DataBaseHelper(mainActivity));


        // to close keyboard
        mView.findViewById(R.id.login_relative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input method manager
                InputMethodManager inputMethodManager = (InputMethodManager)
                        view.getContext().getSystemService(mainActivity.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set Email when Remembered
        String rEmail = PreferencesManager.getInstance(mainActivity).getUserEmail();
        Log.i(TAG, "onResume: email= " + rEmail);
        if (!rEmail.equals(""))
            loginEmailEt.setText(rEmail);
    }

    @OnClick({R.id.login_button, R.id.sign_up_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                Log.i(TAG, "onViewClicked: logging in ...");
                User user = usersModelView.isUserRegsted(loginEmailEt.getText().toString());
                String passwordText = loginPasswordEt.getEditText().getText().toString().trim();
                Log.i(TAG, "onViewClicked: password" + passwordText);
                if (user == null) {
                    Log.i(TAG, "onViewClicked: user with email->" + loginEmailEt.getText().toString() + " does not exists!");
                    Toast toast = Toast.makeText(mainActivity, "User not valid", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Log.i(TAG, "onViewClicked: user exsist..." + user.getId());
                    PreferencesManager.getInstance(mainActivity).setUserId(user.getId());
                    if (rememberMeCb.isChecked()) {
                        Log.i(TAG, "onViewClicked: cheked save remember me");
                        rememberUserEmail(loginEmailEt.getText().toString());
                    }
                    if (user.getRole().equals("admin")) {
                        mainActivity.setAdminFragment();
                    } else {

                        mainActivity.setHomeActivity();
                    }
                }

                break;
            case R.id.sign_up_button:
                Log.i(TAG, "onViewClicked: sign_up_button action..");
                mainActivity.setSignUpFragment();
                break;
        }
    }

    private void rememberUserEmail(String email) {
        Log.i(TAG, "rememberUserEmail: to be saved: " + email);
        PreferencesManager.getInstance(mainActivity).setUserEmail(email);
    }
}
