package com.dev.whatsapp.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.whatsapp.Database.DB_DAO;
import com.dev.whatsapp.Fragment.ContatosFragment;
import com.dev.whatsapp.Fragment.MensagensFragment;
import com.dev.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao = DB_DAO.getAutenticacao();
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);

        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
            getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Mensagens", MensagensFragment.class)
                .add("Contatos", ContatosFragment.class)
                .create()
        );

        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_opcoes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.sair:
                deslogarUsuario();
                break;

            case R.id.configuracoes:
                configuracoesTela();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){
        try{
            autenticacao.signOut();
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void configuracoesTela(){
        startActivity(new Intent(PrincipalActivity.this, AjustesActivity.class));
    }
}
