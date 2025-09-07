package com.store;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String category;
    private String description;
    private double price; // giá gốc
    private int quantity;
    private double discount; // phần trăm, ví dụ 10.0 = 10%

    public Product(int id, String name, String category, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.discount = 0.0;
    }

    // Getters và Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; } // giá gốc
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    // Giá sau khi áp dụng giảm giá
    public double getPriceAfterDiscount() {
        return price * (1 - discount / 100.0);
    }

    // Tổng giá trị tồn kho của sản phẩm
    public double getTotalValue() {
        return getPriceAfterDiscount() * quantity;
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s | Cat:%s | Giá:%,.0f -> Giảm còn: %,.0f | SL:%d | Mô tả:%s | Giảm:%.1f%%",
                id, name, category, price, getPriceAfterDiscount(), quantity, description, discount);
    }
}
