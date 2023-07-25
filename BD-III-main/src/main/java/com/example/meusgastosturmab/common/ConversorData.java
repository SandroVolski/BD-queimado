package com.example.meusgastosturmab.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorData {
    public static String converterDateParaDataHora(Date data){ //static -> n precisa ter uma instancia da data para converter a data
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        return formatador.format(data);
    }
}
