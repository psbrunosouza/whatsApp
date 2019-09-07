package com.dev.whatsapp.Helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    //Metodo utilizado para verificar as permissoes, verificando inicialmente
    //se a versao do dispositivo e maior ou igual a versao marshmellow

    public static boolean validarPermissao(String[] permissaoCapturada, Activity activity, int requestCode){

        if(Build.VERSION.SDK_INT >= 23){
            List<String> listaPermissao = new ArrayList<>();

            for(String permissao : permissaoCapturada){
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) ==
                        PackageManager.PERMISSION_GRANTED;

                if(!temPermissao){
                    listaPermissao.add(permissao);
                }

                if(listaPermissao.isEmpty()) return true;

                String[] novasPermissoes = new String[listaPermissao.size()];
                listaPermissao.toArray(novasPermissoes);

                ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
            }
        }

        return true;
    }

}
