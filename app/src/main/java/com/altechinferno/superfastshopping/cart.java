package com.altechinferno.superfastshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.altechinferno.superfastshopping.model.Cart;
import com.altechinferno.superfastshopping.ViewHolder.CartViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button buttonCheckout;
    private TextView totalPrice;


    FirebaseFirestore mFireStore;
    String customerID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        buttonCheckout = findViewById(R.id.buttonCheckout);
        totalPrice = findViewById(R.id.totalPrice);



        FirebaseUser customerCurrent = FirebaseAuth.getInstance().getCurrentUser();
        customerID = customerCurrent.getUid();


        mFireStore = FirebaseFirestore.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        CollectionReference cartListRef = mFireStore.collection("CartList/UserView/Products");

        Query query = cartListRef.whereEqualTo(customerID,true);

        FirestoreRecyclerOptions<Cart> options =
                new FirestoreRecyclerOptions.Builder<Cart>()
                        .setQuery(query, Cart.class)
                        .build();

        FirestoreRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirestoreRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.productName.setText(model.getPname());
                holder.productPrice.setText(model.getPrice());
                holder.productQuantity.setText(model.getQuantity());

                holder.getAdapterPosition();

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }
}
