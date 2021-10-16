package com.tachnologies.myligapro.common.model.dataSession;

import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;

public class AdmSession {
    private static UsuarioAdmin adminLogeado;
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
        private static final AdmSession INSTANCE = new AdmSession();
    }

    public static AdmSession getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /** ------------------getters setters */
    public static UsuarioAdmin getAdminLogeado() {
        return adminLogeado;
    }

    public static void setAdminLogeado(UsuarioAdmin adminLogeado) {
        AdmSession.adminLogeado = adminLogeado;
    }
    public static String getIdCuentaSel() {
        return idCuentaSel;
    }
    public static void setIdCuentaSel(String idCuentaSel) {
        AdmSession.idCuentaSel = idCuentaSel;
    }
    public static String getIdCanchaSel() {
        return idCanchaSel;
    }
    public static void setIdCanchaSel(String idCanchaSel) {
        AdmSession.idCanchaSel = idCanchaSel;
    }
    public static String getIdLigaSel() {
        return idLigaSel;
    }
    public static void setIdLigaSel(String idLigaSel) {
        AdmSession.idLigaSel = idLigaSel;
    }
    public static String getNombreCanchaSel() {
        return nombreCanchaSel;
    }
    public static void setNombreCanchaSel(String nombreCanchaSel) {
        AdmSession.nombreCanchaSel = nombreCanchaSel;
    }
    public static String getNombreLigaSel() {
        return nombreLigaSel;
    }
    public static void setNombreLigaSel(String nombreLigaSel) {
        AdmSession.nombreLigaSel = nombreLigaSel;
    }
    public static String getUrlLogoLigaSel() {
        return urlLogoLigaSel;
    }
    public static void setUrlLogoLigaSel(String urlLogoLigaSel) {
        AdmSession.urlLogoLigaSel = urlLogoLigaSel;
    }
    public static String getUrlLogoCanchaSel() {
        return urlLogoCanchaSel;
    }
    public static void setUrlLogoCanchaSel(String urlLogoCanchaSel) {
        AdmSession.urlLogoCanchaSel = urlLogoCanchaSel;
    }
    public static String getIdEquipoSel() {
        return idEquipoSel;
    }
    public static void setIdEquipoSel(String idEquipoSel) {
        AdmSession.idEquipoSel = idEquipoSel;
    }
    public static String getNombreEquipoSel() {
        return nombreEquipoSel;
    }
    public static void setNombreEquipoSel(String nombreEquipoSel) {
        AdmSession.nombreEquipoSel = nombreEquipoSel;
    }
    public static String getUrlLogoEquipoSel() {
        return urlLogoEquipoSel;
    }
    public static void setUrlLogoEquipoSel(String urlLogoEquipoSel) {
        AdmSession.urlLogoEquipoSel = urlLogoEquipoSel;
    }
}
