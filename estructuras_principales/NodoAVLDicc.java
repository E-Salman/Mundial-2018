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
public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc hijoIzquierdo, hijoDerecho;
    
    public NodoAVLDicc(){}
    
    public NodoAVLDicc(Comparable clave, Object dato){
        this.clave = clave;
        this.dato = dato;
        altura = 0;
    }
    
    public Comparable getClave(){
        return clave;
    }
    
    public void setClave(Comparable clave){
        this.clave = clave;
    }
    
    public Object getDato(){
        return dato;
    }
    
    public void setDato(Object dato){
        this.dato = dato;
    }
    
    public int getAltura(){
        return altura;
    }
    
    public void recalcularAltura(){
        System.out.println("Recalculo altura " + clave.toString());
        if(hijoIzquierdo == null && hijoDerecho == null){
            altura = 0;
        }
        else if(hijoIzquierdo != null && hijoDerecho != null){
            if(hijoIzquierdo.altura >= hijoDerecho.altura){
                altura = hijoIzquierdo.altura + 1;
            }
            else{
                altura = hijoDerecho.altura + 1;
            }
        }
        else if(hijoIzquierdo != null){
            altura = hijoIzquierdo.altura + 1;
        }
        else{
            altura = hijoDerecho.altura + 1;
        }
    }
    
    public void setAltura(int altura){
        this.altura = altura;
    }
    
    public NodoAVLDicc getHijoIzquierdo(){
        return hijoIzquierdo;
    }
    
    public void setHijoIzquierdo(NodoAVLDicc hijo){
        hijoIzquierdo = hijo;
        //recalcularAltura();
    }
    
    public NodoAVLDicc getHijoDerecho(){
        return hijoDerecho;
    }
    
    public void setHijoDerecho(NodoAVLDicc hijo){
        hijoDerecho = hijo;
        //recalcularAltura();
    }
    
    @Override
    public boolean equals(Object obj){
        boolean retVal;
        if(obj.getClass().equals(NodoAVLDicc.class)){
            retVal = equals((NodoAVLDicc)obj);
        }
        else if(obj.getClass().equals(String.class)){
            retVal = ((String)clave).equalsIgnoreCase((String)obj);
        }
        else{
            retVal = super.equals(obj);
        }
        return retVal;
    }
    
    public boolean equals(NodoAVLDicc nodo){
        boolean retVal;
        if(this.clave.getClass().equals(String.class) && nodo.clave.getClass().equals(String.class)){
            retVal = ((String)clave).equalsIgnoreCase((String)nodo.clave);
        }
        else{
            retVal = clave.equals(nodo.clave);
        }
        return retVal;
    }
}
