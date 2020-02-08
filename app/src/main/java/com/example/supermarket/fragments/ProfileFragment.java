package com.example.supermarket.fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.supermarket.R;
import com.example.supermarket.activities.HomeActivity;
import com.example.supermarket.app.App;
import com.example.supermarket.modelViews.UsersModelView;
import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.DataBaseHelper;
import com.example.supermarket.utils.PreferencesManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    @Inject
    UsersModelView usersModelView;

    @BindView(R.id.profile_name_tv)
    TextView profileNameTv;
    @BindView(R.id.profile_last_name_tv)
    TextView profileLastNameTv;
    @BindView(R.id.profile_email_tv)
    TextView profileEmailTv;
    @BindView(R.id.profile_phone_tv)
    TextView profilePhoneTv;
    @BindView(R.id.profile_city_tv)
    TextView profileCityTv;
    @BindView(R.id.profile_gender_tv)
    TextView profileGenderTv;
    @BindView(R.id.profile_password_tv)
    TextView profilePasswordTv;

    private HomeActivity homeActivity;
    private View mView;

    private User currentUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Creating ...");

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        // HomeActivity
        homeActivity = (HomeActivity) getActivity();

        // ButterKnife
        ButterKnife.bind(this, mView);

        // Dagger
        ((App) homeActivity.getApplication()).getComponent().inject(this);

        usersModelView.setDataBaseHelper(new DataBaseHelper(homeActivity));

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: setting data ...");
        setTextView();
    }

    private void setTextView() {
        Log.i(TAG, "setTextView: getting user");
        currentUser = usersModelView.getUserById(PreferencesManager.getInstance(homeActivity).getUserId());
        profileNameTv.setText(currentUser.getFirstName());
        profileLastNameTv.setText(currentUser.getLastName());
        profileEmailTv.setText(currentUser.getEmail());
        profilePhoneTv.setText(currentUser.getPhone());
        profileCityTv.setText(currentUser.getCity());
        profileGenderTv.setText(currentUser.getGender());
        profilePasswordTv.setText(currentUser.getPassword());
    }

    @OnClick({R.id.profile_name_edit_iv, R.id.profile_last_name_edit_iv, R.id.profile_email_edit_iv, R.id.profile_phone_edit_iv, R.id.profile_city_edit_iv, R.id.profile_gender_edit_iv, R.id.profile_password_edit_iv})
    public void onViewClicked(View view) {
        showProfileEditDialog(view.getId());

    }


    public void showProfileEditDialog(int viewId) {

        Log.i(TAG, "showProfileEditDialog: ...");
        EditText editText;
        Button editBt, cancelBt;

        Dialog dialog = new Dialog(homeActivity);
        dialog.setContentView(R.layout.profile_edit_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        editText = dialog.findViewById(R.id.profile_edit_text_et);
        editBt = dialog.findViewById(R.id.profile_edit_dilog_bt);
        cancelBt = dialog.findViewById(R.id.profile_cancel_dilog_bt);

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")){
                    setChange(viewId, editText.getText().toString());
                    dialog.dismiss();
                }
                else
                    editText.setError("Fill this!");
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    public void setChange(int viewId, String change){

        switch (viewId) {
            case R.id.profile_name_edit_iv:
                currentUser.setFirstName(change);
                break;
            case R.id.profile_last_name_edit_iv:
                currentUser.setLastName(change);
                break;
            case R.id.profile_email_edit_iv:
                currentUser.setEmail(change);
                break;
            case R.id.profile_phone_edit_iv:
                currentUser.setPhone(change);
                break;
            case R.id.profile_city_edit_iv:
                currentUser.setCity(change);
                break;
            case R.id.profile_gender_edit_iv:
                currentUser.setGender(change);
                break;
            case R.id.profile_password_edit_iv:
                currentUser.setPassword(change);
                break;
        }

        Log.i(TAG, "setChange: update data base...");
        usersModelView.updateUser(currentUser);
        Log.i(TAG, "setChange: update views...");
        setTextView();
    }
}
