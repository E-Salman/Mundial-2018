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
public class Cola {           
    private Nodo frente;
    private Nodo finall;   
    
    public Cola(){}
    
    public Cola(Object nElem){
        poner(nElem);
    }
    
    public boolean poner(Object nElem){
        Nodo nodoAux;
        if(esVacia()){
            frente = new Nodo(nElem);
            finall = frente;
        }
        else{
            nodoAux = new Nodo(nElem);
            finall.setEnlace(nodoAux);
            finall = nodoAux;
        }
        return true;
    }
    
    public boolean sacar(){
        boolean colaVacia = esVacia();
        if(!colaVacia) frente = frente.getEnlace();
        return !colaVacia;
    }
    
    public Object obtenerFrente(){        
        return frente.getElem();
    }
    
    public boolean esVacia(){
        return (frente == null);
    }
    
    public void vaciar(){        
        frente = null;
        finall = null;
    }
    
    public Cola clone(){
        Cola cCola = new Cola();
        if(!esVacia()){
            Nodo nodoAux = frente;        
            while(nodoAux.getElem() != finall.getElem()){
                cCola.poner(nodoAux.getElem());
                nodoAux = nodoAux.getEnlace();
            }
            cCola.poner(nodoAux.getElem());
        }        
        System.out.println(cCola.frente);
        System.out.println(frente);
        return cCola;
    }
    
    public String toString(){
        String sOut = "";
        if(!esVacia()){
            Nodo nodoAux = frente;
            while(nodoAux != finall){
                sOut = sOut + nodoAux.getElem() + "\n";
                nodoAux = nodoAux.getEnlace();
            }
            sOut = sOut + nodoAux.getElem();
        }        
        return sOut;
    }
}
 