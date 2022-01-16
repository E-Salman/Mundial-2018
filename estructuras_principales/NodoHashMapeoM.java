/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018.estructuras_principales;

import Mundial2018.estructuras_auxiliares.Lista;

/**
 *
 * @author Admin
 */
public class NodoHashMapeoM {    
    
    private Object dominio;
    private Lista rango = new Lista();
    private NodoHashMapeoM enlace;
    
    public NodoHashMapeoM(){}
    
    public Object getDominio(){
        return dominio;
    }
    
    public void setDominio(Object dominio){
        this.dominio = dominio;
    }
    
    public Lista getRango(){
        return rango;
    }
    
    public void setRango(Lista rango){
        this.rango = rango;
    }
    
    public NodoHashMapeoM getEnlace(){
        return enlace;
    }
    
    public void setEnlace(NodoHashMapeoM nEnlace){
        enlace = nEnlace;
    }
    
    public String toString(){
        return ("Dominio: " + dominio + "\nRango: " + rango.toString());
    }
}
