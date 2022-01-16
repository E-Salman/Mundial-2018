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
public class Nodo {    
    
    private Object elem;
    private Nodo enlace;
    
    public Nodo(Object nElem, Nodo nEnlace){
        elem = nElem;
        enlace = nEnlace;
    }
    
    public Nodo(Object elemN){
        elem = elemN;
        enlace = null;
    }
    
    public Object getElem(){
        return elem;
    }
    
    public void setElem(Object nElem){
        elem = nElem;
    }
    
    public Nodo getEnlace(){
        return enlace;
    }
    
    public void setEnlace(Nodo nEnlace){
        enlace = nEnlace;
    }    
}
