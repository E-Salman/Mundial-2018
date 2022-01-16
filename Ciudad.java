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
public class Ciudad {
    private String nombre;
    private double superficie;
    private int habitantes;
    private boolean esSede;
    
    public Ciudad(String nombre, double superficie, int habitantes, boolean esSede){
        this.nombre = nombre;
        this.superficie = superficie;
        this.habitantes = habitantes;
        this.esSede = esSede;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public double getSuperficie(){
        return superficie;
    }
    
    public void setSuperficie(double superficie){
        this.superficie = superficie;
    }
    
    public int getHabitantes(){
        return habitantes;
    }
    
    public void setHabitantes(int habitantes){
        this.habitantes = habitantes;
    }
    
    public boolean getEsSede(){
        return esSede;
    }
    
    public void setEsSede(Boolean esSede){
        this.esSede = esSede;
    }
        
    @Override
    public boolean equals(Object ciudad){
        boolean valRet = false;
        if(ciudad.getClass() == String.class){
            valRet = this.equals((String)ciudad);
        }
        else if(ciudad.getClass() == Ciudad.class){    
            valRet = this.equals((Ciudad)ciudad);
        }
        return valRet;
    }
    
    public boolean equals(String ciudad){
        return (this.nombre.equalsIgnoreCase(ciudad));
    }   
    
    public boolean equals(Ciudad ciudad){
        return (this.nombre.equalsIgnoreCase(ciudad.getNombre()));
    }   
    
    public String toString(){
        return ("Nombre: " + nombre + " Superficie: " + superficie + " Habitantes: " + habitantes + " es sede: " + esSede);
    }
}
