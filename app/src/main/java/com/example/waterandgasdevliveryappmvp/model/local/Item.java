package com.example.waterandgasdevliveryappmvp.model.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String description;
    public int price;
    public String image;
    public int quantity;
}
