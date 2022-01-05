package com.example.todo_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_app.Utils.Sharepref;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class PasswordActivity extends AppCompatActivity {
    public TextInputEditText password;
    public MaterialButton btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        password = findViewById(R.id.edit_text);
        btnChange = findViewById(R.id.submit_button);

        btnChange.setOnClickListener(v -> {
            Sharepref.setDefaults("password", Objects.requireNonNull(password.getText()).toString(), this.getApplicationContext());
            startActivity(new Intent(PasswordActivity.this, AppLockActivity.class));
        });
    }
}