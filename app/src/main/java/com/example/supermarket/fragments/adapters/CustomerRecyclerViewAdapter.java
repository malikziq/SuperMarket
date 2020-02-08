package com.example.supermarket.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket.R;
import com.example.supermarket.activities.AdminActivity;
import com.example.supermarket.models.pojo.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "CustomerRecyclerViewAda";

    private Context mContext;
    private List<User> users;
    private OnCustomerListner onCustomerListner;

    public CustomerRecyclerViewAdapter(Context mContext, List<User> users, OnCustomerListner onCustomerListner) {
        this.mContext = mContext;
        this.users = users;
        this.onCustomerListner = onCustomerListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_recycler_view_item, parent, false);

        return new ViewHolder(view, onCustomerListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String a = users.get(position).getFirstName() + " " + users.get(position).getLastName();
        holder.name.setText(a);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customer_remove_iv)
        ImageView imageView;
        @BindView(R.id.customer_name)
        TextView name;
        @BindView(R.id.customer_recycler_view_card)
        CardView cardView;

        private OnCustomerListner onCustomerListner;

        public ViewHolder(@NonNull View itemView, OnCustomerListner onCustomerListner) {
            super(itemView);

            this.onCustomerListner = onCustomerListner;
            ButterKnife.bind(this, itemView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AdminActivity) mContext).showCart(users.get(getAdapterPosition()).getId());
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCustomerListner.onRemoveClick(users.get(getAdapterPosition()).getId());
                    users.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface OnCustomerListner{
        public void onRemoveClick(int userId);
    }
}
