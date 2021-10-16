package com.tachnologies.myligapro.common.pojo;

import com.google.firebase.database.Exclude;

import java.util.Map;

public class MiCuentaAdmin {
    private String uidCuenta;
    private Map<String, ItemCanchaListado> canchas;

    public MiCuentaAdmin(){}

    public MiCuentaAdmin(String uidCuenta, Map<String, ItemCanchaListado> canchas){
        this.uidCuenta = uidCuenta;
        this.canchas = canchas;
    }

    public String getUidCuenta() {
        return uidCuenta;
    }
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
