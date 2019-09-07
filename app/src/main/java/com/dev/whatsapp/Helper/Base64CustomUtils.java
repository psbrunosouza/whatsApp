package com.dev.whatsapp.Helper;

import android.util.Base64;

public class Base64CustomUtils {

    //Metodo utilizado para conversao do email do usuario em um id em base64
    //A funcao Base64 da classe utils, traz o metodo encodeToString, para conversao de uma string
    //essa string e inicialmente convertida em bytes, utilizando o metodo DEFAULT de conversao
    //finalizando com substituicao dos espacos vazios (\\n) no inicio e (\\r) no final do texto

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    //Metodo utilizado para decodificacao do ID do usuario em seu email
    //Eh criado uma String que é o retorno solicitado pela funcao, a funcao Base64.decode realiza o processo
    //de decodificacao, passando o texto codificado e como segundo parametro o metodo de decodificacao DEFAULT

    public static String decodificarBase64(String textoCodificado){
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }
}
