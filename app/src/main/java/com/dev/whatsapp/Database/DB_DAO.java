package com.dev.whatsapp.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DB_DAO {
    private static FirebaseAuth autenticacao;
    private static DatabaseReference dbReferencia;

    public static FirebaseAuth getAutenticacao(){

        if(autenticacao == null){
            return autenticacao = FirebaseAuth.getInstance();
        }else{
            return autenticacao;
        }
    }

    public static DatabaseReference getDbReferencia(){
        if(dbReferencia == null){
            return dbReferencia = FirebaseDatabase.getInstance().getReference();
        }else{
            return dbReferencia;
        }
    }
}
