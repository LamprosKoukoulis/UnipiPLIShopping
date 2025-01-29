package com.example.unipiplishopping;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView title, id, description, price, location, releaseDate;
    Button addToOrderButton;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.productTitle);
        id = itemView.findViewById(R.id.id);
        description = itemView.findViewById(R.id.description);
        price = itemView.findViewById(R.id.price);
        location = itemView.findViewById(R.id.location);
        releaseDate = itemView.findViewById(R.id.releaseDate);
        addToOrderButton = itemView.findViewById(R.id.addToOrderButton);

    }
    public List<TextView> getTextViews(View view){
        List<TextView> list=new ArrayList<>();
        if(view instanceof TextView) {
            list.add((TextView) view);
            //I dont have to check if it's button/EditText as all widgets extend TextView
        }
        if(view instanceof ViewGroup){//All items are in ContrainedLayout extends ViewGroup
            ViewGroup layout=(ViewGroup) view;
            for(int i=0;i<layout.getChildCount();i++){
                View item =layout.getChildAt(i);
                list.addAll(getTextViews(item));
            }
        }
        return list;
    }
}