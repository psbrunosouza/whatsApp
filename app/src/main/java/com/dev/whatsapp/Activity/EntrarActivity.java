package com.dev.whatsapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.whatsapp.Database.DB_DAO;
import com.dev.whatsapp.Helper.ValidacaoAutenticao;
import com.dev.whatsapp.Model.Usuario;
import com.dev.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.security.Principal;

public class EntrarActivity extends AppCompatActivity implements View.OnClickListener{
    //COMPONENTES INTERFACE
    private TextView irCadastrar;
    private TextInputEditText txt_email, txt_senha;
    private Button bt_entrar;
    //DATABASE E AUTH
    private FirebaseAuth autenticacao = DB_DAO.getAutenticacao();
    //MODELOS
    private Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        //ATRIBUIR VARIÁVEIS AOS COMPONENTES DA INTERFACE
        irCadastrar = findViewById(R.id.bt_irCadastrar);
        txt_email = findViewById(R.id.txt_email);
        txt_senha = findViewById(R.id.txt_senha);
        bt_entrar = findViewById(R.id.bt_entrar);
        irCadastrar.setOnClickListener(this);
        bt_entrar.setOnClickListener(this);

        //DETERMINA O ESTILO DE ALGUNS CARACTERES
        SpannableStringBuilder spannable = new SpannableStringBuilder("Ainda não possui conta? Cadastre-se");
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)),
                24,
                35,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        irCadastrar.setText(spannable);
    }

    //FUNÇÃO PARA AÇÕES DE CLIQUE DO USUÁRIO (CADASTRO E PAG DE CADASTRO)
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_irCadastrar:
                startActivity(new Intent(EntrarActivity.this, CadastrarActivity.class));
                break;
            case R.id.bt_entrar:
                ValidacaoAutenticao validacao = new ValidacaoAutenticao(getApplicationContext());

                usuario.setEmail(txt_email.getText().toString());
                usuario.setSenha(txt_senha.getText().toString());

                validacao.setEmail(usuario.getEmail());
                validacao.setSenha(usuario.getSenha());

                if(validacao.validarAcesso()){
                    acessarConta(usuario);
                }
                break;
        }
    }

    //FUNÇÃO PARA CONTROLE E AUTENTICAÇÃO DO USUÁRIO
    public void acessarConta(Usuario usuario){
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(EntrarActivity.this, PrincipalActivity.class));
                        Toast.makeText(EntrarActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    }else{
                        String excecao;

                        try{
                            throw task.getException();
                        }catch(FirebaseAuthInvalidCredentialsException e){
                            excecao = "Formato do e-mail não permitido";
                        }catch(FirebaseAuthUserCollisionException e){
                            excecao = "E-mail ou senha incorreto.";
                        }catch(Exception e){
                            excecao = "Não foi possível autenticar o usuário";
                        }

                        Toast.makeText(EntrarActivity.this, excecao, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if(usuarioAtual != null){
            startActivity(new Intent(EntrarActivity.this, PrincipalActivity.class));
            finish();
        }
    }
}
