package com.tachnologies.myligapro.common.pojo;

public class ItemMenuAdm {
    private String nombre;
    private int codImagen;
    private int idMenu;

    public ItemMenuAdm(String nombre, int codImagen, int idMenu) {
        this.nombre = nombre;
        this.codImagen = codImagen;
        this.idMenu = idMenu;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCodImagen() {
        return codImagen;
    }
    public void setCodImagen(int codImagen) {
        this.codImagen = codImagen;
    }
    public int getIdMenu() {
        return idMenu;
    }
    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }
}
