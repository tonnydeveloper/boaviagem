package com.example.boaviagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private  DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        this.databaseHelper = new DatabaseHelper(this);

        SharedPreferences s = getSharedPreferences("refs", MODE_PRIVATE);

        boolean logged = s.getBoolean("logged", false);

        if(logged){
            Intent enter = new Intent(this, DashboardActivity.class);
            startActivity(enter);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        MaterialButton btnLogin = (MaterialButton) findViewById(R.id.login);
        MaterialButton btnCad = (MaterialButton) findViewById(R.id.cadastro);

        Intent login = new Intent(this, LoginActivity.class);
        Intent cadasro = new Intent(this, CadastroActivity.class);

        btnLogin.setOnClickListener(view -> {
            startActivity(login);
        });

        btnCad.setOnClickListener(view -> {
            startActivity(cadasro);
        });
    }
}