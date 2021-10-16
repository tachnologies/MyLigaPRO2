package com.tachnologies.myligapro.common.pojo;

public class RefEquipoDelegado {
    private String uidCancha;
    private String nombreCancha;
    private String urlLogoCancha;
    private String uidEquipo;
    private String nombreEquipo;
    private String urlLogoEquipo;
    private String uidLiga;
    private String nombreLiga;
    private String urlLogoLiga;

    private String uidCuenta;

    public RefEquipoDelegado() {
    }

    public RefEquipoDelegado(String uidCancha, String nombreCancha, String urlLogoCancha,
            String uidEquipo, String nombreEquipo, String urlLogoEquipo, String uidLiga,
            String nombreLiga, String urlLogoLiga, String uidCuenta) {

        this.uidCancha = uidCancha;
        this.nombreCancha = nombreCancha;
        this.urlLogoCancha = urlLogoCancha;
        this.uidEquipo = uidEquipo;
        this.nombreEquipo = nombreEquipo;
        this.urlLogoEquipo = urlLogoEquipo;
        this.uidLiga = uidLiga;
        this.nombreLiga = nombreLiga;
        this.urlLogoLiga = urlLogoLiga;
        this.uidCuenta = uidCuenta;
    }

    public String getUidCancha() {
        return uidCancha;
    }
    public void setUidCancha(String uidCancha) {
        this.uidCancha = uidCancha;
    }
    public String getNombreCancha() {
        return nombreCancha;
    }
    public void setNombreCancha(String nombreCancha) {
        this.nombreCancha = nombreCancha;
    }
    public String getUrlLogoCancha() {
        return urlLogoCancha;
    }
    public void setUrlLogoCancha(String urlLogoCancha) {
        this.urlLogoCancha = urlLogoCancha;
    }
    public String getUidEquipo() {
        return uidEquipo;
    }
    public void setUidEquipo(String uidEquipo) {
        this.uidEquipo = uidEquipo;
    }
    public String getNombreEquipo() {
        return nombreEquipo;
    }
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
    public String getUrlLogoEquipo() {
        return urlLogoEquipo;
    }
    public void setUrlLogoEquipo(String urlLogoEquipo) {
        this.urlLogoEquipo = urlLogoEquipo;
    }
    public String getUidLiga() {
        return uidLiga;
    }
    public void setUidLiga(String uidLiga) {
        this.uidLiga = uidLiga;
    }
    public String getNombreLiga() {
        return nombreLiga;
    }
    public void setNombreLiga(String nombreLiga) {
        this.nombreLiga = nombreLiga;
    }
    public String getUrlLogoLiga() {
        return urlLogoLiga;
    }
    public void setUrlLogoLiga(String urlLogoLiga) {
        this.urlLogoLiga = urlLogoLiga;
    }
    public String getUidCuenta() {
        return uidCuenta;
    }
    public void setUidCuenta(String uidCuenta) {
        this.uidCuenta = uidCuenta;
    }
}
