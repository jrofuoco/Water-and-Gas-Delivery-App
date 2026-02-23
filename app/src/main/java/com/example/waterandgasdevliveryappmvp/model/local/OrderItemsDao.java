package com.example.waterandgasdevliveryappmvp.model.local;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface OrderItemsDao {
    @Insert
    void insert(OrderItems orderItem);
}
