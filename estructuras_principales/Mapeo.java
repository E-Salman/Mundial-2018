/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018.estructuras_principales;

import Mundial2018.EquiposParticipantes;
import Mundial2018.Seleccion;
import Mundial2018.estructuras_auxiliares.Lista;

/**
 *
 * @author Admin
 */
public class Mapeo {
    private final int TAM = 100;
    private NodoHashMapeoM[] tabla = new NodoHashMapeoM[TAM - 1];
    private int cant = 0;
    
    public Mapeo(){}
    
    public boolean asociar(Object valorDominio, Object valorRango){
        boolean exito = true;
        int pos;
        Lista auxLista;       
        pos = hash(valorDominio);
        auxLista = obtenerValores(valorDominio);
        
        if(auxLista == null && tabla[pos] == null){
            tabla[pos] = new NodoHashMapeoM();
            tabla[pos].setDominio(valorDominio);
            exito = tabla[pos].getRango().insertar(valorRango, tabla[pos].getRango().longitud() + 1);
        }
        else if(auxLista == null && tabla[pos] != null){
            NodoHashMapeoM auxNodo = new NodoHashMapeoM();
            auxNodo.setDominio(valorDominio);
            exito = auxNodo.getRango().insertar(valorRango, auxNodo.getRango().longitud() + 1);           
            tabla[pos].setEnlace(auxNodo);
        }
        else{
            //auxLista no es nulo, tabla[pos] tampoco, verifica entonces si el par de valorDominio y valorRango existe en el mapeo
            if(auxLista.localizar(valorRango) == -1){
                exito = auxLista.insertar(valorRango, auxLista.longitud() + 1);
            }
            else exito = false;
        }
        if(exito) cant++;        
        return exito;
    }
    
    public boolean desasociar(Object valorDominio, Object valorRango){
        boolean exito = false;
        int pos = hash(valorDominio);
        Lista auxLista = obtenerValores(valorDominio);
        
        if(auxLista != null){
            //Si auxLista no es nulo, entonces tabla[pos] tampoco lo es
            int posLista = auxLista.localizar(valorRango);
            if(posLista != -1){
                exito = auxLista.eliminar(posLista);
            }
            else exito = false;            
            if(auxLista.esVacia()) tabla[pos] = null;
        }
        return exito;
    }
    
    public Lista obtenerValores(Object valorDominio){
        int pos = hash(valorDominio);
        Lista valores = null;
        if(tabla[pos] != null){       
            NodoHashMapeoM auxNodo = recorrerEnlaces(valorDominio, pos);
            if(auxNodo != null) valores = auxNodo.getRango();
        }
        return valores;
    }
    
    public Lista obtenerConjuntoDominio(){
        Lista conjuntoDominio = new Lista();        
        int limite = TAM - 1;
        for(int i = 1; i < limite; i++){
            if(tabla[i] != null){
                conjuntoDominio.insertar(tabla[i], conjuntoDominio.longitud() + 1);            
            }   
        }
        return conjuntoDominio;
    }
    
    public Lista obtenerConjuntoRango(){
        Lista conjuntoRango = new Lista();
        NodoHashMapeoM auxNodo;
        int limite, cont, i = 0;
        cont = 0;
        limite = TAM - 1;
        while(i < limite && cont < cant){
            if(tabla[i] != null){
                auxNodo = tabla[i];
                while(auxNodo != null){
                    for(int v = 1; v <= auxNodo.getRango().longitud(); v++){
                        conjuntoRango.insertar(auxNodo.getRango().recuperar(v), conjuntoRango.longitud() + 1);                    
                    }
                    auxNodo = auxNodo.getEnlace();
                }
            }
            i++;
        }   
        return conjuntoRango;
    }
    
    private NodoHashMapeoM recorrerEnlaces(Object valorDominio, int pos){
        //Devuelve el nodo de valorDominio, o nulo si no lo encuentra en la posicion dada
        NodoHashMapeoM aux = tabla[pos];        
        while(aux != null && !aux.getDominio().equals(valorDominio)){
            aux = aux.getEnlace();
        }
        return aux;
    }

    private int hash(Object dominio){      
        int valorElem = 0, aux, cont = 0;
        Class elemClass = dominio.getClass();
        
        if(elemClass == EquiposParticipantes.class){
            valorElem = (valorCadena(((EquiposParticipantes)dominio).getEq1().getPais()) + valorCadena(((EquiposParticipantes)dominio).getEq2().getPais())) / 3;
        }
        else if(elemClass == Seleccion.class){
            valorElem = valorCadena(((Seleccion)dominio).getPais());
        }
        else if(elemClass == String.class){
            valorElem = valorCadena(dominio);
        }
        else if(elemClass == Character.class){
            valorElem = (char) dominio;
        }
        else if(elemClass == Integer.class){
            valorElem = (int) dominio;
        }
        else if(elemClass == Float.class){
            valorElem = (int) (float) dominio;
        }
        else if(elemClass == Double.class){
            valorElem = (int) (double) dominio;
        }
        
        valorElem = valorElem * valorElem;
        aux = valorElem;
        do{
            aux = aux / 10;            
            cont++;
        }while(aux > 0);
        cont = (cont / 2) + 1;
        valorElem = (valorElem % (int)(Math.pow(10, cont)));
        while(valorElem > 100){
            valorElem /= 10;
        }
        return valorElem;
    }  
    
    private int valorCadena(Object elem){
        String string = (String) elem;
        int valorCadena = 0, sLength = string.length();
        
        for(int i = 0; i < sLength; i++){
            valorCadena = string.charAt(i) + valorCadena;
        }
        return valorCadena;
    }
    
    public String toString(){
        String retVal = "";
        Object auxObj;
        Lista auxLista = obtenerConjuntoDominio();        
        for(int i = 1; i <= auxLista.longitud(); i++){
            auxObj = auxLista.recuperar(i);
            retVal = retVal + auxObj.toString() + "\n";
        }
        return retVal;
    }
    
    public boolean esVacia(){
        return cant == 0;
    }
}