package com.app.cureace.adapter;

import static com.app.cureace.activity.medicines.CartActivity.total_price;
import static com.app.cureace.activity.medicines.CartActivity.total_tv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cureace.R;
import com.app.cureace.activity.medicines.MedicinesDetailsActivity;
import com.app.cureace.model.MedicineCartModel;
import com.app.cureace.model.MedicineModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<MedicineCartModel> ambulanceModelList;
    Context context;
    SharedPreferences sharedPref;
    public CartAdapter(List<MedicineCartModel> ambulanceModelList, Context context) {
        this.ambulanceModelList = ambulanceModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cart_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        sharedPref = context.getSharedPreferences(AppUtil.PREFS, 0);

        holder.name_tv.setText(ambulanceModelList.get(position).getM_title());
        holder.price_tv.setText("Price : ₹"+ambulanceModelList.get(position).getM_price());
        holder.date_tv.setText("Quantity : " +ambulanceModelList.get(position).getM_quantity() + "\nTotal : " +ambulanceModelList.get(position).getM_sub_total());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicinesDetailsActivity.class);
                intent.putExtra("data",ambulanceModelList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        total_price=0;
        for (int i = 0; i<ambulanceModelList.size(); i++)
        {
            total_price +=Double.parseDouble(ambulanceModelList.get(i).getM_sub_total());


        }
        total_tv.setText("Total : ₹"+total_price);

        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delProduct(holder,position);
            }
        });


        Picasso.with(context).load(ambulanceModelList.get(position).getM_image()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return ambulanceModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         public TextView name_tv,price_tv,date_tv;
         public ImageView imageView;
         public AppCompatButton remove_btn;
         public ViewHolder(View itemView) {
            super(itemView);
             name_tv = (TextView) itemView.findViewById(R.id.name_tv);
             price_tv = (TextView) itemView.findViewById(R.id.price_tv);
             date_tv = (TextView) itemView.findViewById(R.id.date_tv);
             imageView =   itemView.findViewById(R.id.imageView);
             remove_btn =   itemView.findViewById(R.id.remove_btn);

        }
    }


    private void delProduct(ViewHolder holder, int position) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference()
                .child(AppUtil.MEDICINES_CART_TABLE_KEY).child(sharedPref.getString(AppUtil.USER_ID,"")).child(ambulanceModelList.get(position).getId());
        mDatabaseRef.removeValue();
        holder.itemView.setVisibility(View.GONE);
        Toast.makeText(context, "Product has been removed.", Toast.LENGTH_SHORT).show();
        ambulanceModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ambulanceModelList.size());

    }


}
