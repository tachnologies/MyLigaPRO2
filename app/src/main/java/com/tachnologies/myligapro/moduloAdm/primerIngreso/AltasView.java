package com.tachnologies.myligapro.moduloAdm.primerIngreso;

public interface AltasView {
    void activarComponentes();
    void desactivarComponentes();
    void bloquearPantalla();
    void desbloquearPantalla();

    void cuentaGuardada(String uidCuenta);
    void abrirAdmActivity();
    void mostrarError(int resMsg);
}
