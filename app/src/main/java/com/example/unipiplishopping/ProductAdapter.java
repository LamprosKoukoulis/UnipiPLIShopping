package com.example.unipiplishopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> productList;
    final List<Product> selectedProducts= new ArrayList<>();
    public ProductAdapter(List<Product> productList) {this.productList = productList;}

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.title.setText(product.getTitle());
        holder.id.setText(product.getId());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.location.setText(product.getLocation());
        holder.releaseDate.setText(product.getReleaseDate());
        holder.addToOrderButton.setOnClickListener(v->{
            if(selectedProducts.contains(product))
                Toast.makeText(holder.itemView.getContext(), "Το προιον είναι ήδη στο καλάθι", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(holder.itemView.getContext(), "Το προιον προστέθηκε στο καλάθι", Toast.LENGTH_SHORT).show();
                selectedProducts.add(product);
            }});
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public List<Product> getSelectedProducts(){return selectedProducts;}

}


