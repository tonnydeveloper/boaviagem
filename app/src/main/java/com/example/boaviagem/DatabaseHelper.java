package com.example.boaviagem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private  static final String DATABASE = "boa_viagem";
    private  static int VERSION = 1;

    public  DatabaseHelper(Context context){
        super(context, DATABASE, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS usuario(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT, email TEXT, senha TEXT, phone TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS viagem (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, destino TEXT, tipo_viagem INTEGER, data_chegada DATE," +
                "data_saida DATE, orcamento DOUBLE, quantidade_pessoas INTEGER, id_user INTEGER, " +
                "FOREIGN KEY(id_user) REFERENCES usuario(_id));");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS gasto (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "categoria TEXT, data DATE, valor DOUBLE, descicao TEXT, local TEXT," +
                "viagem_id INTEGER, FOREIGN KEY(viagem_id) REFERENCES viagem(_id));");
    }
    @Override
    public  void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

    }

    public void onDelete(SQLiteDatabase sqLiteDatabase, Integer id, String tabela){
        sqLiteDatabase.execSQL("DELETE FROM "+tabela+" WHERE _id = ?", new String[]{String.valueOf(id)});
    }

    public void insertUser(SQLiteDatabase db, ContentValues values){
        db.insert("usuario", null, values);
    }
    public void createViagem(SQLiteDatabase db, ContentValues values){
        db.insert("viagem", null, values);
    }

    public boolean getUserExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuario WHERE email = ?", new String[]{email});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }

    public List<String> login(SQLiteDatabase db, String email, String senha){
        Cursor cursor = db.rawQuery("SELECT email, senha FROM usuario WHERE email = ? AND senha = ?", new String[]{email, senha});
        List<String> login = new ArrayList<>();

        if(cursor.moveToFirst()){
            login.add(cursor.getString(0));
            login.add(cursor.getString(1));
        }

        cursor.close();
        return login;
    }

    public List<Viagem> getViagens(SQLiteDatabase db, int id) {
        List<Viagem> viagemLista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM viagem WHERE id_user = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                int idV = cursor.getInt(0);
                String destino = cursor.getString(1);
                int tipo = cursor.getInt(2);
                String chegada = cursor.getString(3);
                String saida = cursor.getString(4);
                float orcamento = cursor.getFloat(5);
                int pessoas = cursor.getInt(6);

                String tipoTexto = (tipo == 1) ? "Lazer" : "Negócios";

                String dados = "Destino: " + destino + "\n" +
                        "Tipo: " + tipoTexto + " | Pessoas: " + pessoas + "\n" +
                        "De: " + chegada + " até " + saida + "\n" +
                        "Orçamento: R$ " + orcamento;

                viagemLista.add(new Viagem(idV, dados));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return viagemLista;
    }

    public Integer getIdUser(SQLiteDatabase db, String email){
        Cursor cursor = db.rawQuery("SELECT _id, nome FROM usuario WHERE email = ?", new String[]{email});
        Integer id = null;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        return id;
    }

    public  String getNomeUser(SQLiteDatabase db, String email){
        Cursor cursor = db.rawQuery("SELECT nome FROM usuario WHERE email = ?", new String[]{email});
        String nome = "";

        if(cursor.moveToFirst()){
            nome = cursor.getString(0);
        }

        cursor.close();
        return nome;
    }
}
