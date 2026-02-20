package com.example.waterandgasdevliveryappmvp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.waterandgasdevliveryappmvp.R;
import com.example.waterandgasdevliveryappmvp.model.OTPPresenter;

public class ForgotPassword extends AppCompatActivity implements OTPPresenter.OTPView {

    ImageView back;
    Button send;
    TextView email_address;

    private OTPPresenter presenter;

    private String verification_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        send = findViewById(R.id.reset_password);
        email_address = findViewById(R.id.email);

        presenter = new OTPPresenter(this);

        //SENDOTP
        send.setOnClickListener(view -> {
            String email = email_address.getText().toString().trim();
            if(!email.isEmpty()) {
                presenter.sendOtp(email);
            }else {
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            }
        });

        //BACK
        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(view -> {
            finish();
        });
    }
    private void showCustomOtpDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_otp, null);
        EditText otpInput = dialogView.findViewById(R.id.otp_input);

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Verify", (dialog, which) -> {
                    String enteredOtp = otpInput.getText().toString().trim();
                    if (enteredOtp.equals(verification_code)) {
                        Toast.makeText(this, "OTP Verified!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Wrong OTP!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showSuccess(String otp) {
        verification_code = otp; // store OTP
        runOnUiThread(() -> {
            Toast.makeText(this, "OTP sent: " + otp, Toast.LENGTH_SHORT).show();
            showCustomOtpDialog();
        });
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show());
    }
}
