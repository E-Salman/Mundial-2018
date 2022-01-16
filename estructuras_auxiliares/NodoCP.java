/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018.estructuras_auxiliares;

/**
 *
 * @author Admin
 */
public class NodoCP{
    private Comparable prioridad;
    private Cola items;
    private NodoCP enlace;
    
    public NodoCP(){
        items = new Cola();
    }
    
    public Cola getItems(){
        return items;
    }
    
    public void setPrioridad(Comparable prioridad){
        this.prioridad = prioridad;
    }
    
    public Comparable getPrioridad(){
        return prioridad;
    }
    
    public void setEnlace(NodoCP enlace){
        this.enlace = enlace;
    }
    
    public NodoCP getEnlace(){
        return enlace;
    }
}
