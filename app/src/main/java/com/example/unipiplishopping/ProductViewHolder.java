package com.example.unipiplishopping;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView title, id,description, price, location, releaseDate;
    Button addToOrderButton;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.productTitle);
        id =itemView.findViewById(R.id.id);
        description = itemView.findViewById(R.id.description);
        price = itemView.findViewById(R.id.price);
        location = itemView.findViewById(R.id.location);
        releaseDate = itemView.findViewById(R.id.releaseDate);
        addToOrderButton=itemView.findViewById(R.id.addToOrderButton);
    }
}
