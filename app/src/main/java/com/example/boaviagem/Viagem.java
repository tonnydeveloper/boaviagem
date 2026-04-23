package com.example.boaviagem;

public class Viagem {;
    private int id;
    private String dadosFormatados;

    public Viagem(int id, String dadosFormatados){
        setId(id);
        setDadosFormatados(dadosFormatados);
    }

    private void setId(int id){
        this.id = id;
    }
    private  void setDadosFormatados(String d){
        this.dadosFormatados = d;
    }

    public int getId() {
        return  id;
    }

    public  String getDadosFormatados(){
        return  dadosFormatados;
    }

    @Override
    public String toString(){
        return this.dadosFormatados;
    }
}
