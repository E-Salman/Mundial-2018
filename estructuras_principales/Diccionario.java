/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018.estructuras_principales;

import Mundial2018.estructuras_auxiliares.Lista;

/**
 *
 * @author Admin
 */
public class Diccionario {
    private NodoAVLDicc raiz = null;
    
    public Diccionario(){}
    
    public boolean insertar(Comparable clave, Object dato){
        boolean exito = true;
        
        if(raiz == null) raiz = new NodoAVLDicc(clave, dato);
        else{
            exito = recInsertar(raiz, clave, dato);
        }         
        System.out.println(toString());
        return exito;
    }
    
    private boolean recInsertar(NodoAVLDicc nodo, Comparable clave, Object dato){
        boolean exito = true;
        if(nodo != null){
            if(clave.compareTo(nodo.getClave()) < 0){
                if(nodo.getHijoIzquierdo() == null){
                    nodo.setHijoIzquierdo(new NodoAVLDicc(clave, dato));   
                    nodo.recalcularAltura();
                }
                else{                    
                    if(recInsertar(nodo.getHijoIzquierdo(), clave, dato)){                        
                       nodo.recalcularAltura();
                       balance(nodo);
                    }
                    else exito = false;
                }
            }
            else if(clave.compareTo(nodo.getClave()) > 0){
                if(nodo.getHijoDerecho() == null){
                    nodo.setHijoDerecho(new NodoAVLDicc(clave, dato));
                    nodo.recalcularAltura();
                }
                else{
                    if(recInsertar(nodo.getHijoDerecho(), clave, dato)){       
                       nodo.recalcularAltura(); 
                       balance(nodo);
                    }
                    else exito = false;
                }                
            }
            else exito = false;
        }
        return exito;
    }            
    
    private int balance(NodoAVLDicc nodo){
        int altI, altD, balance = 0, balanceI, balanceD;
        NodoAVLDicc aux;
        
        if(nodo != null){
            balanceI = balance(nodo.getHijoIzquierdo());
            balanceD = balance(nodo.getHijoDerecho());
            if(nodo.getHijoIzquierdo() != null){
                altI = nodo.getHijoIzquierdo().getAltura();                
            }
            else altI = -1;
            if(nodo.getHijoDerecho() != null){
                altD = nodo.getHijoDerecho().getAltura();                
            }
            else altD = -1;
            balance = altI - altD;
            
            if(balance == -2 && balanceD == -1){
                aux = padre(raiz, nodo.getClave());
                if(aux == null) raiz = rotSimpleIzquierda(nodo);
                else{
                    if(aux.getHijoDerecho().equals(nodo)){
                        aux.setHijoDerecho(rotSimpleIzquierda(nodo));
                    }
                    else{
                        aux.setHijoIzquierdo(rotSimpleIzquierda(nodo));
                    }
                }
            }
            else if(balance == 2 && (balanceI == 1 || balanceI == 0)){
                aux = padre(raiz, nodo.getClave());
                if(aux == null) raiz = rotSimpleDerecha(nodo);                  
                else{
                    if(aux.getHijoDerecho().equals(nodo)){
                        aux.setHijoDerecho(rotSimpleDerecha(nodo));
                    }                    
                    else{
                        aux.setHijoIzquierdo(rotSimpleDerecha(nodo));
                    }
                }
            }   
            else if(balance == -2 && balanceD == 1){                
                aux = padre(raiz, nodo.getClave());
                nodo.setHijoDerecho(rotSimpleDerecha(nodo.getHijoDerecho()));
                if(aux == null){
                    raiz = rotSimpleIzquierda(nodo);
                }                  
                else{
                    if(aux.getHijoDerecho().equals(nodo)){
                        aux.setHijoDerecho(rotSimpleIzquierda(nodo));
                    }                    
                    else{
                        aux.setHijoIzquierdo(rotSimpleIzquierda(nodo));
                    }
                }
            }
            else if(balance == 2 && (balanceI == -1 || balanceI == 0)){
                nodo.setHijoIzquierdo(rotSimpleIzquierda(nodo.getHijoIzquierdo()));
                aux = padre(raiz, nodo.getClave());
                if(aux == null){
                    raiz = rotSimpleDerecha(nodo);
                }                  
                else{
                    if(aux.getHijoDerecho().equals(nodo)){
                        aux.setHijoDerecho(rotSimpleDerecha(nodo));
                    }                    
                    else{
                        aux.setHijoIzquierdo(rotSimpleDerecha(nodo));
                    }
                }
            }
        }
        return balance;
    }        
    
