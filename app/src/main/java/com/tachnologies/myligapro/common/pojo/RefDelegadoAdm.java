package com.tachnologies.myligapro.common.pojo;

public class RefDelegadoAdm {
    private String nombre;
    private String urlFoto;
    private String uid;
    private String lada;

    public RefDelegadoAdm(String nombre, String urlFoto, String uid, String lada) {
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.uid = uid;
        this.lada = lada;
    }

    public RefDelegadoAdm() {
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
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getLada() {
        return lada;
    }
    public void setLada(String lada) {
        this.lada = lada;
    }
}
