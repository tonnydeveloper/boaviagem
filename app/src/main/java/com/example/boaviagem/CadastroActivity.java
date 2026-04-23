package com.example.boaviagem;

import android.content.ContentValues;
import android.content.Intent;
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

public class CadastroActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        this.databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();

        TextInputEditText nome = findViewById(R.id.nome);
        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText phone = findViewById(R.id.telefone);
        TextInputEditText pass = findViewById(R.id.senha);
        MaterialButton btnCadastro = findViewById(R.id.btnCadastrar);
        TextInputLayout layoutNome = findViewById(R.id.layoutNome);
        TextInputLayout layoutEmail = findViewById(R.id.layoutEmail);
        TextInputLayout layoutTelefone = findViewById(R.id.layoutTelefone);
        TextInputLayout layoutPass = findViewById(R.id.layoutSenha);

        phone.addTextChangedListener(Mask.apply(phone));

        btnCadastro.setOnClickListener(view -> {

            if(nome.getText().toString().isEmpty()) {
                layoutNome.setError("Campo obrigatório");
                nome.requestFocus();
                return;
            }else {
                layoutNome.setError(null);
            }
            if(email.getText().toString().isEmpty()) {
                layoutEmail.setError("Campo obrigatório");
                email.requestFocus();
                return;
            }else {
                layoutEmail.setError(null);
            }
            if(phone.getText().toString().isEmpty()) {
                layoutTelefone.setError("Campo obrigatório");
                phone.requestFocus();
                return;
            }else {
                layoutTelefone.setError(null);
            }
            if(pass.getText().toString().isEmpty()) {
                layoutPass.setError("Campo obrigatório");
                pass.requestFocus();
                return;
            }else {
                layoutPass.setError(null);
            }

            if(databaseHelper.getUserExists(email.getText().toString())){
                Toast.makeText(CadastroActivity.this, "Email já registrado!", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();
            values.put("nome", nome.getText().toString());
            values.put("email", email.getText().toString());
            values.put("senha", pass.getText().toString());
            values.put("phone", phone.getText().toString());

            try {
                this.databaseHelper.insertUser(db, values);
                Toast.makeText(CadastroActivity.this, "Cadastro Efetuado!", Toast.LENGTH_SHORT).show();

                Intent login = new Intent(this, LoginActivity.class);
                // login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                finish();
            }catch (Exception ignored){
                Toast.makeText(CadastroActivity.this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}