package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import com.tachnologies.myligapro.common.utils.Utilidades;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Cuenta {
    @Exclude
    private String uid;
    /** Este es el chido para poder calcular los precios*/
    private String tipoCuenta;
    private Date fechaProximoPago;

    private List<Pago>pagos;
    private Map<String, Cancha> canchas;

    /** -------------------- Datos Control */
    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public Cuenta(){}

    public Cuenta(String uidAdmin, String tipoCuenta){
        this.tipoCuenta = tipoCuenta;
        this.estatus = "A";
        this.fechaAlta = Utilidades.fechaHoyFormateada();
        this.usuarioAlta = uidAdmin;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    public Date getFechaProximoPago() {
        return fechaProximoPago;
    }
    public void setFechaProximoPago(Date fechaProximoPago) {
        this.fechaProximoPago = fechaProximoPago;
    }
    public List<Pago> getPagos() {
        return pagos;
    }
    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public Map<String, Cancha> getCanchas() {
        return canchas;
    }

    public void setCanchas(Map<String, Cancha> canchas) {
        this.canchas = canchas;
    }
}
