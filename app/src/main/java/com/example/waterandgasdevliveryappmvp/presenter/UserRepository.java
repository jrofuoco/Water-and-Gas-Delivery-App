package com.example.waterandgasdevliveryappmvp.presenter;

import android.content.Context;

import com.example.waterandgasdevliveryappmvp.model.local.AppDatabase;
import com.example.waterandgasdevliveryappmvp.model.local.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public void insertUser(User user, Callback callback) {
        executor.execute(() -> {
            db.userDao().insert(user);
            callback.onComplete();
        });
    }

    public void getUserByEmail(String email, UserCallback callback) {
        executor.execute(() -> {
            User user = db.userDao().getUserByEmail(email);
            callback.onResult(user);
        });
    }

    public interface Callback {
        void onComplete();
    }

    public interface UserCallback {
        void onResult(User user);
    }
}
