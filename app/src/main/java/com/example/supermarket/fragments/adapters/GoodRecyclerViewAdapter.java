package com.example.supermarket.fragments.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supermarket.R;
import com.example.supermarket.activities.GoodDetail;
import com.example.supermarket.activities.HomeActivity;
import com.example.supermarket.models.pojo.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodRecyclerViewAdapter extends RecyclerView.Adapter<GoodRecyclerViewAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "GoodRecyclerViewAdapter";

    List<Item> goods;
    List<Item> filteredGoods;


    private Context mContext;
    private OnGoodListener onGoodListener;

    public GoodRecyclerViewAdapter(Context mContext, List<Item> goods, OnGoodListener onGoodListener) {
        this.goods = goods;
        this.mContext = mContext;
        this.filteredGoods = new ArrayList<>(goods);
        this.onGoodListener = onGoodListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_good_item, parent, false);

        return new ViewHolder(view, onGoodListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.i(TAG, "onBindViewHolder: " + holder);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        // Put image from uri into imageView
        Glide.with(mContext)
                .load(filteredGoods.get(position).getUriImageUtl())
                .apply(requestOptions)
                .into(holder.goodImageIv);

        holder.goodTitleTv.setText(filteredGoods.get(position).getTitle());
        holder.goodPriceTv.setText(String.valueOf(filteredGoods.get(position).getPrice()) + "$");

        Log.i(TAG, "onBindViewHolder: good Title: " + filteredGoods.get(position).getTitle() + " id: " + filteredGoods.get(position).getId() + " isLoved: " + filteredGoods.get(position).isLoved());
        // Put the full like id users has it in favorites
        if (filteredGoods.get(position).isLoved())
            holder.changeLikeImag();
        else
            holder.changeDisLikeImage();
    }

    @Override
    public int getItemCount() {
        return filteredGoods.size();
    }

    @Override
    public Filter getFilter() {
        return goodsFilter;
    }

    private Filter goodsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            Log.i(TAG, "performFiltering: Filtering ...");

            List<Item> filterList = new ArrayList<>();
            String filterPattern = charSequence.toString().toLowerCase().trim();

            String[] filterPatterns = filterPattern.split(",");

            Log.i(TAG, "performFiltering: " + filterPatterns[0] + " from " + filterPattern);

            // Filtering the goods type which is the filterPatterns[0]
            Log.i(TAG, "performFiltering: filter TYPE");
            if (charSequence == null || charSequence.length() == 0 || filterPatterns[0].equals("others")) {
                filterList.addAll(goods);
            } else {

                for (Item item :
                        goods) {
                    if (item.getType().toLowerCase().equals(filterPatterns[0]))
                        filterList.add(item);
                }
            }

            // Filter the height between low and the high index filterPatters[1] and filterPatterns[2]
            Log.i(TAG, "performFiltering: h:" + filterPatterns[1] + " l:" + filterPatterns[2]);
            if (filterPatterns[1].equals("-1") && filterPatterns[2].equals("-1")) {

            } else {
                int h = Integer.parseInt(filterPatterns[2]);
                int l = Integer.parseInt(filterPatterns[1]);

                filterList = filterList.stream()
                        .filter(i -> i.getHeight() < h && i.getHeight() > l)
                        .collect(Collectors.toList());
            }

            // Filter based on the price where we get when ever itrms prive is less than FilterPatterns[3]
            Log.i(TAG, "performFiltering: p:" + filterPatterns[3]);
            if (filterPatterns[3].equals("-1")) {

            } else {
                int p = Integer.parseInt(filterPatterns[3]);

                filterList = filterList.stream()
                        .filter(i -> i.getPrice() < p)
                        .collect(Collectors.toList());
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            filteredGoods.clear();
            filteredGoods.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // View Holder class RecyclerView Cards Items etc...
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnGoodListener onGoodListener;

        @BindView(R.id.good_title_tv)
        TextView goodTitleTv;
        @BindView(R.id.good_like_iv)
        ImageView goodLikeIv;
        @BindView(R.id.good_image_iv)
        ImageView goodImageIv;
        @BindView(R.id.good_price_tv)
        TextView goodPriceTv;
        @BindView(R.id.good_add_to_cart_iv)
        ImageView shoopingCartIv;
        @BindView(R.id.recycler_view_item_card_ciew)
        CardView cardView;

        private boolean isLoved = false;

        public ViewHolder(View itemView, OnGoodListener onGoodListener) {
            super(itemView);

            Log.i(TAG, "ViewHolder: binding ButterKnife .. ");
            ButterKnife.bind(this, itemView);

            this.onGoodListener = onGoodListener;

            goodLikeIv.setOnClickListener(this);
            shoopingCartIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shoppingCartAnim();

                    onGoodListener.onCartClick(filteredGoods.get(getAdapterPosition()).getId());
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item good = filteredGoods.get(getAdapterPosition());
                    Log.i(TAG, "onClick: open details of=" + good.getTitle());

                    Pair[] p = new Pair[3];
                    p[0] = new Pair<View, String>(goodImageIv, "shared_image");
                    p[1] = new Pair<View, String>(goodPriceTv, "shared_price");
                    p[2] = new Pair<View, String>(goodTitleTv, "shared_title");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((HomeActivity) mContext, p);
                    Intent intent = new Intent(mContext, GoodDetail.class);
                    intent.putExtra(GoodDetail.GOOD_TITLE, good.getTitle());
                    intent.putExtra(GoodDetail.GOOD_IMG, good.getImageurl());
                    intent.putExtra(GoodDetail.GOOD_PRICE, good.getPrice().toString());
                    intent.putExtra(GoodDetail.GOOD_DESCRIPTION, good.getDescription());
                    mContext.startActivity(intent, options.toBundle());
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (!filteredGoods.get(getAdapterPosition()).isLoved()) {
                Log.i(TAG, "onClick: CLICK ON LIKE (Y)");
                filteredGoods.get(getAdapterPosition()).setLoved(true);

                changeLikeImag();

                Log.i(TAG, "onClick: good id = " + filteredGoods.get(getAdapterPosition()).getTitle());
                onGoodListener.onGoodClick(filteredGoods.get(getAdapterPosition()).getId());
            } else {
                Log.i(TAG, "onClick: Dis liking... " + filteredGoods.get(getAdapterPosition()).getTitle());
                filteredGoods.get(getAdapterPosition()).setLoved(false);

                changeDisLikeImage();

                onGoodListener.onGoodDisLike(filteredGoods.get(getAdapterPosition()).getId());
            }
        }

        public void changeLikeImag() {
            Log.i(TAG, "changeLikeImag: Like !");
            goodLikeIv.setImageResource(R.drawable.ic_like_full);

        }

        public void changeDisLikeImage() {
            Log.i(TAG, "changeLikeImag: DIS Like !");
            goodLikeIv.setImageResource(R.drawable.ic_like_empty);

        }

        public void shoppingCartAnim() {
            TransitionDrawable transitionDrawable = (TransitionDrawable) shoopingCartIv.getDrawable();
            transitionDrawable.startTransition(1);
            transitionDrawable.reverseTransition(1000);
        }
    }

    public interface OnGoodListener {

        void onGoodClick(int goodId);

        void onGoodDisLike(int goodId);

        void onCartClick(int goodId);
    }
}
