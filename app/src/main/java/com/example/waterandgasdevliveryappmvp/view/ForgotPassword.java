package com.example.waterandgasdevliveryappmvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    // --- Additions for robust OTP dialog ---
    private androidx.appcompat.app.AlertDialog otpDialog;
    private Button resendDialogButton;
    private CountDownTimer resendTimer;
    // ---

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
                Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
            }
        });

        //BACK
        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void startResendCooldownTimer() {
        if (resendTimer != null) {
            resendTimer.cancel();
        }
        resendTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (resendDialogButton != null) {
                    resendDialogButton.setEnabled(false);
                    resendDialogButton.setText("Resend in " + millisUntilFinished / 1000 + "s");
                    resendDialogButton.setBackgroundColor(getResources().getColor(R.color.grey));
                }
            }

            @Override
            public void onFinish() {
                if (resendDialogButton != null) {
                    resendDialogButton.setEnabled(true);
                    resendDialogButton.setText(R.string.resend);
                    resendDialogButton.setBackgroundColor(getResources().getColor(R.color.primary_orange));
                }
            }
        }.start();
    }

    private void showCustomOtpDialog() {
        if (otpDialog != null && otpDialog.isShowing()) {
            // If dialog is already showing, just restart the cooldown.
            startResendCooldownTimer();
            return;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_otp, null);
        EditText otpInput = dialogView.findViewById(R.id.otp_input);
        resendDialogButton = dialogView.findViewById(R.id.resend_OTP);

        otpDialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Verify", null)
                .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                .create();

        otpDialog.setOnDismissListener(dialog -> {
            if (resendTimer != null) {
                resendTimer.cancel();
            }
            otpDialog = null;
            resendDialogButton = null;
            resendTimer = null;
        });

        otpDialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = otpDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                String enteredOtp = otpInput.getText().toString().trim();
                if (enteredOtp.equals(verification_code)) {
                    Toast.makeText(ForgotPassword.this, "OTP Verified!", Toast.LENGTH_SHORT).show();
                    otpDialog.dismiss();
                    Intent intent = new Intent(ForgotPassword.this, ChangePassword.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPassword.this, "Wrong OTP! Please try again.", Toast.LENGTH_SHORT).show();
                    otpInput.setText("");
                }
            });

            startResendCooldownTimer(); // Start cooldown on initial show

            resendDialogButton.setOnClickListener(view -> {
                String email = email_address.getText().toString().trim();
                if (!email.isEmpty()) {
                    presenter.sendOtp(email);
                    Toast.makeText(ForgotPassword.this, "Resending OTP...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPassword.this, "Email address not found.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        otpDialog.show();
    }

    @Override
    public void showSuccess(String otp) {
        verification_code = otp; // store OTP
        runOnUiThread(() -> {
            Toast.makeText(this, "OTP sent: " + "******", Toast.LENGTH_SHORT).show();
            showCustomOtpDialog();
        });
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show());
        System.out.println("Error: " + message);
    }
}
