package com.example.waterandgasdevliveryappmvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.waterandgasdevliveryappmvp.R;
import com.example.waterandgasdevliveryappmvp.model.LoginPresenter;

public class Login extends AppCompatActivity implements LoginView{

    Button login;
    TextView signup;

    EditText email, password;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginPresenter = new LoginPresenter(this, this);
        //LOGIN BUTTONa
        login = findViewById(R.id.login_button);
        login.setOnClickListener(view -> {
            loginPresenter.login(email.getText().toString(), password.getText().toString());
        });

        //SIGN UP BUTTON
        signup = findViewById(R.id.signup_Btn);
        signup.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        });
    }


    @Override
    public void showMessage(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onLoginSuccess() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Homepage.class);
            startActivity(intent);
        });
    }
}
