package com.altechinferno.superfastshopping.ViewHolder;


import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.altechinferno.superfastshopping.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView productName, productPrice, productQuantity, productImage;
    private AdapterView.OnItemClickListener itemClickListener;



    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productPrice = itemView.findViewById(R.id.price);
        productQuantity = itemView.findViewById(R.id.quantity);
        productImage = itemView.findViewById(R.id.cartImage);

    }

    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}



