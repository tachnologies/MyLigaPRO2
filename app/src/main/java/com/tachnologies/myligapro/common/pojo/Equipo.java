package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import com.tachnologies.myligapro.common.utils.Constantes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipo {
    @Exclude
    private String uid;
    private String nombre;
    private String urlFoto;
    private List<RefDelegadoAdm> delegados;
    private Map<String, Jugador> jugadores;

    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public Equipo(){}

    public Equipo(String uidEquipo){
        this.uid = uidEquipo;
    }

    public Equipo(String uidEquipo, String urlFoto){
        this.uid = uidEquipo;
        this.urlFoto = urlFoto;
    }

    public Equipo(String uid, String nombre, String urlFoto, List<RefDelegadoAdm> delegados,
                  Map<String, Jugador> jugadores) {
        this.uid = uid;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.delegados = delegados;
        this.jugadores = jugadores;
    }

    public Map<String, Object> getObjectoParaInsertar(){
        Map<String, Object> valores = new HashMap<>();

        if(nombre != null && !nombre.isEmpty()){
            valores.put(Constantes.NOMBRE, nombre);
        }

        valores.put(Constantes.URL_FOTO, urlFoto);
        valores.put(Constantes.DELEGADOS, delegados);

        if(jugadores != null && !jugadores.isEmpty()){
            valores.put(Constantes.JUGADORES, jugadores);
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
    public List<RefDelegadoAdm> getDelegados() {
        return delegados;
    }
    public void setDelegados(List<RefDelegadoAdm> delegados) {
        this.delegados = delegados;
    }
    public Map<String, Jugador> getJugadores() {
        return jugadores;
    }
    public void setJugadores(Map<String, Jugador> jugadores) {
        this.jugadores = jugadores;
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
