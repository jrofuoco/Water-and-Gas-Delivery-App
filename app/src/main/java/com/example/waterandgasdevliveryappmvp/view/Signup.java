package com.example.waterandgasdevliveryappmvp.view;

import android.os.Bundle;
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
import com.example.waterandgasdevliveryappmvp.presenter.SignupPresenter;

public class Signup extends AppCompatActivity implements SignupView{

    ImageView back_arrow;
    TextView login_here;
    EditText firstname, lastname, email, phone_number, password, confirm_password;
    Button signup;

    private SignupPresenter signupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email_address);
        phone_number = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        signup = findViewById(R.id.signup_button);

        signupPresenter = new SignupPresenter(this, this);

        signup.setOnClickListener(view -> {
            String phoneStr = phone_number.getText().toString();
            if (password.getText().toString().equals(confirm_password.getText().toString())){
                if (phoneStr.isEmpty()) {
                    Toast.makeText(Signup.this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    signupPresenter.register(
                            firstname.getText().toString(),
                            lastname.getText().toString(),
                            email.getText().toString(),
                            Long.parseLong(phoneStr),
                            password.getText().toString()
                    );
                } catch (NumberFormatException e) {
                    Toast.makeText(Signup.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });

        //BACK ARROW
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(view -> {
            finish();
        });

        //LOGIN HERE BUTTON
        login_here = findViewById(R.id.login_here_button);
        login_here.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void showMessage(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRegistrationSuccess() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Account successfully created.", Toast.LENGTH_SHORT);
            finish();
        });
    }
}