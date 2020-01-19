package com.altechinferno.superfastshopping.CartViewHolder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.altechinferno.superfastshopping.R;
import com.altechinferno.superfastshopping.Interface.ItemClickListner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView productName, productPrice, productQuantity;
    private ItemClickListner itemClickListner;


    public CartViewHolder(View itemView)
    {
        super(itemView);

        productName = itemView.findViewById(R.id.productName);
        productPrice = itemView.findViewById(R.id.price);
        productQuantity = itemView.findViewById(R.id.quantity);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
