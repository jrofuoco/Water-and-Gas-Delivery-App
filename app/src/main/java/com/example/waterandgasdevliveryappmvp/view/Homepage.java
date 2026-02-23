package com.example.waterandgasdevliveryappmvp.view;
import com.example.waterandgasdevliveryappmvp.model.HomePagePresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.waterandgasdevliveryappmvp.R;
import com.example.waterandgasdevliveryappmvp.model.local.AppDatabase;
import com.example.waterandgasdevliveryappmvp.model.local.Item;
import com.example.waterandgasdevliveryappmvp.view.adapter.ItemAdapter;

import java.util.List;

public class Homepage extends AppCompatActivity implements HomePageView {

    private GridView gridView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        gridView = findViewById(R.id.gridView_items);

        AppDatabase db = AppDatabase.getInstance(this);
        HomePagePresenter presenter = new HomePagePresenter(this, db);

        setupBottomNavigation();

        presenter.loadItems(); // 👈 Call presenter
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                gridView.setVisibility(View.VISIBLE);
                return true;
            } else if (itemId == R.id.navigation_cart) {
                gridView.setVisibility(View.INVISIBLE);
                return true;
            } else if (itemId == R.id.navigation_account) {
                gridView.setVisibility(View.INVISIBLE);
                return true;
            }
            return false;
        });
    }

    @Override
    public void showItems(List<Item> items) {
        runOnUiThread(() -> {
            itemAdapter = new ItemAdapter(this, items);
            gridView.setAdapter(itemAdapter);
        });
    }
}