    private NodoAVLDicc rotSimpleDerecha(NodoAVLDicc nodo){
        System.out.println("Rotacion simple derecha sobre " + nodo.getClave());
        NodoAVLDicc h, temp;
        h = nodo.getHijoIzquierdo();
        temp = h.getHijoDerecho();
        h.setHijoDerecho(nodo);
        nodo.setHijoIzquierdo(temp);
        nodo.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    
    private NodoAVLDicc rotSimpleIzquierda(NodoAVLDicc nodo){
        System.out.println("Rotacion simple izquierda sobre " + nodo.getClave());
        NodoAVLDicc h, temp;        
        h = nodo.getHijoDerecho();
        temp = h.getHijoIzquierdo();
        h.setHijoIzquierdo(nodo);
        nodo.setHijoDerecho(temp);
        nodo.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    
    public boolean eliminar(Comparable clave){
        boolean exito[] = {false};
        recAuxEliminar(raiz, clave, exito);
        return exito[0];
    }
    
    private boolean recAuxEliminar(NodoAVLDicc nodo, Comparable clave, boolean[] exito){
        boolean encontrado = false, auxIzquierda = false;
        NodoAVLDicc auxNodo = null;
        if(nodo != null){            
            System.out.println("En nodo: " + nodo.getClave());
            if(clave.compareTo(nodo.getClave()) < 0){
                if(recAuxEliminar(nodo.getHijoIzquierdo(), clave, exito)){
                    System.out.println("Saliendo a " + nodo.getClave());
                    auxNodo = nodo.getHijoIzquierdo();
                    auxIzquierda = true;
                }
            }
            else if(clave.compareTo(nodo.getClave()) > 0){
                if(recAuxEliminar(nodo.getHijoDerecho(), clave, exito)){
                    System.out.println("Saliendo a " + nodo.getClave());
                    auxNodo = nodo.getHijoDerecho();
                }
            }
            else{
                //Encontrado
                encontrado = true;
                if(raiz.equals(nodo)){
                    auxNodo = nodo;                    
                }
            }
            if(exito[0]){
                nodo.recalcularAltura();
                balance(nodo);
            }
            if(auxNodo != null){
                //Caso 1: el nodo a borrar es la raiz y estamos en la primera iteracion
                //Caso 2: el nodo a borrar es cualquier otro y estamos en la N iteracion                
                if(encontrado){
                    //Es la raiz
                    if(auxNodo.getHijoIzquierdo() == null && auxNodo.getHijoDerecho() == null){
                        raiz = null;
                    }
                    else if(auxNodo.getHijoIzquierdo() != null && auxNodo.getHijoDerecho() == null){
                        raiz = auxNodo.getHijoIzquierdo();
                    }
                    else if(auxNodo.getHijoIzquierdo() == null && auxNodo.getHijoDerecho() != null){
                        raiz = auxNodo.getHijoDerecho();
                    }
                    else if(auxNodo.getHijoIzquierdo() != null && auxNodo.getHijoDerecho() != null){
                        //El nodo a eliminar tiene 2 subarboles
                        //Conseguir el menor valor del subarbol derecho, reemplazar el nodo original por el y borrarlo
                        auxNodo = auxNodo.getHijoDerecho();
                        while(auxNodo.getHijoIzquierdo() != null){
                            auxNodo = auxNodo.getHijoIzquierdo();
                        }
                        boolean[] auxBool = {false};
                        System.out.println("Nodo reemplazante: " + auxNodo);
                        recAuxEliminar(raiz, auxNodo.getClave(), auxBool);
                        auxNodo.setHijoIzquierdo(raiz.getHijoIzquierdo());
                        auxNodo.setHijoDerecho(raiz.getHijoDerecho());                        
                        raiz = auxNodo;
                    }                    
                    raiz.recalcularAltura();
                    balance(raiz);
                }
                else{
                    if(auxNodo.getHijoIzquierdo() == null && auxNodo.getHijoDerecho() == null){
                        //agregarAPadre(nodo, null, auxNodo, auxIzquierda);
                        if(auxIzquierda){
                            nodo.setHijoIzquierdo(auxNodo);
                        }
                        else{
                            nodo.setHijoDerecho(auxNodo);
                        }
                    }
                    else if(auxNodo.getHijoIzquierdo() != null && auxNodo.getHijoDerecho() == null){
                        nodo.setHijoIzquierdo(auxNodo.getHijoIzquierdo());
                        //agregarAPadre(nodo, auxNodo.getHijoIzquierdo(), auxNodo, auxIzquierda);
                    }
                    else if(auxNodo.getHijoIzquierdo() == null && auxNodo.getHijoDerecho() != null){
                        nodo.setHijoDerecho(auxNodo.getHijoDerecho());
                        //agregarAPadre(nodo, auxNodo.getHijoDerecho(), auxNodo, auxIzquierda);
                    }
                    else if(auxNodo.getHijoIzquierdo() != null && auxNodo.getHijoDerecho() != null){
                        //El nodo a eliminar tiene 2 subarboles
                        //Conseguir el menor valor del subarbol derecho, reemplazar el nodo original por el y borrarlo
                        NodoAVLDicc nodoReemplazante = auxNodo.getHijoDerecho();
                        while(nodoReemplazante.getHijoIzquierdo() != null){
                            nodoReemplazante = nodoReemplazante.getHijoIzquierdo();
                        }
                        boolean[] auxBool = {false};
                        recAuxEliminar(auxNodo, nodoReemplazante.getClave(), auxBool);
                        System.out.println(nodo.getClave() + " " + nodoReemplazante.getClave() + " " + auxNodo.getClave() + " " +  auxIzquierda);
                        agregarAPadre(nodo, nodoReemplazante, auxNodo, auxIzquierda);
                        nodoReemplazante.recalcularAltura();
                        balance(nodoReemplazante);
                    }                    
                    nodo.recalcularAltura();
                    balance(nodo);
                }
                exito[0] = true;
            }
        }
        return encontrado;
    }
    
    private void agregarAPadre(NodoAVLDicc padre, NodoAVLDicc nodoNuevo, NodoAVLDicc nodoEliminar, boolean aLaIzquierda){
        if(nodoNuevo != null){
            nodoNuevo.setHijoIzquierdo(nodoEliminar.getHijoIzquierdo());
            nodoNuevo.setHijoDerecho(nodoEliminar.getHijoDerecho());
        }        
        if(aLaIzquierda){
            padre.setHijoIzquierdo(nodoNuevo);
        }
        else{
            padre.setHijoDerecho(nodoNuevo);
        }
    }    
    
    public boolean existeClave(Comparable clave){
        boolean encontrada = false;
        NodoAVLDicc auxNodo = raiz;
        while(auxNodo != null && !encontrada){
            if(clave.compareTo(auxNodo.getClave()) < 0){
                auxNodo = auxNodo.getHijoIzquierdo();
            }
            else if(clave.compareTo(auxNodo.getClave()) > 0){
                auxNodo = auxNodo.getHijoDerecho();
            }
            else{
                encontrada = true;
            }
        }
        return encontrada;
    }
    
    public Object obtenerInformacion(Comparable clave){
        //Devuelve null si no lo encuentra
        NodoAVLDicc auxNodo = raiz;
        while(auxNodo != null && !auxNodo.getClave().equals(clave)){
            if(clave.compareTo(auxNodo.getClave()) < 0){
                auxNodo = auxNodo.getHijoIzquierdo();
            }
            else{                
                auxNodo = auxNodo.getHijoDerecho();
            }
        }
        return auxNodo;
    }
    
    public Object ubicarNodo(Comparable clave){
        NodoAVLDicc nodoUbicado = ubicarNodo(raiz, clave);
        Object retVal = null;
        if(nodoUbicado != null){
            retVal = nodoUbicado.getDato();
        }
        return retVal;
    }
    
    private NodoAVLDicc ubicarNodo(NodoAVLDicc nodo, Comparable clave){
        NodoAVLDicc auxNodo = null;
        if(nodo != null){
            if(nodo.equals(clave)){
                auxNodo = nodo;
            }
            else if(clave.compareTo(nodo.getClave()) < 0){
                auxNodo = ubicarNodo(nodo.getHijoIzquierdo(), clave);
            }
            else if(clave.compareTo(nodo.getClave()) > 0){
                auxNodo = ubicarNodo(nodo.getHijoDerecho(), clave);
            }
        }
        return auxNodo;
    }
    
    public NodoAVLDicc padre(NodoAVLDicc nodo, Comparable clave){
        NodoAVLDicc nodoPadre = null;
        if(nodo.equals(clave)) nodoPadre = null;
        else if(nodo.getHijoIzquierdo() != null && clave.compareTo(nodo.getClave()) < 0){
            if(nodo.getHijoIzquierdo().equals(clave)){
                nodoPadre = nodo;
            }
            else{
                nodoPadre = padre(nodo.getHijoIzquierdo(), clave);
            }            
        }
        else if(nodo.getHijoDerecho() != null && clave.compareTo(nodo.getClave()) > 0){
            if(nodo.getHijoDerecho().equals(clave)){
                nodoPadre = nodo;
            }
            else{
                nodoPadre = padre(nodo.getHijoDerecho(), clave);
            }            
        }        
        return nodoPadre;
    }
    
    public Lista minMax(Comparable min, Comparable max){
        Lista retLista = new Lista();
        recMinMax(retLista, min, max, raiz);
        return retLista;
    }
    
    private void recMinMax(Lista lista, Comparable min, Comparable max, NodoAVLDicc nodo){
        if(nodo != null){
            System.out.println("Evaluando nodo " + nodo.getClave());
            if(nodo.getClave().compareTo(max) < 0){
                recMinMax(lista, min, max, nodo.getHijoDerecho());
            }
            if(nodo.getClave().compareTo(min) >= 0 && nodo.getClave().compareTo(max) <= 0){
                lista.insertar(nodo.getDato(), lista.longitud() + 1);
            }
            if(nodo.getClave().compareTo(min) > 0){
                recMinMax(lista, min, max, nodo.getHijoIzquierdo());
            }
        }
    }
    
   /*private void recMinMax(Lista lista, Comparable min, Comparable max, NodoAVLDicc nodo){
        if(nodo != null){
            if(nodo.getClave().compareTo(min) >= 0){
                recMinMax(lista, min, max, nodo.getHijoIzquierdo());
                if(nodo.getClave().compareTo(max) <= 0){
                    recMinMax(lista, min, max, nodo.getHijoDerecho());
                    if(lista.localizar(nodo.getDato())== -1){
                        lista.insertar(nodo.getDato(), lista.longitud() + 1);
                    }                    
                }
            }
            else{
                recMinMax(lista, min, max, nodo.getHijoDerecho());
            }
        }
    }*/
    
    public Lista listarDatos(){
        Lista inOrden = new Lista();
        recAuxListar(inOrden, raiz);
        return inOrden;
    }
    
    private void recAuxListar(Lista lista, NodoAVLDicc nodo){
        if(nodo != null){
            recAuxListar(lista, nodo.getHijoIzquierdo());
            lista.insertar(nodo.getDato(), lista.longitud() + 1);
            recAuxListar(lista, nodo.getHijoDerecho());
        }
    }  
    
    public Lista listarClaves(){
        Lista inOrden = new Lista();
        recAuxListarClaves(inOrden, raiz);
        return inOrden;
    }
    
    private void recAuxListarClaves(Lista lista, NodoAVLDicc nodo){
        if(nodo != null){
            recAuxListarClaves(lista, nodo.getHijoIzquierdo());
            lista.insertar(nodo.getClave(), lista.longitud() + 1);
            recAuxListarClaves(lista, nodo.getHijoDerecho());
        }
    }
    
    public String toString(){
        return toStringAux(raiz);
    }
                
    private String toStringAux(NodoAVLDicc n) {
        String s = "";
        if (n != null) {
            
            s += n.getAltura() + " - "+ n.getClave().toString();
            
            if (n.getHijoIzquierdo() != null) {
                s += "   HI:" + n.getHijoIzquierdo().getClave().toString();
            } else {
                s += "   HI: null";
            }
            
            if (n.getHijoDerecho() != null) {
                s += "   HD:" + n.getHijoDerecho().getClave().toString();
            } else {
                s += "   HD: null";
             }

            s += "\n";
            if (n.getHijoIzquierdo() != null) {
                s += toStringAux(n.getHijoIzquierdo());
            }

            if (n.getHijoDerecho() != null) {
                s += toStringAux(n.getHijoDerecho());
            }
        }
        return s;
    }    
    
    public boolean esVacio(){
        return raiz == null;
    }
}