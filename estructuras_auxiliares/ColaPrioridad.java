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
public class ColaPrioridad {
    NodoCP inicio;
    
    public boolean insertar(Object elemento, Comparable prioridad){
        boolean exito = true;
        if(inicio == null){
            inicio = new NodoCP();
            inicio.setPrioridad(prioridad);
            inicio.getItems().poner(elemento);
        }
        else{
            NodoCP auxNodo = inicio;
            NodoCP auxNodoNuevo = new NodoCP();
            exito = auxNodoNuevo.getItems().poner(elemento);
            auxNodoNuevo.setPrioridad(prioridad);
            if(prioridad.compareTo(inicio.getPrioridad()) > 0){
                auxNodoNuevo.setEnlace(inicio);
                inicio = auxNodoNuevo;
            }
            else{
                while(auxNodo.getEnlace() != null && (prioridad.compareTo(auxNodo.getEnlace().getPrioridad()) < 0)){
                    auxNodo = auxNodo.getEnlace();
                }
                if(auxNodo.getEnlace() != null){
                    if(auxNodo.getEnlace().getPrioridad().equals(prioridad)){
                        exito = auxNodo.getEnlace().getItems().poner(elemento);
                    }
                    else{                    
                        auxNodoNuevo.setEnlace(auxNodo.getEnlace());
                        auxNodo.setEnlace(auxNodoNuevo);
                    }
                }
                else{
                    auxNodo.setEnlace(auxNodoNuevo);
                }
            }
        }
        return exito;
    }
    
    public boolean eliminarFrente(){
        boolean eliminado = true;
        eliminado = inicio.getItems().sacar();
        if(eliminado && inicio.getItems().esVacia()){
            inicio = inicio.getEnlace();            
        }
        return eliminado;
    }
    
    public Object obtenerFrente(){
        return inicio.getItems().obtenerFrente();
    }
    
    public boolean esVacia(){
        return inicio == null;
    }
    
    public String toString(){
        NodoCP auxNodo = inicio;
        String retVal = "";
        while(auxNodo != null){
            retVal = retVal + auxNodo.getItems().toString() + "\n";
            auxNodo = auxNodo.getEnlace();
        }
        return retVal;
    }
}
