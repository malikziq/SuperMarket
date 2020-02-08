package com.example.supermarket.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.supermarket.R;
import com.example.supermarket.activities.AdminActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.modelViews.UsersModelView;
import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.EditTextUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddAdminFragment extends Fragment {

    private static final String TAG = "AddAdminFragment";
    @BindView(R.id.admin_first_name_et)
    EditText firstNameEt;
    @BindView(R.id.admin_last_name_et)
    EditText lastNameEt;
    @BindView(R.id.admin_email_et)
    EditText emailEt;
    @BindView(R.id.admin_password_et)
    EditText passwordEt;
    @BindView(R.id.admin_confirm_password_et)
    EditText confirmPasswordEt;
    @BindView(R.id.admin_city_spinner)
    Spinner citySpinner;
    @BindView(R.id.admin_phone_et)
    EditText phoneEt;
    @BindView(R.id.admin_gender_spinner)
    Spinner genderSpinner;
    @BindView(R.id.admin_register_btn)
    Button registerBtn;

    private View mView;
    private AdminActivity adminActivity;
    @Inject
    public UsersModelView usersModelView;

    private boolean isFirstNameValid = false;
    private boolean isLastNameValid = false;
    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;
    private boolean isConfirmPasswordValid = false;
    private boolean isCitySpinnerValid = false;
    private boolean isGenderSpinnerValid = false;
    private boolean isPhoneValid = false;

    private String citySpinnerText = "";
    private String genderSpinnerText = "";


    public AddAdminFragment() {
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
        mView = inflater.inflate(R.layout.fragment_add_admin, container, false);

        adminActivity = (AdminActivity) getActivity();

        //Bind View
        ButterKnife.bind(this, mView);

        ((App) adminActivity.getApplication()).getComponent().inject(this);


        // Initializing Spinners with data and listeners
        initCitySpinner();
        initGenderSpinner();

        initEditTextListeners();
        initSpinnerListeners();

        return mView;
    }

    @OnClick(R.id.admin_register_btn)
    public void onViewClicked() {
        Log.i(TAG, "onViewClicked: Clicked (1)");
        User user = getUserInfo();
        if (user != null) {
            Log.i(TAG, "onViewClicked: users data -> " + user.toString());
            usersModelView.setDataBaseHelper(new DataBaseHelper(adminActivity));

            long userId = usersModelView.registerUser(user);

            Log.i(TAG, "onViewClicked: userId = " + userId);

            if (userId == -1) {
                Log.w(TAG, "onViewClicked: Failed to add" + userId);
                Toast toast = Toast.makeText(adminActivity, "Make sure of your data?!", Toast.LENGTH_SHORT);
                toast.show();
            } else{
                Toast toast = Toast.makeText(adminActivity, "We have a new ADMIN :) .", Toast.LENGTH_SHORT);
                toast.show();
            }

            Log.i(TAG, "onViewClicked: ENDED");

        } else {
            Toast toast = Toast.makeText(adminActivity, "Make sure of your data?!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private User getUserInfo() {
        Log.i(TAG, "getUserInfo: GET USERS INPUT !");
        if (isCitySpinnerValid
                && isConfirmPasswordValid
                && isEmailValid
                && isFirstNameValid
                && isGenderSpinnerValid
                && isPhoneValid
                && isLastNameValid
                && isPasswordValid) {
            Log.i(TAG, "getUserInfo: VALID!");

            User user = new User();
            user.setRole("user");
            user.setPassword(passwordEt.getText().toString());
            user.setLastName(lastNameEt.getText().toString());
            user.setFirstName(firstNameEt.getText().toString());
            user.setGender(genderSpinnerText);
            user.setCity(citySpinnerText);
            user.setEmail(emailEt.getText().toString());
            user.setPhone(phoneEt.getText().toString());
            user.setRole("admin");
            return user;
        }

        Log.w(TAG, "getUserInfo: NOT VALID" );
        return null;
    }

    private void initCitySpinner() {

        Log.i(TAG, "initCitySpinner");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(adminActivity, R.array.cities, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        citySpinner.setAdapter(adapter);
    }

    private void initGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(adminActivity, R.array.gender, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        genderSpinner.setAdapter(adapter);
    }

    private void initEditTextListeners() {

        emailEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (emailEt.getText().toString().length() > 0) {
                    if (!EditTextUtils.isEmailValid(emailEt.getText().toString())) {
                        emailEt.setError("Not valid!");
                        isEmailValid = false;
                    } else
                        isEmailValid = true;
                } else
                    isEmailValid = false;
            }
        });

        firstNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (firstNameEt.getText().toString().length() < 3) {
                    isFirstNameValid = false;
                    firstNameEt.setError("At least 3");
                } else
                    isFirstNameValid = true;
            }
        });

        lastNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (lastNameEt.getText().toString().length() < 3) {
                    lastNameEt.setError("At least 3");
                    isLastNameValid = false;
                } else
                    isLastNameValid = true;
            }
        });

        passwordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (passwordEt.getText().toString().length() < 6) {
                    passwordEt.setError("At least 6");
                    isPasswordValid = false;
                } else {
                    if (!EditTextUtils.isPasswordValid(passwordEt.getText().toString())) {
                        isPasswordValid = false;
                        passwordEt.setError("At 1 char 1 num 1 special char");
                    } else
                        isPasswordValid = true;
                }
            }
        });

        confirmPasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if (!confirmPasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
                    isConfirmPasswordValid = false;
                    confirmPasswordEt.setError("Doesn't match!");
                } else
                    isConfirmPasswordValid = true;
            }
        });

        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (phoneEt.getText().toString().length() != 8){
                    phoneEt.setError("please 8 dig");
                    isPhoneValid = false;
                }
                else
                    isPhoneValid = true;
            }
        });
    }

    private void initSpinnerListeners() {
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);

                if (selectedItemText.equals("Select City"))
                    isCitySpinnerValid = false;
                else {
                    isCitySpinnerValid = true;
                    citySpinnerText = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);

                if (selectedItemText.equals("Select Gender"))
                    isGenderSpinnerValid = false;
                else {
                    isGenderSpinnerValid = true;
                    genderSpinnerText = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}

