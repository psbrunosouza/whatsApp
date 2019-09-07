package com.dev.whatsapp.Model;

import com.dev.whatsapp.Database.DB_DAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    /*
     * CLASSE que define os dados do usuario necessarios
     * para seu cadastro e acesso a aplicacao
     */

    private String id, nome, email, senha;
    private DatabaseReference dbRef = DB_DAO.getDbReferencia();

    public Usuario() {
    }

    public void salvarUsuario(){
        DatabaseReference usuario = dbRef.child("usuarios");
        usuario.child(this.id).setValue(this);
    }

    public String getNome() {
        return nome;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
