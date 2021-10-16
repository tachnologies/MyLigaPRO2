package com.tachnologies.myligapro.common.pojo;

public class ItemLadaListado {
    private String icono;
    private String pais;
    private String lada;

    public ItemLadaListado(String icono, String pais, String lada){
        this.icono = icono;
        this.pais = pais;
        this.lada = lada;
    }

    public String getIcono() {
        return icono;
    }
    public void setIcono(String icono) {
        this.icono = icono;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getLada() {
        return lada;
    }
    public void setLada(String lada) {
        this.lada = lada;
    }
}
