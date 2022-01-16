/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018.estructuras_principales;

import Mundial2018.Ciudad;
import Mundial2018.estructuras_auxiliares.Lista;

/**
 *
 * @author Admin
 */
public class Grafo {
    private NodoVert inicio = null;
    
    public Grafo(){}
    
    public boolean insertarVertice(Object vertice){
        boolean exito = false;
        NodoVert aux = ubicarVertice(vertice);
        if(aux == null){
            inicio = new NodoVert(vertice, inicio);
            exito = true;
        }
        return exito;
    }
    
    public Object recuperarDato(Object buscado){
        Object retVal = null;
        NodoVert auxNodo = ubicarVertice(buscado);
        if(auxNodo != null) retVal = auxNodo.getElem();
        return retVal;
    }
    
    private NodoVert ubicarVertice(Object verticeBuscado){
        NodoVert aux = inicio;
        while(aux != null && (!aux.getElem().equals(verticeBuscado))){
            aux = aux.getSigVertice();
        }
        return aux;
    }
    
    public boolean eliminarVertice(Object eVertice){
        boolean exito = false;
        NodoVert aux = inicio;
        if(aux != null){
            if(aux.getElem().equals(eVertice)){
                auxEliminarVertice(inicio, inicio.getPrimerAdy());
                inicio = inicio.getSigVertice();
                exito = true;
            }
            else{
                while(aux.getSigVertice() != null && !aux.getSigVertice().getElem().equals(eVertice)){
                    aux = aux.getSigVertice();
                }
                if(aux.getSigVertice() != null){
                    auxEliminarVertice(aux.getSigVertice(), aux.getSigVertice().getPrimerAdy());
                    aux.setSigVert(aux.getSigVertice().getSigVertice());
                    exito = true;
                }
            }
        }   
        return exito;
    }
    
    private void auxEliminarVertice(NodoVert auxVert, NodoAdy auxAdy){
        while(auxAdy != null){
            auxEliminarArcos(auxVert, auxAdy.getVertice().getElem());
            auxAdy = auxAdy.getSigAdyacente();
        }
    }
    
    public boolean existeVertice(Object vertice){        
        return (ubicarVertice(vertice) != null);
    } 
    
    public boolean insertarArco(Object origen, Object destino, Object etiqueta){
        //Falla si el arco ya existe, o si no encuentra uno de los dos objetos
        boolean insertado = false;
        if(!existeArco(origen, destino)){
            NodoVert auxOrigen = ubicarVertice(origen);
            NodoVert auxDestino = ubicarVertice(destino);
            if(auxOrigen != null && auxDestino != null){
                auxInsertar(auxOrigen, auxDestino, etiqueta);
                auxInsertar(auxDestino, auxOrigen, etiqueta);
                insertado = true;
            }
        }
        return insertado;
    }
    
    private void auxInsertar(NodoVert origen, NodoVert destino, Object etiqueta){
        NodoAdy auxAdy = origen.getPrimerAdy();
        if(auxAdy == null){
            origen.setPrimerAdy(new NodoAdy(destino, etiqueta));
        }
        else{
            while(auxAdy.getSigAdyacente() != null){
                auxAdy = auxAdy.getSigAdyacente();                        
            }
            auxAdy.setSigAdyacente(new NodoAdy(destino, etiqueta));
        }
    }
    
    public boolean eliminarArco(Object origen, Object destino){
        //Falla si el arco no existe
        boolean eliminado = false;
        NodoVert auxOrigen;
        //Recorre la estructura 2 veces, sacar existeArco()
        if(existeArco(origen, destino)){
            auxOrigen = ubicarVertice(origen);            
            auxEliminarArcos(auxOrigen, destino);
            eliminado = true;
        }
        return eliminado;
    }
    
