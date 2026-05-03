package com.example.boaviagem;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NovaviagemActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novaviagem);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        this.databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        SharedPreferences s = getSharedPreferences("refs", MODE_PRIVATE);


        TextInputEditText chegada = findViewById(R.id.editDataChegada);
        TextInputEditText destino = findViewById(R.id.editDestino);
        TextInputEditText saida = findViewById(R.id.editDataSaida);
        TextInputEditText orcamento = findViewById(R.id.editOrcamento);
        TextInputEditText pessoas = findViewById(R.id.editQtdPessoas);
        Button salvar = findViewById(R.id.btnSalvar);
        TextInputLayout layoutChegada = findViewById(R.id.layoutDataChegada);
        TextInputLayout layoutDestino = findViewById(R.id.layoutDestino);
        TextInputLayout layoutSaida = findViewById(R.id.layoutDataSaida);
        TextInputLayout layoutOrcamento = findViewById(R.id.layoutOrcamento);
        TextInputLayout layoutPessoas = findViewById(R.id.layoutQtdPessoas);
        RadioGroup radioGroup = findViewById(R.id.GroupTipo);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int checked) {
                RadioButton selected = findViewById(checked);
                String text = selected.getText().toString();

                Toast.makeText(NovaviagemActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
        int init = radioGroup.getCheckedRadioButtonId();

        if (init != -1){
            RadioButton selected = findViewById(init);
            String text = selected.getText().toString();
            Toast.makeText(NovaviagemActivity.this, String.valueOf(text), Toast.LENGTH_SHORT).show();
        }
        Integer id = s.getInt("id", 0);
        salvar.setOnClickListener(view -> {
            if(destino.getText().toString().isEmpty()){
                layoutDestino.setError("Campo Obrigatório!");
                destino.requestFocus();
                return;
            }else{
                layoutDestino.setError(null);
            }

            if(chegada.getText().toString().isEmpty()){
                layoutChegada.setError("Campo Obrigatório!");
                chegada.requestFocus();
                return;
            }else{
                layoutChegada.setError(null);
            }

            if(saida.getText().toString().isEmpty()){
                layoutSaida.setError("Campo Obrigatório!");
                saida.requestFocus();
                return;
            }else{
                layoutSaida.setError(null);
            }
            if(orcamento.getText().toString().isEmpty()){
                layoutOrcamento.setError("Campo Obrigatório!");
                orcamento.requestFocus();
                return;
            }else{
                layoutOrcamento.setError(null);
            }

            if(pessoas.getText().toString().isEmpty()){
                layoutPessoas.setError("Campo Obrigatório!");
                pessoas.requestFocus();
                return;
            }else{
                layoutPessoas.setError(null);
            }

            DateTimeFormatter enter = null;
            String dataChegada = "";
            String dataSaida = "";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data1 = LocalDate.parse(chegada.getText().toString(), enter);
                LocalDate data2 = LocalDate.parse(saida.getText().toString(), enter);
                dataChegada = data1.toString();
                dataSaida = data2.toString();
            }

            RadioButton selected = findViewById(init);
            String text = selected.getText().toString().toLowerCase();
            int t  = (text.equals("lazer") ? 1 : (text.equals("trabalho") ? 2 : 0));
            ContentValues values = new ContentValues();
            values.put("destino", destino.getText().toString());
            values.put("tipo_viagem", t);
            values.put("data_chegada", dataChegada);
            values.put("data_saida", dataSaida);
            values.put("orcamento", Double.parseDouble(orcamento.getText().toString()));
            values.put("quantidade_pessoas", Integer.parseInt(pessoas.getText().toString()));
            values.put("id_user", id);

            try {
                this.databaseHelper.createViagem(db, values);
                Toast.makeText(NovaviagemActivity.this, "Viagem Registrada com Sucesso!", Toast.LENGTH_SHORT).show();
            }catch (Exception ignored){
                Toast.makeText(NovaviagemActivity.this, "Erro ao registrar viagem!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}