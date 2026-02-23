package com.example.waterandgasdevliveryappmvp.view;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.waterandgasdevliveryappmvp.R;
import com.example.waterandgasdevliveryappmvp.model.local.AppDatabase;
import com.example.waterandgasdevliveryappmvp.model.local.Item;
import com.example.waterandgasdevliveryappmvp.view.adapter.ItemAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Homepage extends AppCompatActivity {

    private GridView gridView;
    private ItemAdapter itemAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        gridView = findViewById(R.id.gridView_items);

        db = AppDatabase.getInstance(this);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Item> items = db.itemsDao().getAllItems();
            runOnUiThread(() -> {
                itemAdapter = new ItemAdapter(Homepage.this, items);
                gridView.setAdapter(itemAdapter);
            });
        });
    }
}
