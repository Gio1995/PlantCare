package com.example.applicazione;

public class Pianta {
    static String nome;
    static String descrizione;

    private Pianta(){}

    public static Pianta getPianta(){
        return new Pianta();
    }

    public static void setNome(String n){
        nome = n;
    }

    public static void setDescrizione(String d){
        descrizione = d;
    }

    public String getNome(){
        return nome;
    }

    public String getDescrizione(){
        return descrizione;
    }
}
