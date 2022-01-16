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
public class NodoAdy {    
    
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;
    
    public NodoAdy(NodoVert nVert, Object etiqueta){
        vertice = nVert;
        this.etiqueta = etiqueta;
    }
    
    public NodoVert getVertice(){
        return vertice;
    }
    
    public NodoAdy getSigAdyacente(){
        return sigAdyacente;
    }
    
    public Object getEtiqueta(){
        return etiqueta;
    }
    
    public void setVertice(NodoVert nVert){
        vertice = nVert;
    }
    
    public void setSigAdyacente(NodoAdy nAdy){
        sigAdyacente = nAdy;
    }
    
    public void setEtiqueta(Object nEtiqueta){
        etiqueta = nEtiqueta;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean retVal;
        if(NodoAdy.class == obj.getClass()){
            retVal = equals((NodoAdy)obj);
        }
        else{
            retVal = super.equals(obj);
        }
        return retVal;
    }
    
    public boolean equals(NodoAdy nAdy){
        return this.vertice.equals(nAdy.vertice) && this.sigAdyacente.equals(nAdy.sigAdyacente) && this.etiqueta.equals(nAdy.etiqueta);
    }
}
