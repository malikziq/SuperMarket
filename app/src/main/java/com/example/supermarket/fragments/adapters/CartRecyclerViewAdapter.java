package com.example.supermarket.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supermarket.R;
import com.example.supermarket.models.pojo.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CartRecyclerViewAdapter";

    private List<Item> cartItems;
    private Context mContext;

    public CartRecyclerViewAdapter(Context mContext, List<Item> cartItems) {
        this.cartItems = cartItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item cItem = cartItems.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        // Put image from uri into imageView
        Glide.with(mContext)
                .load(cItem.getUriImageUtl())
                .apply(requestOptions)
                .into(holder.cartGoodImageIv);

        holder.cartGoodTitleTv.setText(cItem.getTitle());
        holder.cartGoodPriceTv.setText(String.valueOf(cItem.getPrice()));
        String a =  cItem.getAmount()+ " pieces";
        holder.cartGoodTotalAmount.setText(a);
        double total = cItem.getAmount() * cItem.getPrice();
        String ta = total + "$ Total";
        holder.cartGoodTotalPriceTv.setText(ta);

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cart_good_image_iv)
        ImageView cartGoodImageIv;
        @BindView(R.id.cart_good_title_tv)
        TextView cartGoodTitleTv;
        @BindView(R.id.cart_good_price_tv)
        TextView cartGoodPriceTv;
        @BindView(R.id.cart_good_total_price_tv)
        TextView cartGoodTotalPriceTv;
        @BindView(R.id.total_amount_pieces_tv)
        TextView cartGoodTotalAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
