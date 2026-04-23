package com.example.boaviagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);


        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SharedPreferences s = getSharedPreferences("refs", MODE_PRIVATE);

        MaterialButton btnGastos = findViewById(R.id.btnGasto);
        ImageButton logout = findViewById(R.id.btnLogout);
        MaterialButton btnNovaViagem = findViewById(R.id.btnNovaViagem);
        MaterialButton btnViagens = findViewById(R.id.minhasViagens);
        MaterialButton btnmGastos = findViewById(R.id.gastos);
        TextView v = (TextView) findViewById(R.id.teste);

        v.setText(s.getString("nome", ""));

        btnGastos.setOnClickListener(view -> {
            Intent gastos = new Intent(this, GastosActivity.class);
            startActivity(gastos);
        });

        btnNovaViagem.setOnClickListener(view -> {
            Intent nViagem = new Intent(this, NovaviagemActivity.class);
            startActivity(nViagem);
        });

        btnViagens.setOnClickListener(view -> {
            Intent viagens = new Intent(this, MinhasviagensActivity.class);

            startActivity(viagens);
        });

        btnmGastos.setOnClickListener(view -> {
            Intent gastos = new Intent(this, MeugastosActivity.class);

            startActivity(gastos);
        });

        logout.setOnClickListener(view -> {

            SharedPreferences.Editor edit = s.edit();

            edit.putBoolean("logged", false);
            edit.apply();

            Intent login = new Intent(this, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(login);
            finish();
        });
    }
}