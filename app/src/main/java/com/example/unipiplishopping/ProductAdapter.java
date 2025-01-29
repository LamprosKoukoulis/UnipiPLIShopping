package com.example.unipiplishopping;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> productList;
    SharedPreferences preferences;
    final List<Product> selectedProducts= new ArrayList<>();
    public ProductAdapter(Context context ,List<Product> productList) {
        this.productList = productList;
        this.preferences =context.getSharedPreferences(
                "user_preferences" + FirebaseAuth.getInstance().getCurrentUser().getUid(),context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        //use product.xml to show the products in the page
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        applyFontSize(holder); //sets fontSize based on user preferences
        //creates new RecyclerView.ViewHolder using the product.xml and populates using the current product position
        Product product = productList.get(position);
        holder.title.setText(product.getTitle());
        holder.id.setText(product.getId());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.location.setText(product.getLocation());
        holder.releaseDate.setText(product.getReleaseDate());
        holder.addToOrderButton.setOnClickListener(v->{
            if(selectedProducts.contains(product)) {
                Toast.makeText(holder.itemView.getContext(), "Το προιον είναι ήδη στο καλάθι", Toast.LENGTH_SHORT).show();}
            else {
                Toast.makeText(holder.itemView.getContext(), "Το προιον προστέθηκε στο καλάθι", Toast.LENGTH_SHORT).show();
                selectedProducts.add(product);
            }});
    }
    public void applyFontSize(ProductViewHolder holder){
    // Update font size for all textViews in each ProductViewHolder
        int fontSize = preferences.getInt("fontSize", 20);
            if (holder != null) {
                for (TextView item : holder.getTextViews(holder.itemView)) {
                    item.setTextSize(fontSize);
                }
            }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public List<Product> getSelectedProducts(){return selectedProducts;}
}


