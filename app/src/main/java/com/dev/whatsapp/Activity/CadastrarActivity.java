package com.dev.whatsapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.whatsapp.Database.DB_DAO;
import com.dev.whatsapp.Helper.ValidacaoAutenticao;
import com.dev.whatsapp.Model.Usuario;
import com.dev.whatsapp.R;
import com.dev.whatsapp.Helper.Base64CustomUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {

    //COMPONENTES E INTERFACE
    private TextInputEditText txt_nome, txt_email, txt_senha;
    private Button bt_cadastrar;
    //DATABASE E AUTH
    private FirebaseAuth autenticao = DB_DAO.getAutenticacao();
    private Usuario usuario = new Usuario();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        //ATRIBUIR VARIÁVEIS A INTERFACE
        txt_nome = findViewById(R.id.txt_nome);
        txt_email = findViewById(R.id.txt_email);
        txt_senha = findViewById(R.id.txt_senha);
        bt_cadastrar = findViewById(R.id.btCadastrar);

        //TRATAMENTO DE CLICK
        bt_cadastrar.setOnClickListener(this);
    }

    //CONTROLE DO BOTÃO DE CADASTRO
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btCadastrar:
                ValidacaoAutenticao validacao = new ValidacaoAutenticao(getApplicationContext());

                usuario.setNome(txt_nome.getText().toString());
                usuario.setEmail(txt_email.getText().toString());
                usuario.setSenha(txt_senha.getText().toString());

                validacao.setNome(usuario.getNome());
                validacao.setEmail(usuario.getEmail());
                validacao.setSenha(usuario.getSenha());

                if(validacao.validarCadastro()){
                    cadastrarUsuario();
                }
            break;
        }
    }

    //FUNÇÃO PARA CADASTRO DO USUÁRIO
    public void cadastrarUsuario(){
        autenticao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   try{
                       String idUsuario = Base64CustomUtils.codificarBase64(usuario.getEmail());
                       usuario.setId(idUsuario);
                       usuario.salvarUsuario();
                       startActivity(new Intent(CadastrarActivity.this, PrincipalActivity.class));
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }else{
                   String excecao;

                   try{
                       throw task.getException();
                   } catch(FirebaseAuthWeakPasswordException e){
                       excecao = "A senha digitada é muito fraca";
                   } catch(FirebaseAuthInvalidCredentialsException e){
                       excecao = "O formato do e-mail digitado não é permitido";
                   } catch(FirebaseAuthUserCollisionException e){
                       excecao = "O e-mail digitado, já está sendo utilizado por outra conta";
                   }catch(Exception e){
                       excecao = "Erro ao realizar cadastro";
                   }

                   Toast.makeText(CadastrarActivity.this, excecao, Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
