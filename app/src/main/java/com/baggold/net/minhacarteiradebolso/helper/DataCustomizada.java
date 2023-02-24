package com.baggold.net.minhacarteiradebolso.helper;

import java.text.SimpleDateFormat;

public class DataCustomizada {

    public static String dataAtual() {

        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(data);
    }

    public static String mesAnoDataEscolhida(String data) {

       String[] retornoData = data.split("/");
       String dia = retornoData[0];
       String mes = retornoData[1];
       String ano = retornoData[2];

        return mes + ano;
    }
}
