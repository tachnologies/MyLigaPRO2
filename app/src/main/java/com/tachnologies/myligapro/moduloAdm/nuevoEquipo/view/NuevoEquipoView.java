package com.tachnologies.myligapro.moduloAdm.nuevoEquipo.view;

import android.content.Intent;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public interface NuevoEquipoView {

    void desbloquearPantalla();
    void bloquearPantalla();
    void equipoAgregado();
    void finalGuardar();
    void mostrarError(int resMsg);
    void actualizarDelegado(UsuarioDelegado delegado);
    void abrirGaleria();
    void setImagen(Intent data);
    void guardarEquipo(Equipo equipo);
}
