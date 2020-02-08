package com.example.supermarket.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.supermarket.R;
import com.example.supermarket.activities.HomeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactsFragment extends Fragment {

    private static final String TAG = "ContactsFragment";
    public static final String PHONE_NUMBER = "0599000000";
    public static final String EMAIL_ADDRESS = "supermarket@super.com";


    private HomeActivity homeActivity;
    private View mView;

    public ContactsFragment() {
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
        mView = inflater.inflate(R.layout.fragment_contacts, container, false);

        // bind buttons
        ButterKnife.bind(this, mView);

        return mView;

    }


    @OnClick({R.id.call_us_button, R.id.email_us_button, R.id.supermarket_location_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_us_button:
                callUs();
                break;

            case R.id.email_us_button:
                emailUs();
                break;

            case R.id.supermarket_location_button:
                locationUs();
                break;
        }
    }

    private void callUs() {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));
        startActivity(dialIntent);
    }

    private void emailUs() {
        String[] receipients = {"super@market.com"};

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, receipients);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    private void locationUs() {

        Intent mapsIntent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("geo:19.076,72.8777"));
        startActivity(mapsIntent);
    }
}
