package com.tachnologies.myligapro.common.utils;

/** Servira para realizar validaciones que puedan ser utiles dentro del desarrollo*/
public class Validaciones {

    public static boolean estaVacio(String dato){
        if(dato == null || dato.trim().isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean esNumerico(String dato){
        if(!estaVacio(dato)){
            try{
                int numero = Integer.parseInt(dato);
                return true;
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }

    public boolean esDecimal(String dato){
        if(!estaVacio(dato)){
            try{
                double numero = Double.parseDouble(dato);
                return true;
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }


}
