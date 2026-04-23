package com.example.boaviagem;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.ListIterator;

public class MinhasviagensActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_minhasviagens);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        this.databaseHelper = new DatabaseHelper(this);

        SharedPreferences s = getSharedPreferences("refs", MODE_PRIVATE);
        Integer id = s.getInt("id", 0);

        SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        List<Viagem> lista = this.databaseHelper.getViagens(db, id);
        ListView listView = findViewById(R.id.viagemLista);
        TextView empty = findViewById(R.id.empty);

        listView.setEmptyView(empty);

        ArrayAdapter<Viagem> adapter = new ArrayAdapter<Viagem>(this, android.R.layout.simple_list_item_1, lista);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Viagem idViagem = lista.get(position);
                try {
                    databaseHelper.onDelete(db, idViagem.getId(), "viagem");
                    Toast.makeText(MinhasviagensActivity.this, "Viagem deletada com Sucesso!", Toast.LENGTH_SHORT).show();
                    lista.remove(position);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    Toast.makeText(MinhasviagensActivity.this, "Erro ao deletar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}