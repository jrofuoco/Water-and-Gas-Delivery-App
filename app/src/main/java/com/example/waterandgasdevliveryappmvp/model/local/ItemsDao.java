package com.example.waterandgasdevliveryappmvp.model.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemsDao {
    @Insert
    void insertAll(Item... item);

    @Query("SELECT * FROM items WHERE id = :id")
    Item getItemById(int id);

    @Query("SELECT * FROM items")
    List<Item> getAllItems();
}