    private void auxEliminarArcos(NodoVert nodoOrigen, Object destino){
        NodoAdy auxAdy, auxAdyDestino = null;        
        auxAdy = nodoOrigen.getPrimerAdy();
        for(int i = 0; i < 2; i++){
            if(i == 1){
                destino = nodoOrigen.getElem();
                nodoOrigen = auxAdy.getVertice();
                auxAdy = auxAdyDestino;
            }   
            if(!auxAdy.getVertice().getElem().equals(destino)){
                while(!(auxAdy.getSigAdyacente().getVertice().getElem().equals(destino))){
                    auxAdy = auxAdy.getSigAdyacente();
                }
                auxAdyDestino = auxAdy.getSigAdyacente().getVertice().getPrimerAdy();
                auxAdy.setSigAdyacente(auxAdy.getSigAdyacente().getSigAdyacente());
            }
            else{
                auxAdyDestino = auxAdy.getVertice().getPrimerAdy();
                nodoOrigen.setPrimerAdy(auxAdy.getSigAdyacente());
            }
        }
    }
    
    public boolean modificarEtiqueta(Object origen, Object destino, Object etiqueta){
        //Falla si el arco no existe
        boolean cambiada = false;
        NodoVert auxOrigen;
        NodoAdy auxAdy;
        if(existeArco(origen, destino)){
            auxOrigen = ubicarVertice(origen);
            auxAdy = auxOrigen.getPrimerAdy();
            while(!(auxAdy.getVertice().getElem().equals(destino))){
                auxAdy = auxAdy.getSigAdyacente();
            }
            auxAdy.setEtiqueta(etiqueta);                                
            cambiada = true;
        }
        return cambiada;      
    }
    
    public boolean existeArco(Object origen, Object destino){
        boolean encontrado = false;
        NodoVert auxOrigen = ubicarVertice(origen);
        NodoAdy auxAdy;
        if(auxOrigen != null){
            auxAdy = auxOrigen.getPrimerAdy();
            while(auxAdy != null && !auxAdy.getVertice().getElem().equals(destino)){
                auxAdy = auxAdy.getSigAdyacente();
            }
            encontrado = (auxAdy != null);            
        }
        return encontrado;
    }        
    
    public boolean vacio(){
        return (inicio == null);
    }
    
