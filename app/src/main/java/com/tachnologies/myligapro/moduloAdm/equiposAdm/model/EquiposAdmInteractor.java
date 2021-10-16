package com.tachnologies.myligapro.moduloAdm.equiposAdm.model;

import com.tachnologies.myligapro.common.pojo.Equipo;

public interface EquiposAdmInteractor {

    void subscribirAEquipos();
    void quitarSubscripcionAEquipos();
    void eliminarEquipo(Equipo equipo);
    void buscarEquipo(String uidEquipo);
}
