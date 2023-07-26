package com.example.recyclerviewdb;

public class Utilidades {

    public static String TABLA_DESCARGAS = "descargas";
    public static String CAMPO_CODIGO = "codigo";
    public static String CAMPO_NOMBRE = "nombre";
    public static String CAMPO_RUTA = "ruta";
    public static String CAMPO_FECHA = "fecha";
    public static String CAMPO_URLWEBDATA = "urlwebdata";
    public static String CAMPO_ESTADOPDF = "estadopdf";


    public static  final String CREAR_TABLA_DESCARGAS = "CREATE TABLE " + TABLA_DESCARGAS + "("+CAMPO_CODIGO+" TEXT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_RUTA+" TEXT, "+CAMPO_FECHA+" TEXT, "+CAMPO_URLWEBDATA+" TEXT, "+CAMPO_ESTADOPDF+" TEXT)";


}