    public boolean existeCamino(Object origen, Object destino){
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = inicio;
        
        while(((auxO == null || auxD == null)) && aux != null){
            if(aux.getElem().equals(origen)) auxO = aux;
            if(aux.getElem().equals(destino)) auxD = aux;
            aux = aux.getSigVertice();
        }
        
        if(auxO != null && auxD != null){
           Lista visitados = new Lista();
           exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }
    
    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis){
        boolean exito = false;
        if(n != null){
            if(n.getElem().equals(dest)){
                exito = true;
            }
            else{
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while(!exito && ady != null){
                    if(vis.localizar(ady.getVertice().getElem()) < 0){
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }
    
    public Object[] caminoMasCorto(Object origen, Object destino){        
        //Devuelve nulo en listDist[0] si no hay camino entre las ciudades
        Lista visitados = new Lista();
        NodoVert aux = inicio;
        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        Object[] listDist = new Object[2];

        while(aux != null && (auxOrigen == null || auxDestino == null)){
            if(aux.getElem().equals(origen)) auxOrigen = aux;
            if(aux.getElem().equals(destino)) auxDestino = aux;
            aux = aux.getSigVertice();
        }
        
        if(auxOrigen != null && auxDestino != null){
            visitados.insertar(auxOrigen.getElem(), visitados.longitud() + 1);
            //{DistanciaMaxima, DistanciaAcumulada}
            double[] distancias = {-1, 0};            
            listDist[0] = recCaminoMasCorto(auxOrigen, auxDestino, visitados, distancias);
            listDist[1] = distancias[0];
        }
        return listDist;
    }
    
    private Lista recCaminoMasCorto(NodoVert origen, Object destino, Lista visitados, double[] distancias){
        NodoAdy auxAdy = origen.getPrimerAdy();
        Lista caminoMasCorto = null;
        Lista listaAux;
        if(origen.equals(destino)){
            if((distancias[0] > distancias[1]) || distancias[0] == -1){
                caminoMasCorto = visitados.clone();
                distancias[0] = distancias[1];
            }
        }
        else{
            while(auxAdy != null){
                if(visitados.localizar(auxAdy.getVertice().getElem()) == -1){
                    distancias[1] = distancias[1] + (double)auxAdy.getEtiqueta();
                    if((distancias[0] > distancias[1]) || distancias[0] == -1){      
                        visitados.insertar(auxAdy.getVertice().getElem(), visitados.longitud() + 1);
                        listaAux = recCaminoMasCorto(auxAdy.getVertice(), destino, visitados, distancias);
                        if(listaAux != null){
                            caminoMasCorto = listaAux;
                        }
                        visitados.eliminar(visitados.longitud());
                    }
                    distancias[1] = distancias[1] - (double)auxAdy.getEtiqueta();
                }    
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return caminoMasCorto;
    }
    
    public Object[] caminoMasCortoCiudades(Object origen, Object destino){
        //Devuelve nulo en listDist[0] si no hay camino entre las ciudades
        Lista visitados = new Lista();
        NodoVert aux = inicio;
        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        Object[] listDist = new Object[2];

        while(aux != null && (auxOrigen == null || auxDestino == null)){
            if(aux.getElem().equals(origen)) auxOrigen = aux;
            if(aux.getElem().equals(destino)) auxDestino = aux;
            aux = aux.getSigVertice();
        }
        
        if(auxOrigen != null && auxDestino != null){
            visitados.insertar(auxOrigen.getElem(), visitados.longitud() + 1);
            //{DistanciaMaxima, DistanciaAcumulada}
            double[] distancias = {-1, 0};            
            listDist[0] = recCaminoMasCortoCiudades(auxOrigen, auxDestino, visitados, distancias);
            listDist[1] = distancias[0];
        }
        return listDist;
    }
    
    private Lista recCaminoMasCortoCiudades(NodoVert origen, Object destino, Lista visitados, double[] distancias){
        NodoAdy auxAdy = origen.getPrimerAdy();
        Lista caminoMasCorto = null;
        Lista listaAux;
        if(origen.equals(destino)){
            if((distancias[0] > distancias[1]) || distancias[0] == -1){
                caminoMasCorto = visitados.clone();
                distancias[0] = distancias[1];
            }
        }
        else{
            while(auxAdy != null){
                if(visitados.localizar(auxAdy.getVertice().getElem()) == -1){
                    distancias[1] = distancias[1] + 1;
                    if((distancias[0] > distancias[1]) || distancias[0] == -1){      
                        visitados.insertar(auxAdy.getVertice().getElem(), visitados.longitud() + 1);
                        listaAux = recCaminoMasCortoCiudades(auxAdy.getVertice(), destino, visitados, distancias);
                        if(listaAux != null){
                            caminoMasCorto = listaAux;
                        }
                        visitados.eliminar(visitados.longitud());
                    }
                    distancias[1] = distancias[1] - 1;
                }    
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return caminoMasCorto;
    }    
    
    public Lista caminosCiudades(Object origen, Object destino){
        //Devuelve una lista vacia si no hay un camino
        Lista visitados = new Lista();
        Lista caminos = new Lista();
        NodoVert aux = inicio;
        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;

        while(aux != null && (auxOrigen == null || auxDestino == null)){
            if(aux.getElem().equals(origen)) auxOrigen = aux;
            if(aux.getElem().equals(destino)) auxDestino = aux;
            aux = aux.getSigVertice();
        }
        
        if(auxOrigen != null && auxDestino != null){
            visitados.insertar(auxOrigen.getElem(), visitados.longitud() + 1);
            recCaminosCiudades(auxOrigen, auxDestino, visitados, caminos);
        }
        return caminos;
    }
    
    private void recCaminosCiudades(NodoVert origen, Object destino, Lista visitados, Lista caminos){
        NodoAdy auxAdy = origen.getPrimerAdy();
        if(origen.equals(destino)){
            caminos.insertar(visitados.clone(), caminos.longitud() + 1);
        }        
        else{
            while(auxAdy != null){
                if(visitados.localizar(auxAdy.getVertice().getElem()) == -1){                
                    visitados.insertar(auxAdy.getVertice().getElem(), visitados.longitud() + 1);
                    recCaminosCiudades(auxAdy.getVertice(), destino, visitados, caminos);
                    visitados.eliminar(visitados.longitud());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
    } 
    
    public Object[] caminoCiudadesEvitar(Object origen, Object destino, Object evitar){
        //Devuelve nulo en listDist[0] si no hay camino entre las ciudades
        Lista visitados = new Lista();
        NodoVert aux = inicio;
        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        NodoVert auxEvitar = null;
        Object[] listDist = new Object[2];

        while(aux != null && (auxOrigen == null || auxDestino == null || auxEvitar == null)){
            if(aux.getElem().equals(origen)) auxOrigen = aux;
            if(aux.getElem().equals(destino)) auxDestino = aux;
            if(aux.getElem().equals(evitar)) auxEvitar = aux;
            aux = aux.getSigVertice();
        }
        
        if(auxOrigen != null && auxDestino != null && auxEvitar != null){
            visitados.insertar(auxOrigen.getElem(), visitados.longitud() + 1);
            //{DistanciaMaxima, DistanciaAcumulada}
            double[] distancias = {-1, 0};            
            listDist[0] = recCaminoCiudadesEvitar(auxOrigen, auxDestino, visitados, distancias, auxEvitar);
            listDist[1] = distancias[0];
        }
        return listDist;
    }
    
    private Lista recCaminoCiudadesEvitar(NodoVert origen, Object destino, Lista visitados, double[] distancias, Object evitar){
        NodoAdy auxAdy = origen.getPrimerAdy();
        Lista caminoMasCorto = null;
        Lista listaAux;
        if(origen.equals(destino)){
            if((distancias[0] > distancias[1]) || distancias[0] == -1){
                caminoMasCorto = visitados.clone();
                distancias[0] = distancias[1];
            }
        }
        else{
            while(auxAdy != null){
                if(visitados.localizar(auxAdy.getVertice().getElem()) == -1){
                    distancias[1] = distancias[1] + (double)auxAdy.getEtiqueta();
                    if(((distancias[0] > distancias[1]) || distancias[0] == -1) && !auxAdy.getVertice().equals(evitar)){      
                        visitados.insertar(auxAdy.getVertice().getElem(), visitados.longitud() + 1);
                        listaAux = recCaminoCiudadesEvitar(auxAdy.getVertice(), destino, visitados, distancias, evitar);                        
                        if(listaAux != null){
                            caminoMasCorto = listaAux;
                        }
                        visitados.eliminar(visitados.longitud());
                    }
                    distancias[1] = distancias[1] - (double)auxAdy.getEtiqueta();
                }    
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return caminoMasCorto;
    }
    
    public Lista listarEnProfundidad(){
        Lista visitados = new Lista();
        NodoVert aux = inicio;
        while(aux != null){
            //Se agregan a la lista el nodo vertice y todos sus adyacentes
            //Si el nodo esta en la lista (porque se lo agrego revisando los adyacentes de otro), no hace falta llamar al otro metodo
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnProfundidadAux(aux, visitados);                
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }
    
    private void listarEnProfundidadAux(NodoVert n, Lista vis){
        if(n != null){
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while(ady != null){
                if((vis.localizar(ady.getVertice().getElem()) < 0)){
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }    
    
    public String toString(){
        return listarEnProfundidad().toString();
    }
}