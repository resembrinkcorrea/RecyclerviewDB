package com.example.recyclerviewdb.Model;

public class MListAppLocal {
    private int photoapp;
    private String nombre;
    private String packageName;

    public MListAppLocal(int photoapp, String nombre, String packageName) {
        this.photoapp = photoapp;
        this.nombre = nombre;
        this.packageName = packageName;
    }

    public int getPhotoapp() {
        return photoapp;
    }

    public void setPhotoapp(int photoapp) {
        this.photoapp = photoapp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
