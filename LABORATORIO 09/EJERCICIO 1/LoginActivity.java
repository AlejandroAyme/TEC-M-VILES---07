package com.ucsm.autenticacionfirebase;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private ProgressBar loading;

    private FirebaseAuth mAuth;

    private String semail;
    private String spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loading = findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            performLoginOrRegistration();
        });
    }

    private void performLoginOrRegistration() {

        semail =
                username.getText()
                        .toString()
                        .trim();

        spassword =
                password.getText()
                        .toString()
                        .trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {

            username.setError("Email inválido");

            return;
        }

        if (spassword.length() < 6) {

            password.setError("Mínimo 6 caracteres");

            return;
        }

        registerUser();
    }

    private void registerUser() {

        loading.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(

                        semail,
                        spassword

                )
                .addOnCompleteListener(this, task -> {

                    loading.setVisibility(View.GONE);

                    if (task.isSuccessful()) {

                        Toast.makeText(
                                LoginActivity.this,
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                LoginActivity.this,
                                task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}