/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018.estructuras_principales;

/**
 *
 * @author Admin
 */
public class NodoVert {    
    
    private Object elem;
    private NodoVert sigVertice;    
    private NodoAdy primerAdy;
        
    public NodoVert(Object elemN, NodoVert sigVertice){
        elem = elemN;
        this.sigVertice = sigVertice;
    }
    
    public Object getElem(){
        return elem;
    }
    
    public void setElem(Object nElem){
        elem = nElem;
    }
    
    public NodoVert getSigVertice(){
        return sigVertice;
    }
    
    public void setSigVert(NodoVert nElem){        
        sigVertice = nElem;
    }
    
    public NodoAdy getPrimerAdy(){
        return primerAdy;
    }
    
    public void setPrimerAdy(NodoAdy nAdy){
        primerAdy = nAdy;        
    }
}
