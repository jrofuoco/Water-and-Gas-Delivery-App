package com.example.waterandgasdevliveryappmvp.model;

import android.content.Context;

import com.example.waterandgasdevliveryappmvp.presenter.UserRepository;
import com.example.waterandgasdevliveryappmvp.view.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final UserRepository repository;

    public LoginPresenter(LoginView view, Context context) {
        this.view = view;
        this.repository = new UserRepository(context);
    }


    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showMessage("Enter your email or password");
            return;
        }

        repository.getUserByEmail(email, user -> {
            if(user == null || !user.password.equals(password)) {
                view.showMessage("Incorrect email or Password");
            }else {
                view.onLoginSuccess();
            }
        });
    }
}
