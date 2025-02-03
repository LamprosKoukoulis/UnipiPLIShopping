package com.example.unipiplishopping;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Settings implements LocationListener{
    private DatabaseReference reference;
    private LocationManager locationManager;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private String customerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        applyFontSize(findViewById(android.R.id.content));
        //Gets email from intent
        customerEmail = getIntent().getStringExtra("customerEmail");
        if (customerEmail == null) {
        showToast(getString(R.string.failedToRetrieveEmail));
        return;
    }
        read();
        SettingsClickListener();

        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void SettingsClickListener(){
        View buttonSettings=findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {//update UI based on user preferences
        super.onResume();
        isDarkMode = preferences.getBoolean("darkMode",false);
        applyFontSize(findViewById(android.R.id.content));
        applyDarkMode();
        read();
    }

    public void read(){//Reads from db and creates Product
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference("products");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList =new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    try{
                        String id = data.child("id").getValue(String.class);
                        String title = data.child("title").getValue(String.class);
                        String description = data.child("description").getValue(String.class);
                        String releaseDate = data.child("releaseDate").getValue(String.class);
                        String price = data.child("price").getValue(String.class);
                        String location = data.child("location").getValue(String.class);
                        Product product = new Product(id, title, description, releaseDate, price, location);
                        productList.add(product);
                    }catch(Exception e){
                        Log.d("FirebaseRead","Error reading product data",e);
                    }
                }//Using ProductAdapter populates recyclerView in MainAActivity
                adapter = new ProductAdapter(MainActivity.this,productList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast(getString(R.string.noDataFound));
            }
        });
    }

    public void placeOrder(View view){
        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference().child("orders");
        List<Product> selectedProducts = adapter.getSelectedProducts();
        List<String> productsId=new ArrayList<>();

        if(selectedProducts.isEmpty()){
            showToast(getString(R.string.noProductsInCart));
            return;
        }
        //Get product(s) Id from product(s) in the cart
        selectedProducts.forEach(product -> productsId.add(product.getId()));

        Order order= new Order(customerEmail,productsId);
        String orderId= reference2.push().getKey(); //Generate orderId
        if(orderId!=null & customerEmail!=null){
            reference2.child(orderId).setValue(order).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                showToast(getString(R.string.orderRegistered));
                showSuccessfulOrder(selectedProducts);
                selectedProducts.clear();
             }else{
                 showToast(getString(R.string.orderCompletionError));
             }
            });
        }else{
            showToast(getString(R.string.orderCompletionError));
        }
    }
    private void showToast(String info){
        Toast.makeText(MainActivity.this,info,Toast.LENGTH_SHORT).show();
    }
    public void showSuccessfulOrder(List<Product> selectedProductIds){
                StringBuilder builder = new StringBuilder();
                for (Product product : selectedProductIds) {
                    builder.append(product.getTitle() + "\n");
                }
                // Show an alert dialog with the product Title/s
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.orderRegistered))
                        .setMessage(builder.toString())
                        .show();
    }
    //public void gps(View view) {
    //    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    //        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
    //        return;
    //    }
    //    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    //    locationManager.removeUpdates(this);
    //}
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //recyclerView.setText(location.getLatitude()+","+location.getLongitude());
    }
}