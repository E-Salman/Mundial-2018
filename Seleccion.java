/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018;

import Mundial2018.estructuras_auxiliares.Lista;

/**
 *
 * @author Admin
 */
public class Seleccion {
    private String pais, directorT;
    private Lista equiposEnfrentados = new Lista();
    private int puntos, golesAFavor, golesEnContra;
    private char grupo;
    
    public Seleccion(String pais, String directorT){
        this.pais = pais;
        this.directorT = directorT;
    }
    
    public Seleccion(String pais, String directorT, int puntos, int golesF, int golesC, char grupo){
        this.pais = pais;
        this.directorT = directorT;
        this.puntos = puntos;
        this.golesAFavor = golesF;
        this.golesEnContra = golesC;
        this.grupo = grupo;
    }
    
    public String getPais(){
        return pais;
    }
    
    public void setPais(String pais){
        this.pais = pais;
    }
    
    public String getDirectorT(){
        return directorT;
    }
    
    public void setDirectorT(String directorT){
        this.directorT = directorT;
    }
    
    public Lista getEquiposEnfrentado(){
        return equiposEnfrentados;
    }
    
    public void setGrupo(char grupo){
        this.grupo = grupo;
    }
    
    public char getGrupo(){
        return grupo;
    }
    
    public boolean nuevoEquipoEnfrentado(Seleccion nEquipo){
        return equiposEnfrentados.insertar(nEquipo, equiposEnfrentados.longitud() + 1);
    }
    
    public int getPuntos(){
        return puntos;
    }
    
    public void setPuntos(int puntos){
        this.puntos = puntos;
    }
    
    public void agregarPuntos(int puntos){
        this.puntos = this.puntos + puntos;
    }
    
    public int getGolesAFavor(){
        return golesAFavor;
    }
    
    public void setGolesAFavor(int golesF){
        golesAFavor = golesF;
    }
    
    public void agregarGolesAFavor(int goles){
        golesAFavor = golesAFavor + goles;
    }
    
    public int getGolesEnContra(){
        return golesEnContra;
    }
    
    public void setGolesEnContra(int golesC){
        golesEnContra = golesC;
    }
    
    public void agregarGolesEnContra(int goles){        
        golesEnContra = golesEnContra + goles;
    }
    
    public int diferenciaGoles(){
        return golesAFavor - golesEnContra;
    }
    
    public boolean equals(Seleccion seleccion){
        return this.pais.equalsIgnoreCase(seleccion.pais);        
    }
    
    public String toString(){
        return ("Pais: " + pais + " Director Tecnico: " + directorT + " Puntos: " + puntos + " Goles a favor: " + golesAFavor + " Goles en contra: " + golesEnContra + " Grupo: " + grupo + " Diferencia de goles: " + diferenciaGoles());
    }    
}