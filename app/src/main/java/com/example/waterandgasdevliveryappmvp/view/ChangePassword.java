package com.example.waterandgasdevliveryappmvp.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.waterandgasdevliveryappmvp.R;

public class ChangePassword extends AppCompatActivity {
    Button confirm;
    EditText password, confirm_password;

    ImageView back;

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

        //BACKBUTTON
        back = findViewById(R.id.back_arrow3);
        back.setOnClickListener(view -> {
            finish();
        });

        //CONFIRM
        confirm = findViewById(R.id.login_button);
        confirm.setOnClickListener(view -> {

        });
    }
}