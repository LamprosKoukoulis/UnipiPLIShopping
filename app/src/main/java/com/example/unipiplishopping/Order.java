package com.example.unipiplishopping;

import java.util.List;
public class Order {
    public Order(){}
    public Order(String customerName, List<String> productIds){
        this.customerName=customerName;
        this.productIds=productIds;
        this.timestamp=getTimestamp();
    }

    private String customerName;
    private List<String> productIds;
    private long timestamp;

    public String getCustomerName() {return customerName;}
    public List<String> getproductIds() {return productIds;}
    public long getTimestamp() {return System.currentTimeMillis();}
}
