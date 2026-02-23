package com.example.waterandgasdevliveryappmvp.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.waterandgasdevliveryappmvp.presenter.UserRepository;
import com.example.waterandgasdevliveryappmvp.view.ChangePasswordView;

public class ChangePasswordPresenter {
    private final ChangePasswordView view;
    private final UserRepository repository;
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public ChangePasswordPresenter(ChangePasswordView view, Context context) {
        this.view = view;
        this.repository = new UserRepository(context);
    }

    public void changePassword(String email, String newPassword) {
        if (email.isEmpty() || newPassword.isEmpty()) {
            view.showMessage("Enter your email or new password");
            return;
        }

        repository.getUserByEmail(email, user -> {
            if (user != null) {
                repository.updatePassword(email, newPassword.trim(), () -> {
                    mainThreadHandler.post(() -> view.onResetAccess());
                });
            } else {
                mainThreadHandler.post(() -> view.showMessage("User not found"));
                System.out.println(email);
            }
        });
    }
}
