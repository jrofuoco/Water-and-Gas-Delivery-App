package com.example.waterandgasdevliveryappmvp.model.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ItemsDao {
    @Insert
    void insert(Item item);

    @Query("SELECT * FROM items WHERE id = :id")
    Item getItemById(int id);
}
