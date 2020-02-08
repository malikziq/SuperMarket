package com.example.supermarket.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.EditTextUtils;
import com.example.supermarket.R;
import com.example.supermarket.activities.MainActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.modelViews.UsersModelView;
import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.PreferencesManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";

    @BindView(R.id.first_name_et)
    EditText firstNameEt;
    @BindView(R.id.last_name_et)
    EditText lastNameEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.confirm_password_et)
    EditText confirmPasswordEt;
    @BindView(R.id.city_spinner)
    Spinner citySpinner;
    @BindView(R.id.gender_spinner)
    Spinner genderSpinner;
    @BindView(R.id.phone_et)
    EditText phoneEt;

    private View mView;
    private MainActivity mainActivity;
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


    public SignUpFragment() {
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
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Bind ButterKnife
        ButterKnife.bind(this, mView);

        // Get Main Instance
        mainActivity = (MainActivity) getActivity();

        // Dagger
        ((App) mainActivity.getApplication()).getComponent().inject(this);


        // Initializing Spinners with data and listeners
        initCitySpinner();
        initGenderSpinner();

        initEditTextListeners();
        initSpinnerListeners();

        // to close keyboard
        mView.findViewById(R.id.sign_up_relative).setOnClickListener(new View.OnClickListener() {
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

    @OnClick({R.id.back_image_button, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image_button:
                mainActivity.setLoginFragment();
                break;
            case R.id.register_btn:
                Log.i(TAG, "onViewClicked: Clicked (1)");
                User user = getUserInfo();
                if (user != null) {
                    Log.i(TAG, "onViewClicked: users data -> " + user.toString());
                    usersModelView.setDataBaseHelper(new DataBaseHelper(mainActivity));

                    long userId = usersModelView.registerUser(user);

                    Log.i(TAG, "onViewClicked: userId = " + userId);

                    if (userId == -1) {
                        Log.w(TAG, "onViewClicked: Failed to add" + userId);
                        Toast toast = Toast.makeText(mainActivity, "Make sure of your data?!", Toast.LENGTH_SHORT);
                        toast.show();
                    } else{
                        PreferencesManager.getInstance(mainActivity).setUserId((int) userId);
                        mainActivity.setHomeActivity();
                    }

                    Log.i(TAG, "onViewClicked: ENDED");

                } else {
                    Toast toast = Toast.makeText(mainActivity, "Make sure of your data?!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
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

            return user;
        }

        Log.w(TAG, "getUserInfo: NOT VALID" );
        return null;
    }

    private void initCitySpinner() {

        Log.i(TAG, "initCitySpinner");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(mainActivity, R.array.cities, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        citySpinner.setAdapter(adapter);
    }

    private void initGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(mainActivity, R.array.gender, R.layout.simple_spinner_item);
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
