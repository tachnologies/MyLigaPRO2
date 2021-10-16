package com.tachnologies.myligapro.common.model.dataSession;

import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public class DelSession {
    private static UsuarioDelegado delLogeado;
    private static String idCuentaSel;
    private static String idCanchaSel;
    private static String idLigaSel;
    private static String idEquipoSel;

    private static String nombreCanchaSel;
    private static String nombreLigaSel;
    private static String nombreEquipoSel;

    private static String urlLogoCanchaSel;
    private static String urlLogoLigaSel;
    private static String urlLogoEquipoSel;

    private static class SingletonHolder{
        private static final DelSession INSTANCE = new DelSession();
    }

    public static DelSession getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /** ------------------getters setters */
    public static UsuarioDelegado getDelLogeado() {
        return delLogeado;
    }
    public static void setDelLogeado(UsuarioDelegado delLogeado) {
        DelSession.delLogeado = delLogeado;
    }
    public static String getIdCuentaSel() {
        return idCuentaSel;
    }
    public static void setIdCuentaSel(String idCuentaSel) {
        DelSession.idCuentaSel = idCuentaSel;
    }
    public static String getIdCanchaSel() {
        return idCanchaSel;
    }
    public static void setIdCanchaSel(String idCanchaSel) {
        DelSession.idCanchaSel = idCanchaSel;
    }
    public static String getIdLigaSel() {
        return idLigaSel;
    }
    public static void setIdLigaSel(String idLigaSel) {
        DelSession.idLigaSel = idLigaSel;
    }
    public static String getIdEquipoSel() {
        return idEquipoSel;
    }
    public static void setIdEquipoSel(String idEquipoSel) {
        DelSession.idEquipoSel = idEquipoSel;
    }
    public static String getNombreCanchaSel() {
        return nombreCanchaSel;
    }
    public static void setNombreCanchaSel(String nombreCanchaSel) {
        DelSession.nombreCanchaSel = nombreCanchaSel;
    }
    public static String getNombreLigaSel() {
        return nombreLigaSel;
    }
    public static void setNombreLigaSel(String nombreLigaSel) {
        DelSession.nombreLigaSel = nombreLigaSel;
    }
    public static String getNombreEquipoSel() {
        return nombreEquipoSel;
    }
    public static void setNombreEquipoSel(String nombreEquipoSel) {
        DelSession.nombreEquipoSel = nombreEquipoSel;
    }
    public static String getUrlLogoCanchaSel() {
        return urlLogoCanchaSel;
    }
    public static void setUrlLogoCanchaSel(String urlLogoCanchaSel) {
        DelSession.urlLogoCanchaSel = urlLogoCanchaSel;
    }
    public static String getUrlLogoLigaSel() {
        return urlLogoLigaSel;
    }
    public static void setUrlLogoLigaSel(String urlLogoLigaSel) {
        DelSession.urlLogoLigaSel = urlLogoLigaSel;
    }
    public static String getUrlLogoEquipoSel() {
        return urlLogoEquipoSel;
    }
    public static void setUrlLogoEquipoSel(String urlLogoEquipoSel) {
        DelSession.urlLogoEquipoSel = urlLogoEquipoSel;
    }
}
