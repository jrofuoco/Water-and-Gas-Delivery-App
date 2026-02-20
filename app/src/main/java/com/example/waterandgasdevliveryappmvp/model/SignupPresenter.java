package com.example.waterandgasdevliveryappmvp.model;

import android.content.Context;

import com.example.waterandgasdevliveryappmvp.model.local.User;
import com.example.waterandgasdevliveryappmvp.presenter.UserRepository;
import com.example.waterandgasdevliveryappmvp.view.SignupView;

public class SignupPresenter {

    private final SignupView view;
    private final UserRepository repository;

    public SignupPresenter(SignupView view, Context context) {
        this.view = view;
        this.repository = new UserRepository(context);
    }

    public void register(String firstname, String lastname, String email, long phone_number, String password) {
        if(firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || String.valueOf(phone_number).isEmpty() || password.isEmpty()) {
            view.showMessage("All fields are required.");
            return;
        }

        repository.getUserByEmail(email, new UserRepository.UserCallback() {
            @Override
            public void onResult(User user) {
                if (user != null) {
                    view.showMessage("Email already registered");
                } else {
                    User newUser = new User(firstname, lastname, email, phone_number, password);
                    repository.insertUser(newUser, new UserRepository.Callback() {
                        @Override
                        public void onComplete() {
                            view.onRegistrationSuccess();
                        }
                    });
                }
            }
        });
    }
}
