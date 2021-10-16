package com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.view;

import android.content.Intent;

import com.tachnologies.myligapro.common.pojo.Equipo;

public interface EditarEquipoDelView {
    void desbloquearPantalla();
    void bloquearPantalla(int resMsje);

    void llenaInfoEquipo(Equipo equipo);
    void mostrarError(int resMsg);
    void abrirGaleria();
    void setImagen(Intent data);
    void guardarEquipo(String urlFotoEquipo);
    void equipoGuardado();
    void finalGuardar();
}
