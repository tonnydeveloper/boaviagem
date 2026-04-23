package com.example.boaviagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        this.databaseHelper = new DatabaseHelper(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        TextInputEditText user = (TextInputEditText) findViewById(R.id.user);
        TextInputEditText pass = (TextInputEditText) findViewById(R.id.password);
        MaterialButton btnLogin = (MaterialButton) findViewById(R.id.btnLogin);
        TextInputLayout layoutUser = (TextInputLayout) findViewById(R.id.layoutUser);
        TextInputLayout layoutPass = (TextInputLayout) findViewById(R.id.layoutPassword);

        SharedPreferences s = getSharedPreferences("refs", MODE_PRIVATE);

        String u = s.getString("login", "");
        String p = s.getString("pass", "");
        if (!u.isEmpty() && !p.isEmpty()){
            user.setText(u);
            pass.setText(p);
            btnLogin.requestFocus();
        }

        btnLogin.setOnClickListener(view -> {
            String usuario = user.getText().toString();
            String password = pass.getText().toString();

            boolean valid = true;

            if (usuario.isEmpty()){
                layoutUser.setError("Campo Obrigatório!");
                user.requestFocus();
                valid = false;
            }else {
                layoutUser.setError(null);
            }
            if (password.isEmpty()){
                layoutPass.setError("Campo Obrigatório!");
                pass.requestFocus();
                valid = false;
            }else{
                layoutPass.setError(null);
            }

            SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
            List<String> result = databaseHelper.login(db, user.getText().toString(), pass.getText().toString());

            if(valid){
                if(!result.isEmpty()) {
                        SharedPreferences.Editor edit = s.edit();

                        Integer idUser = 0;
                        String nome = "";
                        try{
                            idUser = databaseHelper.getIdUser(db, user.getText().toString());
                            nome = databaseHelper.getNomeUser(db, user.getText().toString());
                        }catch (Exception ignored){
                            Toast.makeText(LoginActivity.this, "Erro ao buscar dados de usuario", Toast.LENGTH_SHORT).show();
                        }

                        edit.putBoolean("logged", true);
                        edit.putString("login", usuario);
                        edit.putString("pass", password);
                        edit.putInt("id", idUser);
                        edit.putString("nome", nome);
                        edit.apply();

                    Intent main = new Intent(this, DashboardActivity.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(main);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Credencias Invalidas!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}