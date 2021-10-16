package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;

public class ItemLigaListado {

    @Exclude
    private String uid;
    private String nombre;
    private String urlFoto;

    public ItemLigaListado(){}

    public ItemLigaListado(String uid, String nombre, String urlFoto){
        this.uid = uid;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
    }

    @Exclude
    public String getUid() {
        return uid;
    }
    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUrlFoto() {
        return urlFoto;
    }
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
