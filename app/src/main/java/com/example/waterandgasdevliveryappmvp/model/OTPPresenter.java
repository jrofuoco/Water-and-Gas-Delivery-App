package com.example.waterandgasdevliveryappmvp.model;


import com.example.waterandgasdevliveryappmvp.model.local.OTPModel;

public class OTPPresenter {

    public interface OTPView {
        void showSuccess(String otp);
        void showError(String message);
    }

    private OTPView view;
    private OTPModel model;

    public OTPPresenter(OTPView view) {
        this.view = view;
        this.model = new OTPModel();
    }

    public void sendOtp(String userEmail) {
        model.sendOTP(userEmail, new OTPModel.OTPListener() {
            @Override
            public void onSent(String otp) {
                view.showSuccess(otp);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }
}