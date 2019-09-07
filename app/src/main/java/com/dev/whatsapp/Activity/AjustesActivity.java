package com.dev.whatsapp.Activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dev.whatsapp.Helper.Permissao;
import com.dev.whatsapp.R;

public class AjustesActivity extends AppCompatActivity {

    public String[] permissoesNecessarias = new String[]{
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ajustes");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Permissao.validarPermissao(permissoesNecessarias, this, 1);
    }
}
