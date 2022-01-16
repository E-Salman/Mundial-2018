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
public class NodoLista {    
    
    private Object elem;
    private NodoLista enlace;
    
    public NodoLista(Object nElem, NodoLista nEnlace){
        elem = nElem;
        enlace = nEnlace;
    }
    
    public NodoLista(Object elemN){
        elem = elemN;
        enlace = null;
    }
    
    public Object getElem(){
        return elem;
    }
    
    public void setElem(Object nElem){
        elem = nElem;
    }
    
    public NodoLista getEnlace(){
        return enlace;
    }
    
    public void setEnlace(NodoLista nEnlace){
        enlace = nEnlace;
    }
}
