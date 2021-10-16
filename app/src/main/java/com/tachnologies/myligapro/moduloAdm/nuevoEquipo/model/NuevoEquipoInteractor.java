package com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model;

import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public interface NuevoEquipoInteractor {
    void guardarEquipo(Equipo equipo);
    void guardarDelegado(UsuarioDelegado delegado);
    void subirEscudoEquipo(Uri uriFoto, String equipoId);
    void actualizarDelegado(UsuarioDelegado delegado);
}
