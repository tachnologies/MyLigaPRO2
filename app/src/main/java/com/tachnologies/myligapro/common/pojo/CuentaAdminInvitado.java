package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;
import java.util.Map;

public class CuentaAdminInvitado {
    @Exclude
    private String uidCuenta;
    private Map<String, ItemCanchaListado> canchas;

    public CuentaAdminInvitado(){}

    @Exclude
    public String getUidCuenta() {
        return uidCuenta;
    }
    @Exclude
    public void setUidCuenta(String uidCuenta) {
        this.uidCuenta = uidCuenta;
    }

    public Map<String, ItemCanchaListado> getCanchas() {
        return canchas;
    }

    public void setCanchas(Map<String, ItemCanchaListado> canchas) {
        this.canchas = canchas;
    }
}
