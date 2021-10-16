package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UsuarioAdmin implements Serializable {

    @Exclude
    private String uid;
    private String nombre;
    private String urlFoto;
    private MiCuentaAdmin miCuenta;
    private Map<String, CuentaAdminInvitado> cuentasInvitado;

    /** --------------------------- Datos Control*/
    /** tipos de estatus
     * A = alta
     * B = baja
     * */
    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public UsuarioAdmin() { }

    /** Alta de un nuevo usuario Admin*/
    public UsuarioAdmin(String uid) {
        this.uid = uid;
        this.fechaAlta = Utilidades.fechaHoyFormateada();
        this.usuarioAlta = uid;
        this.estatus = "A";
    }

    public Map<String, Object> getObjectoParaInsertar(){
        Map<String, Object> valores = new HashMap<>();
        if(nombre != null && !nombre.isEmpty()){
            valores.put(Constantes.NOMBRE, nombre);
        }
        if(urlFoto != null && !urlFoto.isEmpty()){
            valores.put(Constantes.URL_FOTO, urlFoto);
        }
        if(miCuenta != null ){
            valores.put(Constantes.MI_CUENTA, miCuenta);
        }
        if(cuentasInvitado != null && !cuentasInvitado.isEmpty()){
            valores.put(Constantes.CUENTAS_INVITADO, cuentasInvitado);
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
    public String getEstatus() {
        return estatus;
    }
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    public MiCuentaAdmin getMiCuenta() {
        return miCuenta;
    }
    public void setMiCuenta(MiCuentaAdmin miCuenta) {
        this.miCuenta = miCuenta;
    }
    public Map<String, CuentaAdminInvitado> getCuentasInvitado() {
        return cuentasInvitado;
    }
    public void setCuentasInvitado(Map<String, CuentaAdminInvitado> cuentasInvitado) {
        this.cuentasInvitado = cuentasInvitado;
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

}
