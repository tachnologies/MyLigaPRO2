package com.tachnologies.myligapro.moduloAdm.editarEquipo.view;

import android.content.Intent;
import com.tachnologies.myligapro.common.pojo.Equipo;

public interface EditarEquipoAdmView {
    void desbloquearPantalla();
    void bloquearPantalla(int resMsje);

    void llenaInfoEquipo(Equipo equipo);
    void mostrarError(int resMsg);
    void abrirGaleria();
    void setImagen(Intent data);
    void guardarEquipo(String urlFotoEquipo);
    void equipoGuardado();
    void equipoBorrado();
    void finalGuardar();
}
