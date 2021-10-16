package com.tachnologies.myligapro.moduloAdm.editarEquipo.model;

import android.net.Uri;

import com.tachnologies.myligapro.common.callbacks.BasicDelegadoCallback;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public interface EditarEquipoAdmInteractor {
    void consultarEquipo(String uidEquipo);
    void subirEscudoEquipo(Uri uriFoto, String uidEquipo);
    void eliminarEquipo(String uidEquipo);
    void eliminarFotoEquipo(String uidEquipo);
    void guardarEquipo(Equipo equipo);

    void buscarDelegado(String uidDelegado, BasicDelegadoCallback callback);
    void guardarDelegado(int respuestaExito, UsuarioDelegado delegado);
}
