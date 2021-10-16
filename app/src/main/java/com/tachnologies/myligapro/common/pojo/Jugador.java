package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import com.tachnologies.myligapro.common.utils.Constantes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Jugador implements Serializable {
    @Exclude
    private String uid;
    private String nombre;
    private String apellido;
    private String urlFoto;
    private String apodo;
    //private String posicion;
    //private int numeroJugador;
    private int fanPoints;

    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public Map<String, Object> getObjectoParaInsertar(){
        Map<String, Object> valores = new HashMap<>();

        valores.put(Constantes.NOMBRE, nombre);
        valores.put(Constantes.APELLIDO, apellido);
        valores.put(Constantes.URL_FOTO, urlFoto);

        if(apodo != null && !apodo.isEmpty()){
            valores.put(Constantes.APODO, apodo);
        }
        if(fanPoints > 0){
            valores.put(Constantes.FAN_POINTS, fanPoints);
        }
        if(fechaAlta != null && !fechaAlta.isEmpty()){
            valores.put(Constantes.FECHA_ALTA, fechaAlta);
        }
        if(usuarioAlta != null && !usuarioAlta.isEmpty()){
            valores.put(Constantes.USUARIO_ALTA, usuarioAlta);
        }
        if(fechaModificacion != null && !fechaModificacion.isEmpty()){
            valores.put(Constantes.FECHA_MODIFICACION, fechaModificacion);
        }
        if(usuarioModificacion != null && !usuarioModificacion.isEmpty()){
            valores.put(Constantes.USUARIO_MODIFICACION, usuarioModificacion);
        }
        if(estatus != null && !estatus.isEmpty()){
            valores.put(Constantes.ESTATUS, estatus);
        }
        return valores;
    }

    public Jugador() { }

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
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getUrlFoto() {
        return urlFoto;
    }
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    public int getFanPoints() {
        return fanPoints;
    }
    public void setFanPoints(int fanPoints) {
        this.fanPoints = fanPoints;
    }
    public String getApodo() {
        return apodo;
    }
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }
    public String getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public String getUsuarioAlta() {
        return usuarioAlta;
    }
    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }
    public String getFechaModificacion() {
        return fechaModificacion;
    }
    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
    public String getEstatus() {
        return estatus;
    }
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
