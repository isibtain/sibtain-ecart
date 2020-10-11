package com.sibtain.supercms.models;

import lombok.Data;

@Data
public class Cart {

    private int Id;
    private String name;
    private String price;
    private int quantity;
    private String image;

    public Cart(int id, String name, String price, int quantity, String image) {
        Id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

}
