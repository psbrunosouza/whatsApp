package com.dev.whatsapp.Helper;

import android.content.Context;
import android.widget.Toast;

public class ValidacaoAutenticao {
    private String nome, email, senha;
    private Context context;

    public ValidacaoAutenticao(Context context) {
        this.context = context;
    }

    public boolean validarAcesso(){
            if(!email.isEmpty()){
                if(!senha.isEmpty()){
                    return true;
                }else{
                    Toast.makeText(context, "Campo senha está vazio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, "Campo e-mail está vazio", Toast.LENGTH_SHORT).show();
            }

        return false;
    }

    public boolean validarCadastro(){
        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!senha.isEmpty()){
                    return true;
                }else{
                    Toast.makeText(context, "Campo senha está vazio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, "Campo e-mail está vazio", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Campo nome está vazio", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public String getNome() {
        return nome;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
