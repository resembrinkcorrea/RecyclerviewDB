package com.example.recyclerviewdb.Model;

public class Descargas {

    private String codigo;
    private String nombre;
    private String ruta;
    private String fecha;
    private String urlwebdata;
    private String estadopdf;
    private boolean estadocheck;

    public Descargas() {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ruta = ruta;
        this.fecha = fecha;
        this.urlwebdata = urlwebdata;
        this.estadopdf = estadopdf;
        this.estadocheck = estadocheck;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrlwebdata() {
        return urlwebdata;
    }

    public void setUrlwebdata(String urlwebdata) {
        this.urlwebdata = urlwebdata;
    }

    public String getEstadopdf() {
        return estadopdf;
    }

    public void setEstadopdf(String estadopdf) {
        this.estadopdf = estadopdf;
    }

    public boolean isEstadocheck() {
        return estadocheck;
    }

    public void setEstadocheck(boolean estadocheck) {
        this.estadocheck = estadocheck;
    }

    @Override
    public String toString() {
        return "Descargas{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                ", fecha='" + fecha + '\'' +
                ", urlwebdata='" + urlwebdata + '\'' +
                ", estadopdf='" + estadopdf + '\'' +
                ", estadocheck=" + estadocheck +
                '}';
    }
}
