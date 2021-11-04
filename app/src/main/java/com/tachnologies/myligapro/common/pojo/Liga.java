package com.tachnologies.myligapro.common.pojo;

import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.Exclude;

public class Liga implements Serializable {
    @Exclude
    private String uid;
    private String nombre;
    private List<String> dias;
    private String urlFoto;

    /** Aqui podra tener el genero
     * V = Varonil
     * F = Femenil
     * M = Mixto*/
    private String genero;
    /** Aqui podra tener el tipo de torneo
     * T=torneo primer lugar es campeon
     * L=liga con finales
     * */
    private String tipoTorneo;
    //private int numJornadas;
    private int jornadaActual;
    private int equiposCalifican;
    //private boolean finalEsFecha1;
    private boolean empateJuegaPuntoExtra;

    private List<Equipo> equipos;
    private List<Sancionado> sancionados;
    private List<Goleadores> goleo;
    private List<Jornada> jornadas;
    private List<Nota> notas;

    //private List<String> delegados;
    private List<String> adminsInvitados;

    private boolean tieneLimiteRegistros;
    private int cantidadRegistros;
    private boolean hayRepechaje;
    private int equiposRepechaje;
    private boolean finalesConIdaYVuelta;
    /** -------------------- Datos Control */
    private String fechaAlta;
    private String usuarioAlta;
    private String fechaModificacion;
    private String usuarioModificacion;
    private String estatus;

    public Liga(){}

    public Liga(String uid, String nombre, String uidAdmin){
        this.uid = uid;
        this.nombre = nombre;
        this.usuarioAlta = uidAdmin;
        this.fechaAlta = Utilidades.fechaHoyFormateada();
    }

    public Map<String, Object> getObjectoParaInsertar(){
        Map<String, Object> valores = new HashMap<>();

        valores.put(Constantes.NOMBRE, nombre);
        valores.put(Constantes.DIAS, dias);
        valores.put(Constantes.URL_FOTO, urlFoto);

        valores.put(Constantes.GENERO, genero);
        valores.put(Constantes.TIPO_TORNEO, tipoTorneo);
        if(jornadaActual > 0){
            valores.put(Constantes.JORNADA_ACTUAL, jornadaActual);
        }
        if(equiposCalifican > 0){
            valores.put(Constantes.EQUIPOS_CALIFICAN, equiposCalifican);
        }

        valores.put(Constantes.EMPATE_PUNTO_EXTRA, empateJuegaPuntoExtra);

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
    public int getJornadaActual() {
        return jornadaActual;
    }
    public void setJornadaActual(int jornadaActual) {
        this.jornadaActual = jornadaActual;
    }
    public int getEquiposCalifican() {
        return equiposCalifican;
    }
    public void setEquiposCalifican(int equiposCalifican) {
        this.equiposCalifican = equiposCalifican;
    }
    /*public boolean isFinalEsFecha1() {
        return finalEsFecha1;
    }

    public void setFinalEsFecha1(boolean finalEsFecha1) {
        this.finalEsFecha1 = finalEsFecha1;
    }*/
    public boolean isEmpateJuegaPuntoExtra() {
        return empateJuegaPuntoExtra;
    }
    public void setEmpateJuegaPuntoExtra(boolean empateJuegaPuntoExtra) {
        this.empateJuegaPuntoExtra = empateJuegaPuntoExtra;
    }
    public List<Equipo> getEquipos() {
        return equipos;
    }
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    public List<Sancionado> getSancionados() {
        return sancionados;
    }
    public void setSancionados(List<Sancionado> sancionados) {
        this.sancionados = sancionados;
    }
    public List<Goleadores> getGoleo() {
        return goleo;
    }
    public void setGoleo(List<Goleadores> goleo) {
        this.goleo = goleo;
    }
    public List<Jornada> getJornadas() {
        return jornadas;
    }
    public void setJornadas(List<Jornada> jornadas) {
        this.jornadas = jornadas;
    }
    public List<Nota> getNotas() {
        return notas;
    }
    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
    public List<String> getAdminsInvitados() {
        return adminsInvitados;
    }
    public void setAdminsInvitados(List<String> adminsInvitados) {
        this.adminsInvitados = adminsInvitados;
    }
    public boolean isTieneLimiteRegistros() {
        return tieneLimiteRegistros;
    }
    public void setTieneLimiteRegistros(boolean tieneLimiteRegistros) {
        this.tieneLimiteRegistros = tieneLimiteRegistros;
    }
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }
    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }
    public boolean isHayRepechaje() {
        return hayRepechaje;
    }
    public void setHayRepechaje(boolean hayRepechaje) {
        this.hayRepechaje = hayRepechaje;
    }
    public String getEstatus() {
        return estatus;
    }
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    public int getEquiposRepechaje() {
        return equiposRepechaje;
    }
    public void setEquiposRepechaje(int equiposRepechaje) {
        this.equiposRepechaje = equiposRepechaje;
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

   /* public List<String> getDelegados() {
        return delegados;
    }

    public void setDelegados(List<String> delegados) {
        this.delegados = delegados;
    }*/

    public List<String> getDias() {
        return dias;
    }
    public void setDias(List<String> dias) {
        this.dias = dias;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getTipoTorneo() {
        return tipoTorneo;
    }
    public void setTipoTorneo(String tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }
}
