package com.example.waterandgasdevliveryappmvp.model;


import com.example.waterandgasdevliveryappmvp.model.local.AppDatabase;
import com.example.waterandgasdevliveryappmvp.model.local.Item;
import com.example.waterandgasdevliveryappmvp.view.HomePageView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomePagePresenter {

    private final HomePageView view;
    private final AppDatabase db;
    private final ExecutorService executor;

    public HomePagePresenter(HomePageView view, AppDatabase db) {
        this.view = view;
        this.db = db;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void loadItems() {
        executor.execute(() -> {
            List<Item> items = db.itemsDao().getAllItems();
            view.showItems(items);
        });
    }
}
