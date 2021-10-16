package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import com.tachnologies.myligapro.common.utils.Utilidades;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Cancha implements Serializable {

    @Exclude
    private String uid;
    private String nombre;
    private String direccion;
    private Ubicacion ubicacion;
    private List<String> urlFotosCancha;
    private String urlFotoLogo;
    private Map<String, Liga> ligas;
    private List<String> admins;

    /** -------------------- Datos Control */
    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public Cancha() { }

    public Cancha(String nombre, List<String> admins, String usuarioAlta) {
        this.nombre = nombre;
        this.admins = admins;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = Utilidades.fechaHoyFormateada();
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
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public List<String> getUrlFotosCancha() {
        return urlFotosCancha;
    }
    public void setUrlFotosCancha(List<String> urlFotosCancha) {
        this.urlFotosCancha = urlFotosCancha;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getUrlFotoLogo() {
        return urlFotoLogo;
    }
    public void setUrlFotoLogo(String urlFotoLogo) {
        this.urlFotoLogo = urlFotoLogo;
    }

    public Map<String, Liga> getLigas() {
        return ligas;
    }

    public void setLigas(Map<String, Liga> ligas) {
        this.ligas = ligas;
    }

    public List<String> getAdmins() {
        return admins;
    }
    public void setAdmins(List<String> admins) {
        this.admins = admins;
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
