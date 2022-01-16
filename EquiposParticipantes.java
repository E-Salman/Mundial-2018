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
public class EquiposParticipantes {
    private Seleccion eq1, eq2;
    
    public EquiposParticipantes(Seleccion eq1, Seleccion eq2){
        if(eq1.getPais().compareToIgnoreCase(eq2.getPais()) < 0){
            this.eq1 = eq1;
            this.eq2 = eq2;
        }
        else{
            this.eq2 = eq1;
            this.eq1 = eq2;
        }        
    }
    
    public Seleccion getEq1(){
        return eq1;
    }
    
    public Seleccion getEq2(){
        return eq2;
    }

    public String toString(){
        return eq1.toString() + eq2.toString();
    }
    
    @Override
    public boolean equals(Object equipos){
        boolean retVal;
        if(EquiposParticipantes.class == equipos.getClass()){
            retVal = equals((EquiposParticipantes)equipos);
        }
        else{
            retVal = super.equals(equipos);
        }
        return retVal;
    }
    
    public boolean equals(EquiposParticipantes equipos){
        return (this.eq1.equals(equipos.eq1) && this.eq2.equals(eq2));
    }
}
