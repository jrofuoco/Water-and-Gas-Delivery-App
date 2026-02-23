package com.example.waterandgasdevliveryappmvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.waterandgasdevliveryappmvp.R;
import com.example.waterandgasdevliveryappmvp.model.ChangePasswordPresenter;

public class ChangePassword extends AppCompatActivity implements ChangePasswordView {
    Button confirm;
    EditText password, confirm_password;
    ImageView back;
    private ChangePasswordPresenter presenter;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new ChangePasswordPresenter(this, this);

        email = getIntent().getStringExtra("EMAIL");
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: Email not provided.", Toast.LENGTH_SHORT).show();
            finish();
            return; // Stop further execution of onCreate
        }

        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);

        //BACKBUTTON
        back = findViewById(R.id.back_arrow3);
        back.setOnClickListener(view -> {
            finish();
        });

        //CONFIRM
        confirm = findViewById(R.id.login_button);
        confirm.setOnClickListener(view -> {
            String newPassword = password.getText().toString().trim();
            String confirmPassword = confirm_password.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showMessage("Please enter and confirm your new password.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                showMessage("Passwords do not match.");
                return;
            }

            presenter.changePassword(email, newPassword);
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetAccess() {
        Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ChangePassword.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
