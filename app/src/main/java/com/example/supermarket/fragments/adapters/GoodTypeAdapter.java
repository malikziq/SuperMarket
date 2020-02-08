package com.example.supermarket.fragments.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.supermarket.R;
import com.example.supermarket.models.pojo.TypeSpinnerItem;

import java.util.ArrayList;

public class GoodTypeAdapter extends ArrayAdapter<TypeSpinnerItem> {
    private static final String TAG = "GoodTypeAdapter";

    public GoodTypeAdapter(@NonNull Context context, ArrayList<TypeSpinnerItem> typeSpinnerItems) {
        super(context, 0, typeSpinnerItems);
        Log.i(TAG, "GoodTypeAdapter: constuctor!");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(TAG, "getView: Started");
        return initView(position, convertView, parent);
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(TAG, "getDropDownView: Started");
        return initView(position, convertView, parent);
    }

    private View initView(int postion, View convertView, ViewGroup parent) {
        Log.i(TAG, "initView: ");
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_good_type_item, parent, false);
        }

        ImageView typeImage = convertView.findViewById(R.id.good_type_iv);
        TextView typeTextView = convertView.findViewById(R.id.good_type_tv);

        TypeSpinnerItem typeSpinnerItem = getItem(postion);

        Log.i(TAG, "initView: "+ typeSpinnerItem.toString());

        if (typeSpinnerItem != null) {
            typeImage.setImageResource(typeSpinnerItem.getTypeImage());
            typeTextView.setText(typeSpinnerItem.getType());
        }
        return convertView;
    }
}
