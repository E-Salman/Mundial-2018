/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018;

/**
 *
 * @author Admin
 */
public class Partido{
    private Seleccion eq1, eq2;
    private int golesEq1, golesEq2;
    private String ronda, ciudad;
    
    public Partido(){}
    //Prerequisito: dos mismos equipos no pueden volver a enfrentarse
    public Partido(Seleccion eq1, Seleccion eq2, int golesEq1, int golesEq2, String ronda, String ciudad){
        if(eq1.getPais().compareToIgnoreCase(eq2.getPais()) < 0){
            this.eq1 = eq1;
            this.eq2 = eq2;
            this.golesEq1 = golesEq1;
            this.golesEq2 = golesEq2;
        }        
        else{
            this.eq1 = eq2;
            this.eq2 = eq1;
            this.golesEq1 = golesEq2;
            this.golesEq2 = golesEq1;
        }
        this.ronda = ronda;
        this.ciudad = ciudad;
    }
    
    public Seleccion getEq1(){
        return eq1;
    }
    
    public void setEq1(Seleccion eq1){
        this.eq1 = eq1;
    }
    
    public Seleccion getEq2(){
        return eq2;
    }
    
    public void setEq2(Seleccion eq2){
        this.eq2 = eq2;
    }
    
    public int getGolesEq1(){
        return golesEq1;
    }
    
    public void setGolesEq1(int golesEq1){                
        this.golesEq1 = golesEq1;
    }        
    
    public int getGolesEq2(){
        return golesEq2;
    }
    
    public void setGolesEq2(int golesEq2){
        this.golesEq2 = golesEq2;
    }
    
    public String getRonda(){
        return ronda;
    }
    
    public void setRonda(String ronda){
        this.ronda = ronda;
    }
    
    public String getCiudad(){
        return ciudad;
    }
    
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean retVal;
        if(obj.getClass() == Partido.class){
            retVal = this.equals((Partido)obj);
        }
        else{
            retVal = super.equals(obj);
        }
        return retVal;
    }
    
    public boolean equals(Partido partido){
        return partido.eq1.equals(eq1) && partido.eq2.equals(eq2);
    }
    
    public String toString(){
        return "Equipo1: " + eq1.getPais() + " Equipo2: " + eq2.getPais() + " Goles de " + eq1.getPais() + ": " + golesEq1 + " Goles de " + eq2.getPais() + ": " + golesEq2 + " Ronda: " + ronda + " Ciudad: " + ciudad;
    }
}
