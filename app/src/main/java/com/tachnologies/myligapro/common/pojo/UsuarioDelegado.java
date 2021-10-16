package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioDelegado implements Serializable {

    @Exclude
    private String uid;
    private String nombre;
    private String lada;
    //private String correo; //si es que se agrega... ya veremos
    private String urlFoto;
    /**
     * es para la pantalla para listar los equipos del Delegado
     */
    private List<RefEquipoDelegado> equipos;

    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public Map<String, Object> getObjectoParaInsertar(){
        Map<String, Object> valores = new HashMap<>();

        if(nombre != null && !nombre.isEmpty()){
            valores.put(Constantes.NOMBRE, nombre);
        }
        if(lada != null && !lada.isEmpty()){
            valores.put(Constantes.LADA, lada);
        }
        if(urlFoto != null && !urlFoto.isEmpty()){
            valores.put(Constantes.URL_FOTO, urlFoto);
        }

        valores.put(Constantes.EQUIPOS, equipos);

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

    public UsuarioDelegado() { }

    public UsuarioDelegado(String uid, String lada, String nombre, String usuarioAlta, String fechaAlta,
                           String estatus) {
        this.uid = uid;
        this.lada = lada;
        this.nombre = nombre;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
        this.estatus = estatus;
    }

    @Exclude
    public String getUid() {
        return uid;
    }
    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getLada() {
        return lada;
    }
    public void setLada(String lada) {
        this.lada = lada;
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

    public List<RefEquipoDelegado> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<RefEquipoDelegado> equipos) {
        this.equipos = equipos;
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
