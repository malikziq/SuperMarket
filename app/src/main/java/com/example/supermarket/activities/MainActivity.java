package com.example.supermarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.supermarket.R;
import com.example.supermarket.app.App;
import com.example.supermarket.fragments.GetStartedFragment;
import com.example.supermarket.fragments.LoginFragment;
import com.example.supermarket.fragments.SignUpFragment;
import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.DataBaseHelper;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    @Inject
    GetStartedFragment getStartedFragment;
    @Inject
    LoginFragment loginFragment;
    @Inject
    SignUpFragment signUpFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper db = new DataBaseHelper(this);
        if(db.getUserByEmail("admin@admin.com") == null){
            Log.i(TAG, "new Admin: creating ADMIN");
            db.createUser(new User("Admin", "Admin", "admin@admin.com", "0599999", "admin", "male", "admin", "earth"));

        }


        Log.i(TAG, "onCreate: MainActivity Started");
        Log.i(TAG, "onCreate: injecting...");
        ((App) getApplication()).getComponent().inject(this);

        // FragmentManager Transaction
        Log.i(TAG, "onCreate: setting GetStartedFragment!");
        mFragmentManager = getSupportFragmentManager();

        // TODO CHECK always for internet connection if it is valid


        // check if launched from home activity ?
        Intent intent = getIntent();
        if (intent != null) {
            Log.i(TAG, "onCreate: Intent is NOT NULL...");
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            if (message != null && message.equals("yes")) {
                Log.i(TAG, "onCreate: message = " + message);
                setLoginFragment();
            } else {

                mFragmentManager.beginTransaction()
                        .replace(R.id.contener, getStartedFragment)
                        .commit();
            }
        }
    }

    public void setLoginFragment() {
        Log.i(TAG, "setLoginFragment: Setting loginFragment...");
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.contener, loginFragment).commit();
    }

    public void setSignUpFragment() {
        Log.i(TAG, "setSignUpFragment: Setting SignUpFragment...");
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.contener, signUpFragment).commit();
    }

    public void setHomeActivity() {
        Log.i(TAG, "setHomeActivity: Setting HomeActivity...");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void setAdminFragment() {
        Log.i(TAG, "setAdminFragment: Setting AdminActivity...");
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }
}
