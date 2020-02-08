package com.example.supermarket.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supermarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodDetail extends AppCompatActivity {

    private static final String TAG = "GoodDetail";

    public static final String GOOD_TITLE = "goodTitle";
    public static final String GOOD_IMG = "goodImg";
    public static final String GOOD_DESCRIPTION = "goodDescription";
    public static final String GOOD_PRICE = "goodPrice";

    @BindView(R.id.imageView2)
    ImageView goodImage;
    @BindView(R.id.good_activity_good_title)
    TextView goodTitle;
    @BindView(R.id.good_activity_good_price)
    TextView goodPrice;
    @BindView(R.id.good_activity_good_description)
    TextView goodDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);

        Log.i(TAG, "onCreate: Start Details Activity");

        // Bind Views
        ButterKnife.bind(this);

        initText();
    }

    private void initText() {
        Log.i(TAG, "initText: Set data");
        Intent intent = getIntent();
        // Get Data from intent
        String title = intent.getStringExtra(GOOD_TITLE);
        String price = intent.getStringExtra(GOOD_PRICE);
        Log.i(TAG, "initText: " + price);
        String description = intent.getStringExtra(GOOD_DESCRIPTION);

        // Set Data
        goodTitle.setText(title);
        goodDescription.setText(description);
        goodPrice.setText(price);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        // Put image from uri into imageView
        Glide.with(this)
                .load(Uri.parse(intent.getStringExtra(GOOD_IMG)))
                //.apply(requestOptions)
                .into(goodImage);
    }
}
