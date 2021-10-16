package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;

import java.util.Map;

public class ItemCanchaListado {
    @Exclude
    private String idCancha;
    @Exclude
    private String idCuenta;
    private String nombre;
    private String urlFoto;
    private String direccion;

    private Map<String, ItemLigaListado> ligas;

    public ItemCanchaListado(){}

    public ItemCanchaListado(String idCancha, String nombre, String urlFoto, String direccion,
                             Map<String, ItemLigaListado> ligas, String idCuenta){
        this.idCancha = idCancha;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.direccion = direccion;
        this.ligas = ligas;
        this.idCuenta = idCuenta;
    }

    @Exclude
    public String getIdCancha() {
        return idCancha;
    }
    @Exclude
    public void setIdCancha(String idCancha) {
        this.idCancha = idCancha;
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

    public Map<String, ItemLigaListado> getLigas() {
        return ligas;
    }

    public void setLigas(Map<String, ItemLigaListado> ligas) {
        this.ligas = ligas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }
}
