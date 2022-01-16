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
public class Lista {
    private Nodo cabecera;
    private int longitud = 0;
    
    public Lista(){
        cabecera = null;
    }
    
    public boolean insertar(Object elemIn, int pos){
        boolean exito = true;                
        
        if(pos < 1 || pos > longitud + 1) exito = false;
        else{
            if(pos == 1) cabecera = new Nodo(elemIn, cabecera); //Crea una nueva cabecera con el elemento y la enlaza con la vieja            
            else{
                Nodo aux = cabecera;
                int i = 1;
                while(i < pos - 1){
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevoNodo = new Nodo(elemIn, aux.getEnlace());
                aux.setEnlace(nuevoNodo);
            }
            longitud++;
        }              
        return exito;
    }
    
    public boolean eliminar(int pos){        
        boolean exito = true;
        
        if(pos < 1 || pos > longitud) exito = false;
        else{
            if(pos == 1) cabecera = cabecera.getEnlace();
            else{
                Nodo aux = cabecera; //Apuntan al mismo espacio de memoria
                int i = 1;
                while(i < pos - 1){
                    aux = aux.getEnlace(); //El nodo original, cabecera, no se modifica.
                    i++;                    
                }                
                aux.setEnlace(aux.getEnlace().getEnlace()); //Cambia el nodo original, cabecera, en la posicion de aux.
            }
            longitud--;
        }
        return exito;
    }         
    
    public Object recuperar(int pos){
        Nodo aux = cabecera;
        
        if(!(pos < 1 || pos > longitud)){
            for(int i = 1; i < pos; i++){            
                aux = aux.getEnlace();
            }
        }        
        return aux.getElem();                    
    }
    
    public int localizar(Object elemABuscar){
        Nodo aux = cabecera;
        boolean encontrado = false;                
        encontrado = !esVacia();
        int i = 1;        
        if(encontrado){
            do{
                encontrado = (elemABuscar.equals(aux.getElem()));
                aux = aux.getEnlace();            
                if(!encontrado) i++;
            }while(!encontrado && i <= longitud);
        }        
        if (!encontrado) i = -1;
        return i;        
    }
    
    public int longitud() {       
        return longitud;
    }
    
    public boolean esVacia(){
        return cabecera == null;
    }
    
    public void vaciar(){
        cabecera = null;
    }
    
    public Lista clone(){
        Lista listaClon = new Lista();        
        listaClon.cabecera = recAux(cabecera);
        listaClon.longitud = this.longitud;
        return listaClon; 
    }

    private Nodo recAux(Nodo cabecera) {
        Nodo aux, rNodo;
        if(cabecera.getEnlace() == null){            
            rNodo = new Nodo(cabecera.getElem());
        }        
        else{
            aux = recAux(cabecera.getEnlace());   
            rNodo = new Nodo(cabecera.getElem(), aux);
        }
        return rNodo;        
    }
    
    public boolean equals(Object obj){
        boolean retVal;
        if(obj.getClass() == Lista.class){
            retVal = equals((Lista)obj);
        }
        else{
            retVal = super.equals(obj);
        }
        return retVal;
    }
    
    public boolean equals(Lista lista){
        boolean retVal = false;
        Nodo auxThis = this.cabecera;
        Nodo auxLista = lista.cabecera;
        if(this.longitud == lista.longitud){
            retVal = true;
            while(auxThis != null && retVal){
                retVal = (auxThis.getElem().equals(auxLista.getElem()));
                auxThis = auxThis.getEnlace();
                auxLista = auxLista.getEnlace();
            }
        }
        return retVal;
    }
    
    public String toString(){
        String retString = "";
        Nodo aux = cabecera;
        
        while(aux != null){
            retString = retString + aux.getElem().toString() + "\n";            
            aux = aux.getEnlace();
        }
        return retString;
    }
     
    public boolean eliminarObjeto(Object x){
        Nodo aux = null;
        boolean eliminado = false;
        
        if(!(esVacia())){
            if(cabecera.getElem().equals(x)){
                cabecera = cabecera.getEnlace();
                longitud--;
                eliminado = true;                
            }
            else{
                aux = cabecera;
            }
            while(!eliminado && aux.getEnlace() != null){
                if(aux.getEnlace().getElem().equals(x)){
                    aux.setEnlace(aux.getEnlace().getEnlace());
                    eliminado = true;
                    longitud--;
                }
                else aux = aux.getEnlace();
            }
        }
        return eliminado;
    } 
}
