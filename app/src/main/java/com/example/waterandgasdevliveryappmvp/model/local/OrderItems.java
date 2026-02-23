package com.example.waterandgasdevliveryappmvp.model.local;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_items",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Item.class,
                        parentColumns = "id",
                        childColumns = "itemId",
                        onDelete = ForeignKey.CASCADE)
        })
public class OrderItems {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public int itemId;
    public int quantity;
}
