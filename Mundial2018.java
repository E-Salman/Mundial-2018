/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mundial2018;

import Mundial2018.estructuras_auxiliares.ColaPrioridad;
import Mundial2018.estructuras_principales.NodoVert;
import Mundial2018.estructuras_principales.Mapeo;
import Mundial2018.estructuras_principales.Diccionario;
import Mundial2018.estructuras_principales.Grafo;
import Mundial2018.estructuras_auxiliares.Lista;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class Mundial2018 extends JFrame{
    Mundial2018 interfaz;
    Grafo mapa;
    Diccionario equipos;
    Mapeo partidos;
    ColaPrioridad tabla;
    boolean tablaActualizada = false;
    static final Logger logger = Logger.getLogger("Mundial Rusia 2018");
    final String dirIn = "F:/Users/Admin/input.txt"; //Entrada de texto
    final String dirOut = "F:/Users/Admin/logMundial.log"; //Salida del log
    final static Charset ENCODING = StandardCharsets.UTF_8;
    int anchoTexto = 100;
    int ancho50 = (int)(anchoTexto * 0.5);
    int altura = 20;
    int margen = 5;
    
    public Mundial2018(){
        int ancho = 200;
        int altura = 50;
        mapa = new Grafo();
        equipos = new Diccionario();
        partidos = new Mapeo();
        tabla = new ColaPrioridad();
        FileHandler fh;
        try{
            fh = new FileHandler(dirOut);  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);
            cargaInicial();
            mostrarSistema();
        }
        catch (SecurityException | IOException e) {  
            e.printStackTrace();  
        }
        setLayout(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        JLabel lTitulo = new JLabel("Mundial Rusia 2018");
        JButton bABMCiudades = new JButton("ABM Ciudades");
        JButton bABMEquipos = new JButton("ABM Equipos");
        JButton bAltasP = new JButton("Altas de partidos");
        JButton bConsultasEquipos = new JButton("Consultas sobre equipos");
        JButton bConsultasCiudades = new JButton("Consultas sobre ciudades");
        JButton bConsultasViajes = new JButton("Consultas sobre viajes");
        JButton bPosiciones = new JButton("Tabla de posiciones");
        JButton bSistema = new JButton("Mostrar Sistema");
        JButton bSalir = new JButton("Salir");
        setTitle("Mundial Rusia 2018");
        setVisible(true);
        setSize(600, 630);
        lTitulo.setSize(lTitulo.getPreferredSize());
        lTitulo.setLocation((getWidth()  - lTitulo.getWidth()) / 2, (int)(getHeight() * 0.05)); 
        add(lTitulo);
        bABMCiudades.setSize(ancho, altura);
        bABMEquipos.setSize(ancho, altura);
        bAltasP.setSize(ancho, altura);
        bConsultasEquipos.setSize(ancho, altura);
        bConsultasCiudades.setSize(ancho, altura);
        bConsultasViajes.setSize(ancho, altura);
        bPosiciones.setSize(ancho, altura);
        bSistema.setSize(ancho, altura);
        bSalir.setSize(ancho, altura);
        
        bABMCiudades.setLocation((getWidth() - bABMCiudades.getWidth()) / 2, getY() + (bABMCiudades.getHeight()) + 10);
        ponerDebajo(bABMCiudades, bABMEquipos, margen);
        ponerDebajo(bABMEquipos, bAltasP, margen);
        ponerDebajo(bAltasP, bConsultasEquipos, margen);
        ponerDebajo(bConsultasEquipos, bConsultasCiudades, margen);
        ponerDebajo(bConsultasCiudades, bConsultasViajes, margen);
        ponerDebajo(bConsultasViajes, bPosiciones, margen);
        ponerDebajo(bPosiciones, bSistema, margen);
        ponerDebajo(bSistema, bSalir, margen);
        
        bABMCiudades.setLocation(getWidth() / 2 - bABMCiudades.getWidth() / 2, getY() + (bABMCiudades.getHeight()) + 10);                
        add(bABMCiudades); 
        bABMCiudades.addActionListener((ActionEvent ae) -> {
            altasBajasCiudades();
        }); 
        add(bABMEquipos);
        bABMEquipos.addActionListener((ActionEvent ae) -> {
            altasBajasEquipos();
        });
        add(bAltasP);
        bAltasP.addActionListener((ActionEvent ae) -> {
            altasPartidos();
        });
        add(bConsultasEquipos);
        bConsultasEquipos.addActionListener((ActionEvent ae) -> {
            consultasEquipos();
        });
        add(bConsultasCiudades);
        bConsultasCiudades.addActionListener((ActionEvent ae) -> {
            consultasCiudades();
        });
        add(bConsultasViajes);
        bConsultasViajes.addActionListener((ActionEvent ae) -> {
            consultasViajes();
        });
        add(bPosiciones);
        bPosiciones.addActionListener((ActionEvent ae) -> {        
            tablaPosiciones();
        });
        add(bSistema);
        bSistema.addActionListener((ActionEvent ae) -> {
            mostrarSistema();
        });
        add(bSalir);
        bSalir.addActionListener((ActionEvent ae) -> {
            mostrarSistema();
            System.exit(0);            
        });
    }
    
    public static void main(String args[]){
        new Mundial2018();
    }
    
    private void cargaInicial() throws IOException{
        String val1, val2, val3, val4, val5, val6;
        int index;
        Path path = Paths.get(dirIn);
            try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
                String line = null;
                while ((line = reader.readLine()) != null) {
                    switch(line.charAt(0)){
                        case 'E':
                            //altaEquipo(String pais, String dt, String puntos, String golesF, String golesC, String grupo){
                            index = line.indexOf(";");
                            val1 = line.substring(3, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val2 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val3 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val4 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val5 = line.substring(0, index);
                            line = line.substring(index + 2);
                            val6 = line.substring(0);
                            altaEquipo(val1, val2, val4, val5, val6, val3);
                            break;
                            
                        case 'P':
                            //altaPartido(String pais1, String pais2, String goles1, String goles2, String ronda, String ciudad){
                            index = line.indexOf(";");
                            val1 = line.substring(3, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val2 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val3 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val4 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val5 = line.substring(0, index);
                            line = line.substring(index + 2);
                            val6 = line.substring(0);
                            altaPartido(val1, val2, val4, val5, val3, val6);
                            break;
                            
                        case 'C':
                            //altaCiudad(String nombre, String superficie, String habitantes, String esSede){
                            index = line.indexOf(";");
                            val1 = line.substring(3, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val2 = line.substring(0, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val3 = line.substring(0, index);
                            line = line.substring(index + 2);
                            val4 = line.substring(0);
                            altaCiudad(val1, val2, val3, val4);
                            break;
                            
                        case 'R':
                            index = line.indexOf(";");
                            val1 = line.substring(3, index);
                            line = line.substring(index + 2);
                            index = line.indexOf(";");
                            val2 = line.substring(0, index);
                            line = line.substring(index + 2);
                            val3 = line.substring(0);
                            altaRuta(val1, val2, val3);
                            break;
                            
                        default:
                            break;
                    }
                }      
            }
    }
    
    private void ponerDerecha(JComponent refIzquierda, JComponent nObjeto, int margen){
        nObjeto.setLocation(refIzquierda.getX() + refIzquierda.getWidth() + margen, refIzquierda.getY());
    }
    
    private void ponerDebajo(JComponent refSuperior, JComponent nObjeto, int margen){
        nObjeto.setLocation(refSuperior.getX() + (refSuperior.getWidth() - nObjeto.getWidth()), refSuperior.getY() + nObjeto.getHeight() + margen);
    }
    
    private void altasBajasCiudades(){        
        int anchoFila;        
        JFrame interfazABC = new JFrame("Altas - Bajas - Modificaciones Ciudades");
        interfazABC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfazABC.setLayout(null);
        JLabel lTitulo = new JLabel("Altas - Bajas - Modificaciones Ciudades");
        JLabel lACiudadNom = new JLabel("Alta Ciudad - Nombre:");
        JLabel lACiudadSup = new JLabel("Superficie (km^2):");
        JLabel lACiudadHab = new JLabel("Habitantes:");
        JLabel lACiudadSede = new JLabel("Es Sede:");               
        JLabel lBCiudad = new JLabel("Baja Ciudad:");
        JLabel lMCiudad = new JLabel("Modificar Ciudad:");
        JLabel lMCiudadNom = new JLabel("Nuevos Datos - Nombre:");
        JLabel lMCiudadSup = new JLabel("Superficie:");
        JLabel lMCiudadHab = new JLabel("Habitantes:");
        JLabel lMCiudadSede = new JLabel("Es Sede:");
        JLabel lARuta1 = new JLabel("Alta Ruta - Ciudad 1:");
        JLabel lARuta2 = new JLabel("Ciudad 2:");
        JLabel lARutaDist = new JLabel("Distancia:");
        JLabel lBRuta1 = new JLabel("Baja Ruta - Ciudad 1:");
        JLabel lBRuta2 = new JLabel("Baja Ruta - Ciudad 2:");
        JLabel lMRuta1 = new JLabel("Modificar Ruta - Ciudad 1:");
        JLabel lMRuta2 = new JLabel("Ciudad 2:");
        JLabel lMRutaDist = new JLabel("Nueva distancia:");
        JTextField tACiudadNom = new JTextField();
        JTextField tACiudadSup = new JTextField();
        JTextField tACiudadHab = new JTextField();
        JTextField tACiudadSede = new JTextField();
        JTextField tBCiudad = new JTextField();
        JTextField tMCiudad = new JTextField();
        JTextField tMCiudadNom = new JTextField();
        JTextField tMCiudadSup = new JTextField();
        JTextField tMCiudadHab = new JTextField();
        JTextField tMCiudadSede = new JTextField();
        JTextField tARuta1 = new JTextField();
        JTextField tARuta2 = new JTextField();
        JTextField tARutaDist = new JTextField();
        JTextField tBRuta1 = new JTextField();
        JTextField tBRuta2 = new JTextField();   
        JTextField tMRuta1  = new JTextField();
        JTextField tMRuta2 = new JTextField();
        JTextField tMRutaDist = new JTextField();
        JButton bACiudad = new JButton("Ok");
        JButton bBCiudad = new JButton("Ok");
        JButton bMCiudad = new JButton("Ok");
        JButton bARuta = new JButton("Ok");
        JButton bBRuta = new JButton("Ok");
        JButton bMRuta = new JButton("Ok");
        JButton bSalir = new JButton("Salir");
        lTitulo.setSize(lTitulo.getPreferredSize());
        lACiudadNom.setSize(lACiudadNom.getPreferredSize());
        lACiudadSup.setSize(lACiudadSup.getPreferredSize());
        lACiudadHab.setSize(lACiudadHab.getPreferredSize());
        lACiudadSede.setSize(lACiudadSede.getPreferredSize());
        lBCiudad.setSize(lBCiudad.getPreferredSize());
        lMCiudad.setSize(lMCiudad.getPreferredSize());
        lMCiudadNom.setSize(lMCiudadNom.getPreferredSize());
        lMCiudadSup.setSize(lMCiudadSup.getPreferredSize());
        lMCiudadHab.setSize(lMCiudadHab.getPreferredSize());
        lMCiudadSede.setSize(lMCiudadSede.getPreferredSize());
        lARuta1.setSize(lARuta1.getPreferredSize());
        lARuta2.setSize(lARuta2.getPreferredSize());
        lARutaDist.setSize(lARutaDist.getPreferredSize());
        lBRuta1.setSize(lBRuta1.getPreferredSize());
        lBRuta2.setSize(lBRuta1.getPreferredSize());
        lMRuta1.setSize(lMRuta1.getPreferredSize());
        lMRuta2.setSize(lMRuta2.getPreferredSize());
        lMRutaDist.setSize(lMRutaDist.getPreferredSize());
        tACiudadNom.setSize(anchoTexto, altura);        
        tACiudadSup.setSize(ancho50, altura);
        tACiudadHab.setSize(ancho50, altura);
        tACiudadSede.setSize(ancho50, altura);
        tBCiudad.setSize(anchoTexto, altura);
        tMCiudad.setSize(anchoTexto, altura);
        tMCiudadNom.setSize(anchoTexto, altura);
        tMCiudadSup.setSize(ancho50, altura);
        tMCiudadHab.setSize(ancho50, altura);
        tMCiudadSede.setSize(ancho50, altura);
        tBRuta1.setSize(anchoTexto, altura);
        tBRuta2.setSize(anchoTexto, altura);
        tARuta1.setSize(anchoTexto, altura);
        tARuta2.setSize(anchoTexto, altura);
        tARutaDist.setSize(ancho50, altura);
        tMRuta1.setSize(anchoTexto, altura);
        tMRuta2.setSize(anchoTexto, altura);
        tMRutaDist.setSize(ancho50, altura);
        bACiudad.setSize(ancho50, altura);        
        bBCiudad.setSize(ancho50, altura);
        bMCiudad.setSize(ancho50, altura);
        bARuta.setSize(ancho50, altura);
        bMRuta.setSize(ancho50, altura);
        bBRuta.setSize(ancho50, altura);
        bSalir.setSize(anchoTexto, altura);        
        
        anchoFila = lMCiudad.getWidth() + tMCiudad.getWidth() + lMCiudadNom.getWidth() + tMCiudadNom.getWidth() + lMCiudadSup.getWidth() + tMCiudadSup.getWidth() + 
                lMCiudadHab.getWidth() + tMCiudadHab.getWidth() + lMCiudadSede.getWidth() + tMCiudadSede.getWidth() + bMCiudad.getWidth() + margen * 4;
        interfazABC.setSize((int)(anchoFila * 1.25), altura * 20 + margen * 12);
        lTitulo.setLocation((interfazABC.getWidth()  - lTitulo.getWidth()) / 2, (int)(interfazABC.getHeight() * 0.1));
        
        lMCiudad.setLocation((interfazABC.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 4 + margen);
        ponerDerecha(lMCiudad, tMCiudad, margen);
        ponerDerecha(tMCiudad, lMCiudadNom, margen);     
        ponerDerecha(tMCiudad, lMCiudadNom, margen);     
        ponerDerecha(lMCiudadNom, tMCiudadNom, margen);     
        ponerDerecha(tMCiudadNom, lMCiudadSup, margen);     
        ponerDerecha(lMCiudadSup, tMCiudadSup, margen);     
        ponerDerecha(tMCiudadSup, lMCiudadHab, margen);     
        ponerDerecha(lMCiudadHab, tMCiudadHab, margen);     
        ponerDerecha(tMCiudadHab, lMCiudadSede, margen);     
        ponerDerecha(lMCiudadSede, tMCiudadSede, margen);     
        ponerDerecha(tMCiudadSede, bMCiudad, margen);     
                        
        anchoFila = lACiudadNom.getWidth() + lACiudadSup.getWidth() + lACiudadHab.getWidth() + lACiudadSede.getWidth() + tACiudadNom.getWidth()
                + tACiudadSup.getWidth() + tACiudadHab.getWidth() + tACiudadSede.getWidth() + bACiudad.getWidth() + margen * 12;            
        
        lACiudadNom.setLocation((interfazABC.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 2);
        ponerDerecha(lACiudadNom, tACiudadNom, margen);
        ponerDerecha(tACiudadNom, lACiudadSup, margen);
        ponerDerecha(lACiudadSup, tACiudadSup, margen);
        ponerDerecha(tACiudadSup, lACiudadHab, margen);        
        ponerDerecha(lACiudadHab, tACiudadHab, margen);        
        ponerDerecha(tACiudadHab, lACiudadSede, margen);                
        ponerDerecha(lACiudadSede, tACiudadSede, margen);                
        ponerDerecha(tACiudadSede, bACiudad, margen);
        
        anchoFila = lBCiudad.getWidth() + tBCiudad.getWidth() + bBCiudad.getWidth() + margen * 3;
        lBCiudad.setLocation((interfazABC.getWidth() - anchoFila) / 2, lMCiudadNom.getY() + altura * 2 + margen);
        ponerDerecha(lBCiudad, tBCiudad, margen);
        ponerDerecha(tBCiudad, bBCiudad, margen);
        
        anchoFila = lARuta1.getWidth() + lARuta2.getWidth() + lARutaDist.getWidth() + tARuta1.getWidth() + tARuta2.getWidth() + tARutaDist.getWidth() + bARuta.getWidth() + margen * 7;
        
        lARuta1.setLocation((interfazABC.getWidth() - anchoFila) / 2, lBCiudad.getY() + altura * 2 + margen);
        ponerDerecha(lARuta1, tARuta1, margen);
        ponerDerecha(tARuta1, lARuta2, margen);
        ponerDerecha(lARuta2, tARuta2, margen);
        ponerDerecha(tARuta2, lARutaDist, margen);
        ponerDerecha(lARutaDist, tARutaDist, margen);
        ponerDerecha(tARutaDist, bARuta, margen);
        
        anchoFila = lBRuta1.getWidth() + lBRuta2.getWidth() + tBRuta1.getWidth() + tBRuta2.getWidth() + bBRuta.getWidth() + margen * 5;
        lBRuta1.setLocation((interfazABC.getWidth() - anchoFila) / 2, lARuta1.getY() + altura * 2 + margen);
        ponerDerecha(lBRuta1, tBRuta1, margen);
        ponerDerecha(tBRuta1, lBRuta2, margen);
        ponerDerecha(lBRuta2, tBRuta2, margen);
        ponerDerecha(tBRuta2, bBRuta, margen);
        
        anchoFila = lMRuta1.getWidth() + lMRuta2.getWidth() + lMRutaDist.getWidth() + tMRuta1.getWidth() + tMRuta2.getWidth() + tMRutaDist.getWidth() + bMRuta.getWidth() + margen * 7;
        
        lMRuta1.setLocation((interfazABC.getWidth() - anchoFila) / 2, lBRuta1.getY() + altura * 2 + margen);
        ponerDerecha(lMRuta1, tMRuta1, margen);
        ponerDerecha(tMRuta1, lMRuta2, margen);
        ponerDerecha(lMRuta2, tMRuta2, margen);
        ponerDerecha(tMRuta2, lMRutaDist, margen);
        ponerDerecha(lMRutaDist, tMRutaDist, margen);
        ponerDerecha(tMRutaDist, bMRuta, margen);
        
        bSalir.setLocation((interfazABC.getWidth()  - bSalir.getWidth()) / 2, bMRuta.getY() + bSalir.getHeight() + 25);

        interfazABC.add(lTitulo);
        interfazABC.add(lACiudadNom);
        interfazABC.add(lACiudadSup);
        interfazABC.add(lACiudadHab);
        interfazABC.add(lACiudadSede);
        interfazABC.add(lBCiudad);
        interfazABC.add(tMCiudad);
        interfazABC.add(tACiudadNom);
        interfazABC.add(tACiudadSup);
        interfazABC.add(tACiudadHab);
        interfazABC.add(tACiudadSede);
        interfazABC.add(tBCiudad);
        interfazABC.add(lMCiudad);
        interfazABC.add(lMCiudadNom);
        interfazABC.add(lMCiudadSup);
        interfazABC.add(lMCiudadHab);
        interfazABC.add(lMCiudadSede);
        interfazABC.add(tMCiudadNom);
        interfazABC.add(tMCiudadSup);
        interfazABC.add(tMCiudadHab);
        interfazABC.add(tMCiudadSede);        
        interfazABC.add(lARuta1);
        interfazABC.add(lARuta2);
        interfazABC.add(lARutaDist);
        interfazABC.add(lBRuta1);        
        interfazABC.add(lBRuta2);  
        interfazABC.add(lMRuta1);        
        interfazABC.add(lMRuta2);
        interfazABC.add(lMRutaDist);
        interfazABC.add(tARuta1);
        interfazABC.add(tARuta2);
        interfazABC.add(tARuta1);
        interfazABC.add(tARutaDist);
        interfazABC.add(tBRuta1);
        interfazABC.add(tBRuta2);
        interfazABC.add(tMRuta1);
        interfazABC.add(tMRuta2);
        interfazABC.add(tMRutaDist);
        interfazABC.add(bACiudad);
        interfazABC.add(bBCiudad);
        interfazABC.add(bMCiudad);
        interfazABC.add(bARuta);
        interfazABC.add(bMRuta);
        interfazABC.add(bBRuta);
        interfazABC.add(bSalir);        
        interfazABC.setVisible(true);
        bACiudad.addActionListener((ActionEvent ae) -> {
            altaCiudad(tACiudadNom.getText(), tACiudadSup.getText(), tACiudadHab.getText(), tACiudadSede.getText());            
        });
        bBCiudad.addActionListener((ActionEvent ae) -> {
            bajaCiudad(tBCiudad.getText());
        });
        bMCiudad.addActionListener((ActionEvent ae) -> {
            modificarCiudad(tMCiudad.getText(), tMCiudadNom.getText(), tMCiudadSup.getText(), tMCiudadHab.getText(), tMCiudadSede.getText());
        });
        bARuta.addActionListener((ActionEvent ae) -> {
            altaRuta(tARuta1.getText(), tARuta2.getText(), tARutaDist.getText());
        });
        bMRuta.addActionListener((ActionEvent ae) -> {
            modificarRuta(tMRuta1.getText(), tMRuta2.getText(), tMRutaDist.getText());
        });
        bBRuta.addActionListener((ActionEvent ae) -> {
            bajaRuta(tBRuta1.getText(), tBRuta2.getText());
        });        
        bSalir.addActionListener((ActionEvent ae) -> {
            interfazABC.dispose();
        });
    }
    
    private void altaCiudad(String nombre, String superficie, String habitantes, String esSede){
        double superficieDouble;        
        int habitantesInt;
        boolean esSedeBool;        
        String error = "";
        try{
            if(nombre.equals("")) error =  "Debe ingresar el nombre de la ciudad\n";
            if(superficie.equals("")) error = error + "Debe ingresar la superficie de la ciudad\n";
            if(habitantes.equals("")) error = error + "Debe ingresar el numero de habitantes de la ciudad\n";
            if(esSede.equals("")) error = error + "Debe especificar si la ciudad es una sede del mundial o no\n";
            if(!error.equals("")){
                throw new InputException(error);
            }
            superficieDouble = convertDouble(superficie);
            if(superficieDouble < 1) error = error + "La superficie de la ciudad debe ser mayor a cero\n";
            habitantesInt = convertInt(habitantes);
            if(habitantesInt < 0) error = error + "Los habitantes de la ciudad no pueden ser un numero negativo\n";
            if(!error.equals("")){
                throw new InputException(error);
            }
            esSedeBool = convertBoolean(esSede);
            if(mapa.insertarVertice(new Ciudad(nombre, superficieDouble, habitantesInt, esSedeBool))){
                logger.log(Level.INFO, "Ciudad {0} dada de alta exitosamente", nombre);  
            }
            else{
                logger.log(Level.INFO, "La ciudad {0} no pudo darse de alta", nombre);
            }
        }
        catch(InputException e){            
            logger.log(Level.WARNING, e.getMessage());            
        }        
    }    
    
    //Opcion 1: considerar que las ciudades tienen nombre unico
    //Opcion 2: hacer que para borrar una ciudad tambien se pasen todos sus datos (superficie, habitantes, etc)
    //Se opto por la opcion 1
    private void bajaCiudad(String nombreCiudad){
        try{
            if(nombreCiudad.equals("")) throw new InputException("Debe ingresar el nombre de la ciudad a eliminar");
            if(mapa.eliminarVertice(nombreCiudad)){
                logger.log(Level.INFO, "La ciudad {0} fue dada de baja con exito", nombreCiudad);
            }
            else{
                logger.log(Level.INFO, "La ciudad {0} no se pudo eliminar", nombreCiudad);
            }
        }
        catch(InputException e){
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    
    private void modificarCiudad(String nombreOriginal, String nombreNuevo, String supNueva, String habNuevo, String esSedeNuevo){
        boolean esSede;
        double superficieDouble;
        int habitantesInt;
        Ciudad ciudad;
        Object auxObj;
        String error = "";
        try{
            if(nombreOriginal.equals("")) error = "Debe ingresar el nombre de la ciudad a modificar\n";
            if(nombreNuevo.equals("") && supNueva.equals("") && habNuevo.equals("") && esSedeNuevo.equals("")){
                error = error + "Debe modificar al menos un atributo de la ciudad\n";
            }
            if(!error.equals("")) throw new InputException(error);
            auxObj = mapa.recuperarDato(nombreOriginal);
            if(auxObj != null){
                ciudad = (Ciudad)auxObj;
                if(!nombreNuevo.equals("")){
                    ciudad.setNombre(nombreNuevo);
                    logger.log(Level.INFO, "Nuevo nombre de la ciudad: {0}", nombreNuevo);
                }
                if(!supNueva.equals("")){
                    superficieDouble = convertDouble(supNueva);
                    if(superficieDouble < 1) throw new InputException("La superficie de la ciudad debe ser mayor a cero");
                    ciudad.setSuperficie(superficieDouble);
                    logger.log(Level.INFO, "Nueva superficie de la ciudad: {0}", supNueva);
                }
                if(!habNuevo.equals("")){
                    habitantesInt = convertInt(habNuevo);
                    if(habitantesInt < 0) throw new InputException("Los habitantes de la ciudad no pueden ser un numero negativo");
                    ciudad.setHabitantes(habitantesInt);
                    logger.log(Level.INFO, "Nueva cantidad de habitantes de la ciudad: {0}", habNuevo);
                }
                if(!esSedeNuevo.equals("")){
                    esSede = convertBoolean(esSedeNuevo);
                    ciudad.setEsSede(esSede);
                    if(esSede){
                        logger.log(Level.INFO, "La ciudad ahora es sede del mundial");                    
                    }
                    else{
                        logger.log(Level.INFO, "La ciudad ahora no es sede del mundial");                    
                    }             
                }
            }
            else throw new InputException("No se encontro la ciudad a modificar");            
        }
        catch(InputException e){
            logger.log(Level.WARNING, e.getMessage());            
        }        
    }
    
    private void altaRuta(String ciudad1, String ciudad2, String distancia){
        double distanciaDouble;         
        String error = "";
        try{
            if(ciudad1.equals("")) error =  "Debe ingresar el nombre de la primer ciudad\n";
            if(ciudad2.equals("")) error = error + "Debe ingresar el nombre de la segunda ciudad\n";
            if(distancia.equals("")) error = error + "Debe ingresar la distancia de la ruta\n";
            if(mapa.existeArco(ciudad1, ciudad2)) error = error + "Ya existe un arco entre esas dos ciudades\n";
            if(!error.equals("")) throw new InputException(error);           
            distanciaDouble = convertDouble(distancia);
            if(distanciaDouble < 1) throw new InputException("La distancia entre ciudades debe ser mayor a cero");            
            if(mapa.insertarArco(ciudad1, ciudad2, distanciaDouble)){
                logger.log(Level.INFO, "Ruta dada de alta exitosamente");  
            }
            else{
                logger.log(Level.INFO, "La ruta no pudo darse de alta");
            }
        }
        catch(InputException e){            
            logger.log(Level.WARNING, e.getMessage());            
        }     
    }
    
    private void modificarRuta(String ciudad1, String ciudad2, String distancia){
        double distanciaDouble;         
        String error = "";
        try{
            if(ciudad1.equals("")) error =  "Debe ingresar el nombre de la primer ciudad\n";
            if(ciudad2.equals("")) error = error + "Debe ingresar el nombre de la segunda ciudad\n";
            if(distancia.equals("")) error = error + "Debe ingresar la nueva distancia de la ruta\n";
            if(!mapa.existeArco(ciudad1, ciudad2)) error = error + "No se encontro una ruta entre las dos ciudades\n";
            if(!error.equals("")) throw new InputException(error);
            distanciaDouble = convertDouble(distancia);
            if(distanciaDouble < 1) throw new InputException("La distancia entre ciudades debe ser mayor a cero");
            if(mapa.modificarEtiqueta(ciudad1, ciudad2, distanciaDouble)){
                mapa.modificarEtiqueta(ciudad2, ciudad1, distanciaDouble);
                logger.log(Level.INFO, "La nueva distancia de la ruta es {0}", distancia);  
            }
            else{
                logger.log(Level.INFO, "La ruta no pudo modificarse", distancia);
            }
        }
        catch(InputException e){            
            logger.log(Level.WARNING, e.getMessage());            
        }     
    }
    
    private void bajaRuta(String ciudad1, String ciudad2){
        String error = "";
        try{
            if(ciudad1.equals("")) error =  "Debe ingresar el nombre de la primer ciudad\n";
            if(ciudad2.equals("")) error = error + "Debe ingresar el nombre de la segunda ciudad\n";
            if(!mapa.existeArco(ciudad1, ciudad2)) error = error + "No se encontro una ruta entre las dos ciudades\n";
            if(!error.equals("")) throw new InputException(error);
            if(mapa.eliminarArco(ciudad1, ciudad2)){
                logger.log(Level.INFO, "Se elimino la ruta con exito");  
            }
            else{
                logger.log(Level.INFO, "No se pudo eliminar la ruta");
            }
        }
        catch(InputException e){
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    
    private void altasBajasEquipos(){
        int anchoFila;
        JFrame interfazABE = new JFrame("Altas - Bajas - Modificaciones Equipos");
        interfazABE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfazABE.setLayout(null);
        JLabel lTitulo = new JLabel("Altas - Bajas - Modificaciones Equipos");
        JLabel lAEquipoPais = new JLabel("Alta Equipo - Pais:");
        JLabel lEquipoDT = new JLabel("Director Tecnico:");
        JLabel lAEquipoPuntos = new JLabel("Puntos:");
        JLabel lAEquipoGolesFavor = new JLabel("Goles a favor:");               
        JLabel lAEquipoGolesContra = new JLabel("Goles en contra:");               
        JLabel lAEquipoGrupo = new JLabel("Grupo:");   
        JLabel lBEquipo = new JLabel("Baja Equipo:");
        JLabel lMEquipo = new JLabel("Modificar Equipo del pais:");
        JLabel lMEquipoNom = new JLabel("Nuevos Datos - Pais:");
        JLabel lMEquipoDT = new JLabel("Director tecnico:");
        JLabel lMEquipoPuntos = new JLabel("Puntos:");
        JLabel lMEquipoGolesFavor = new JLabel("Goles a favor:");
        JLabel lMEquipoGolesContra = new JLabel("Goles en contra:");
        JLabel lMEquipoGrupo = new JLabel("Grupo:");
        JTextField tAEquipoPais = new JTextField();
        JTextField tAEquipoDT = new JTextField();
        JTextField tAEquipoPuntos = new JTextField();
        JTextField tAEquipoGolesFavor = new JTextField();
        JTextField tAEquipoGolesContra = new JTextField();
        JTextField tAEquipoGrupo = new JTextField();
        JTextField tBEquipo = new JTextField();
        JTextField tMEquipo = new JTextField();
        JTextField tMEquipoPais = new JTextField();
        JTextField tMEquipoDT = new JTextField();
        JTextField tMEquipoPuntos = new JTextField();
        JTextField tMEquipoGolesFavor = new JTextField();
        JTextField tMEquipoGolesContra = new JTextField();
        JTextField tMEquipoGrupo = new JTextField();
        JButton bAEquipo = new JButton("Ok");
        JButton bBEquipo = new JButton("Ok");
        JButton bMEquipo = new JButton("Ok");
        JButton bSalir = new JButton("Salir");
        lTitulo.setSize(lTitulo.getPreferredSize());
        lAEquipoPais.setSize(lAEquipoPais.getPreferredSize());
        lEquipoDT.setSize(lEquipoDT.getPreferredSize());
        lAEquipoPuntos.setSize(lAEquipoPuntos.getPreferredSize());
        lAEquipoGolesFavor.setSize(lAEquipoGolesFavor.getPreferredSize());
        lAEquipoGolesContra.setSize(lAEquipoGolesContra.getPreferredSize());
        lAEquipoGrupo.setSize(lAEquipoGrupo.getPreferredSize());
        lBEquipo.setSize(lBEquipo.getPreferredSize());
        lMEquipo.setSize(lMEquipo.getPreferredSize());
        lMEquipoNom.setSize(lMEquipoNom.getPreferredSize());
        lMEquipoDT.setSize(lMEquipoDT.getPreferredSize());
        lMEquipoPuntos.setSize(lMEquipoPuntos.getPreferredSize());
        lMEquipoGolesFavor.setSize(lMEquipoGolesFavor.getPreferredSize());        
        lMEquipoGolesContra.setSize(lMEquipoGolesContra.getPreferredSize());        
        lMEquipoGrupo.setSize(lMEquipoGrupo.getPreferredSize());
        tAEquipoPais.setSize(anchoTexto, altura);        
        tAEquipoDT.setSize(anchoTexto, altura);
        tAEquipoPuntos.setSize(ancho50, altura);
        tAEquipoGolesFavor.setSize(ancho50, altura);
        tAEquipoGolesContra.setSize(ancho50, altura);
        tAEquipoGrupo.setSize(ancho50, altura);
        tBEquipo.setSize(anchoTexto, altura);
        tMEquipo.setSize(anchoTexto, altura);
        tMEquipoPais.setSize(anchoTexto, altura);
        tMEquipoDT.setSize(anchoTexto, altura);
        tMEquipoPuntos.setSize(ancho50, altura);
        tMEquipoGolesFavor.setSize(ancho50, altura);
        tMEquipoGolesContra.setSize(ancho50, altura);
        tMEquipoGrupo.setSize(ancho50, altura);
        bAEquipo.setSize(ancho50, altura);        
        bBEquipo.setSize(ancho50, altura);
        bMEquipo.setSize(ancho50, altura);
        bSalir.setSize(anchoTexto, altura);
        
        anchoFila = lMEquipo.getWidth() + tMEquipo.getWidth() + lMEquipoNom.getWidth() + tMEquipoPais.getWidth() + lMEquipoDT.getWidth() + tMEquipoDT.getWidth() + lMEquipoPuntos.getWidth() + tMEquipoPuntos.getWidth() + 
                lMEquipoGolesFavor.getWidth() + tMEquipoGolesFavor.getWidth() + lMEquipoGolesContra.getWidth() + tMEquipoGolesContra.getWidth() + lMEquipoGrupo.getWidth() + tMEquipoGrupo.getWidth() + bMEquipo.getWidth() + margen * 4;
        interfazABE.setSize((int)(anchoFila * 1.25), altura * 15);
        lTitulo.setLocation((interfazABE.getWidth()  - lTitulo.getWidth()) / 2, (int)(interfazABE.getHeight() * 0.1));
        
        lMEquipo.setLocation((interfazABE.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 4 + margen);
        ponerDerecha(lMEquipo, tMEquipo, margen);
        ponerDerecha(tMEquipo, lMEquipoNom, margen);     
        ponerDerecha(tMEquipo, lMEquipoNom, margen);     
        ponerDerecha(lMEquipoNom, tMEquipoPais, margen);     
        ponerDerecha(tMEquipoPais, lMEquipoDT, margen);     
        ponerDerecha(lMEquipoDT, tMEquipoDT, margen);     
        ponerDerecha(tMEquipoDT, lMEquipoPuntos, margen);     
        ponerDerecha(lMEquipoPuntos, tMEquipoPuntos, margen);     
        ponerDerecha(tMEquipoPuntos, lMEquipoGolesFavor, margen);     
        ponerDerecha(lMEquipoGolesFavor, tMEquipoGolesFavor, margen);     
        ponerDerecha(tMEquipoGolesFavor, lMEquipoGolesContra, margen);     
        ponerDerecha(lMEquipoGolesContra, tMEquipoGolesContra, margen);
        ponerDerecha(tMEquipoGolesContra, lMEquipoGrupo, margen);
        ponerDerecha(lMEquipoGrupo, tMEquipoGrupo, margen);
        ponerDerecha(tMEquipoGrupo, bMEquipo, margen);
                        
        anchoFila = lAEquipoPais.getWidth() + lEquipoDT.getWidth() + lAEquipoPuntos.getWidth() + lAEquipoGolesFavor.getWidth() + lAEquipoGolesContra.getWidth() + lAEquipoGrupo.getWidth() + tAEquipoPais.getWidth()
                + tAEquipoDT.getWidth() + tAEquipoPuntos.getWidth() + tAEquipoGolesFavor.getWidth() + tAEquipoGolesContra.getWidth() + tAEquipoGrupo.getWidth() + bAEquipo.getWidth() + margen * 12;            
        
        lAEquipoPais.setLocation((interfazABE.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 2);
        ponerDerecha(lAEquipoPais, tAEquipoPais, margen);
        ponerDerecha(tAEquipoPais, lEquipoDT, margen);
        ponerDerecha(lEquipoDT, tAEquipoDT, margen);
        ponerDerecha(tAEquipoDT, lAEquipoPuntos, margen);        
        ponerDerecha(lAEquipoPuntos, tAEquipoPuntos, margen);        
        ponerDerecha(tAEquipoPuntos, lAEquipoGolesFavor, margen);                
        ponerDerecha(lAEquipoGolesFavor, tAEquipoGolesFavor, margen);                
        ponerDerecha(tAEquipoGolesFavor, lAEquipoGolesContra, margen);
        ponerDerecha(lAEquipoGolesContra, tAEquipoGolesContra, margen);
        ponerDerecha(tAEquipoGolesContra, lAEquipoGrupo, margen);
        ponerDerecha(lAEquipoGrupo, tAEquipoGrupo, margen);
        ponerDerecha(tAEquipoGrupo, bAEquipo, margen);
        
        anchoFila = lBEquipo.getWidth() + tBEquipo.getWidth() + bBEquipo.getWidth() + margen * 3;
        lBEquipo.setLocation((interfazABE.getWidth() - anchoFila) / 2, lMEquipoNom.getY() + altura * 2 + margen);
        ponerDerecha(lBEquipo, tBEquipo, margen);
        ponerDerecha(tBEquipo, bBEquipo, margen);
        
        bSalir.setLocation((interfazABE.getWidth()  - bSalir.getWidth()) / 2, bBEquipo.getY() + bSalir.getHeight() + 25);

        interfazABE.add(lTitulo);
        interfazABE.add(lAEquipoPais);
        interfazABE.add(lEquipoDT);
        interfazABE.add(lAEquipoPuntos);
        interfazABE.add(lAEquipoGolesFavor);
        interfazABE.add(lAEquipoGolesContra);
        interfazABE.add(lBEquipo);        
        interfazABE.add(tMEquipo);
        interfazABE.add(tAEquipoPais);
        interfazABE.add(tAEquipoDT);
        interfazABE.add(tAEquipoPuntos);
        interfazABE.add(tAEquipoGolesFavor);
        interfazABE.add(tAEquipoGolesContra);
        interfazABE.add(lAEquipoGrupo);
        interfazABE.add(tBEquipo);
        interfazABE.add(lMEquipo);
        interfazABE.add(lMEquipoNom);
        interfazABE.add(lMEquipoDT);
        interfazABE.add(lMEquipoPuntos);
        interfazABE.add(lMEquipoGolesFavor);
        interfazABE.add(lMEquipoGolesContra);
        interfazABE.add(lMEquipoGrupo);
        interfazABE.add(tMEquipoPais);
        interfazABE.add(tMEquipoDT);
        interfazABE.add(tMEquipoPuntos);
        interfazABE.add(tMEquipoGolesFavor);
        interfazABE.add(tMEquipoGolesContra);   
        interfazABE.add(tAEquipoGrupo);
        interfazABE.add(tMEquipoGrupo); 
        interfazABE.add(bAEquipo);
        interfazABE.add(bBEquipo);
        interfazABE.add(bMEquipo);
        interfazABE.add(bSalir);        
        interfazABE.setVisible(true);
        bAEquipo.addActionListener((ActionEvent ae) -> {
            altaEquipo(tAEquipoPais.getText(), tAEquipoDT.getText(), tAEquipoPuntos.getText(), tAEquipoGolesFavor.getText(), tAEquipoGolesContra.getText(), tAEquipoGrupo.getText());            
        });
        bBEquipo.addActionListener((ActionEvent ae) -> {
            bajaEquipo(tBEquipo.getText());
        });
        bMEquipo.addActionListener((ActionEvent ae) -> {
            modificarEquipo(tMEquipo.getText(), tMEquipoPais.getText(), tMEquipoDT.getText(), tMEquipoPuntos.getText(), tMEquipoGolesFavor.getText(), tMEquipoGolesContra.getText(), tMEquipoGrupo.getText());
        });
        bSalir.addActionListener((ActionEvent ae) -> {
            interfazABE.dispose();
        });
    }
    
    private void altaEquipo(String pais, String dt, String puntos, String golesF, String golesC, String grupo){
        int puntosInt, golesFInt, golesCInt;
        Seleccion equipo;
        String error = "";
        try{
            if(pais.equals("")) error = "Debe ingresar el pais del equipo\n";
            if(dt.equals("")) error = error + "Debe ingresar el director tecnico del equipo\n";
            if(puntos.equals("")) error = error + "Debe ingresar los puntos del equipo\n"; 
            if(golesF.equals("")) error = error + "Debe ingresar la cantidad de goles a favor del equipo\n";
            if(golesC.equals("")) error = error + "Debe ingresar la cantidad de goles en contra del equipo\n";
            if(grupo.equals("")) error = error + "Debe ingresar el grupo del equipo\n";
            if(!error.equals("")) throw new InputException(error);
            puntosInt = convertInt(puntos);
            golesFInt = convertInt(golesF);
            golesCInt = convertInt(golesC);
            if(puntosInt < 0) error = error + "Los puntos del equipo deben ser un numero positivo\n";
            if(golesFInt < 0 || golesCInt < 0) error = error + "Los goles deben ser un numero positivo\n";
            if(!error.equals("")) throw new InputException(error);
            equipo = new Seleccion(pais, dt, puntosInt, golesFInt, golesCInt, convertChar(grupo));
            if(equipos.insertar(equipo.getPais(), equipo)){
                logger.log(Level.INFO, "Equipo {0} dado de alta exitosamente", pais);  
            }
            else{
                logger.log(Level.INFO, "Equipo {0} no pudo darse de alta", pais);
            }
            tablaActualizada = false;
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());            
        }     
    }
    
    private void modificarEquipo(String pais, String paisNuevo, String dt, String puntos, String golesF, String golesC, String grupo){
        int puntosInt;
        String error = "";
        try{
            if(pais.equals("")) error = "Debe ingresar el nombre del pais del equipo\n";
            if(paisNuevo.equals("") && dt.equals("") && puntos.equals("") && golesF.equals("") && golesC.equals("")){
                error = error + "Debe de modificar al menos un atributo del equipo\n";
            }
            if(!error.equals("")) throw new InputException(error);
            if(!paisNuevo.equals("")){
                ((Seleccion)(equipos.ubicarNodo(pais))).setPais(paisNuevo);
                logger.log(Level.INFO, "Nuevo nombre del pais del equipo: {0}", paisNuevo);
            }
            if(!dt.equals("")){
                ((Seleccion)(equipos.ubicarNodo(pais))).setDirectorT(dt);
                logger.log(Level.INFO, "Nuevo director tecnico del equipo: {0}", dt);
            }
            if(!puntos.equals("")){
                puntosInt = convertInt(puntos);
                if(puntosInt < 0) throw new InputException("Los puntos del equipo debe ser un numero positivo");
                ((Seleccion)(equipos.ubicarNodo(pais))).setPuntos(convertInt(puntos));
                logger.log(Level.INFO, "Nuevos puntos del equipo: {0}", puntos);
            }
            if(!golesF.equals("")){
                ((Seleccion)(equipos.ubicarNodo(pais))).setPuntos(convertInt(golesF));
                logger.log(Level.INFO, "Nuevos goles a favor del equipo: {0}", golesF);
                
            }
            if(!golesC.equals("")){
                ((Seleccion)(equipos.ubicarNodo(pais))).setPuntos(convertInt(golesC));
                logger.log(Level.INFO, "Nuevos goles a favor del equipo: {0}", golesC);
            }
            if(!grupo.equals("")){
                ((Seleccion)(equipos.ubicarNodo(pais))).setPuntos(convertChar(grupo));
                logger.log(Level.INFO, "Nuevo grupo del equipo: {0}", grupo); 
            }
            tablaActualizada = false;
        }
        catch(InputException e){
            logger.log(Level.WARNING, e.getMessage());            
        }   
    }
    
    private void bajaEquipo(String pais){
        try{
            if(pais.equals("")) throw new InputException("Debe ingresar el nombre pais del equipo a eliminar");
            if(equipos.eliminar(pais)){ 
                logger.log(Level.INFO, "El equipo {0} fue dado de baja con exito", pais);
            }
            else{
                logger.log(Level.INFO, "El equipo {0} no se pudo eliminar", pais);
            }
            tablaActualizada = false;
        }
        catch(InputException e){
                logger.log(Level.WARNING, e.getMessage());
        }
    }    
    
    private void altasPartidos(){
        int anchoFila;        
        JFrame interfazAP = new JFrame("Altas - Bajas - Modificaciones Equipos");
        interfazAP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfazAP.setLayout(null);
        JLabel lTitulo = new JLabel("Alta Partido");
        JLabel lAEquipoPais1 = new JLabel("Alta Equipo - Pais equipo 1:");
        JLabel lAEquipoPais2 = new JLabel("Pais equipo 2:");
        JLabel lAGolesEquipo1 = new JLabel("Goles equipo 1:");
        JLabel lAGolesEquipo2 = new JLabel("Goles equipo 2:");               
        JLabel lARonda = new JLabel("Ronda:");
        JLabel lACiudad = new JLabel("Ciudad:");                
        JTextField tAEquipoPais1 = new JTextField();
        JTextField tAEquipoPais2 = new JTextField();
        JTextField tAGolesEquipo1 = new JTextField();
        JTextField tAGolesEquipo2 = new JTextField();
        JTextField tARonda = new JTextField();
        JTextField tACiudad = new JTextField();                
        JButton bAPartido = new JButton("Ok");        
        JButton bSalir = new JButton("Salir");
        lTitulo.setSize(lTitulo.getPreferredSize());
        lAEquipoPais1.setSize(lAEquipoPais1.getPreferredSize());
        lAEquipoPais2.setSize(lAEquipoPais2.getPreferredSize());
        lAGolesEquipo1.setSize(lAGolesEquipo1.getPreferredSize());
        lAGolesEquipo2.setSize(lAGolesEquipo2.getPreferredSize());
        lARonda.setSize(lARonda.getPreferredSize());
        lACiudad.setSize(lACiudad.getPreferredSize());
        tAEquipoPais1.setSize(anchoTexto, altura);
        tAEquipoPais2.setSize(anchoTexto, altura);
        tAGolesEquipo1.setSize(ancho50, altura);
        tAGolesEquipo2.setSize(ancho50, altura);
        tARonda.setSize(ancho50, altura);
        tACiudad.setSize(anchoTexto, altura);
        bAPartido.setSize(ancho50, altura);
        bSalir.setSize(anchoTexto, altura);
        
        anchoFila = lAEquipoPais1.getWidth() + lAEquipoPais2.getWidth() + lAGolesEquipo1.getWidth() + lAGolesEquipo2.getWidth() + lARonda.getWidth() + lACiudad.getWidth() + tAEquipoPais1.getWidth()
                + tAEquipoPais2.getWidth() + tAGolesEquipo1.getWidth() + tAGolesEquipo2.getWidth() + tARonda.getWidth() + tACiudad.getWidth() + bAPartido.getWidth() + margen * 12;                       
        
        interfazAP.setSize((int)(anchoFila * 1.25), altura * 10);
        lTitulo.setLocation((interfazAP.getWidth()  - lTitulo.getWidth()) / 2, (int)(interfazAP.getHeight() * 0.1));
        
        lAEquipoPais1.setLocation((interfazAP.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 2);
        ponerDerecha(lAEquipoPais1, tAEquipoPais1, margen);
        ponerDerecha(tAEquipoPais1, lAEquipoPais2, margen);
        ponerDerecha(lAEquipoPais2, tAEquipoPais2, margen);
        ponerDerecha(tAEquipoPais2, lAGolesEquipo1, margen);        
        ponerDerecha(lAGolesEquipo1, tAGolesEquipo1, margen);        
        ponerDerecha(tAGolesEquipo1, lAGolesEquipo2, margen);                
        ponerDerecha(lAGolesEquipo2, tAGolesEquipo2, margen);                
        ponerDerecha(tAGolesEquipo2, lARonda, margen);
        ponerDerecha(lARonda, tARonda, margen);
        ponerDerecha(tARonda, lACiudad, margen);
        ponerDerecha(lACiudad, tACiudad, margen);
        ponerDerecha(tACiudad, bAPartido, margen);        
        
        bSalir.setLocation((interfazAP.getWidth()  - bSalir.getWidth()) / 2, bAPartido.getY() + bSalir.getHeight() + 25);

        interfazAP.add(lTitulo);
        interfazAP.add(lAEquipoPais1);
        interfazAP.add(lAEquipoPais2);
        interfazAP.add(lAGolesEquipo1);
        interfazAP.add(lAGolesEquipo2);
        interfazAP.add(lARonda);
        interfazAP.add(lACiudad);        
        interfazAP.add(tAEquipoPais1);
        interfazAP.add(tAEquipoPais2);
        interfazAP.add(tAGolesEquipo1);
        interfazAP.add(tAGolesEquipo2);
        interfazAP.add(tARonda);
        interfazAP.add(tACiudad);        
        interfazAP.add(bAPartido);
        interfazAP.add(bSalir);        
        interfazAP.setVisible(true);
        bAPartido.addActionListener((ActionEvent ae) -> {
            altaPartido(tAEquipoPais1.getText(), tAEquipoPais2.getText(), tAGolesEquipo1.getText(), tAGolesEquipo2.getText(), tARonda.getText(), tACiudad.getText());            
        });
        bSalir.addActionListener((ActionEvent ae) -> {
            interfazAP.dispose();
        });
    }
    
    private void altaPartido(String pais1, String pais2, String goles1, String goles2, String ronda, String ciudad){
        Seleccion equipo1, equipo2;
        int g1, g2;
        String error = "";
        try{
            if(pais1.equals("")) error = "Debe ingresar el pais del primer equipo\n";
            if(pais2.equals("")) error = error + "Debe ingresar el pais del segundo equipo\n";
            if(goles1.equals("")) error = error + "Debe ingresar los goles del primer equipo\n";
            if(goles2.equals("")) error = error + "Debe ingresar los goles del segundo equipo\n";
            if(ciudad.equals("")) error = error + "Debe ingresar la ciudad donde se jugo el partido\n";
            if(ronda.equals("")) error = error + "Debe ingresar la ronda del partido\n";
            if(!error.equals("")) throw new InputException(error);
            g1 = convertInt(goles1);
            g2 = convertInt(goles2);
            if(g1 < 0 || g2 < 0) throw new InputException ("Los goles de los equipos deben ser un numero positivo\n");
            equipo1 = (Seleccion)equipos.ubicarNodo(pais1);
            equipo2 = (Seleccion)equipos.ubicarNodo(pais2);             
            if(equipo1 != null && equipo2 != null){
                if(mapa.existeVertice(ciudad)){                                        
                    if(partidos.asociar(new EquiposParticipantes(equipo1, equipo2), new Partido(equipo1, equipo2, g1, g2, ronda, ciudad))){
                        equipo1.nuevoEquipoEnfrentado(equipo2);                        
                        equipo1.agregarGolesAFavor(g1);
                        equipo1.agregarGolesEnContra(g2);
                        equipo2.agregarGolesAFavor(g2);
                        equipo2.agregarGolesEnContra(g2);
                        equipo2.nuevoEquipoEnfrentado(equipo1);
                        if(g1 > g2){
                            equipo1.agregarPuntos(3);
                        }
                        else if(g1 < g2){
                            equipo2.agregarPuntos(3);
                        }
                        else if(g1 == g2){
                            equipo1.agregarPuntos(1);
                            equipo2.agregarPuntos(1);
                        }
                        logger.info("Partido agregado con exito");
                        tablaActualizada = false;
                    }
                    else{                        
                        throw new InputException("No se pudo agregar el partido");
                    }
                }
                else{
                    throw new InputException("No se encontro la ciudad");
                }
            }
            else{
                if(equipo1 == null){
                    throw new InputException("El primer equipo no fue encontrado");
                }
                else{
                    throw new InputException("El segundo equipo no fue encontrado");
                }
            }
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());            
        }     
    }
    
    private void consultasEquipos(){
        int anchoFila;
        JFrame interfazCE = new JFrame("Altas - Bajas - Modificaciones Equipos");
        interfazCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfazCE.setLayout(null);
        JLabel lTitulo = new JLabel("Consultas Equipos");
        JLabel lCEPais = new JLabel("Consultar Equipo - Pais:");
        JLabel lMinimo = new JLabel("Listar - Minimo:");
        JLabel lMaximo = new JLabel("Maximo:");
        JLabel lGDif = new JLabel("Listar equipos con diferencia de gol negativa");
        JTextField tCEPais = new JTextField();
        JTextField tMinimo = new JTextField();
        JTextField tMaximo = new JTextField();
        JButton bCEquipo = new JButton("Ok");        
        JButton bMM = new JButton("Ok");
        JButton bGDif = new JButton("Ok");
        JButton bSalir = new JButton("Salir");
        lTitulo.setSize(lTitulo.getPreferredSize());
        lCEPais.setSize(lCEPais.getPreferredSize());
        lMinimo.setSize(lMinimo.getPreferredSize());
        lMaximo.setSize(lMaximo.getPreferredSize());
        lGDif.setSize(lGDif.getPreferredSize());
        tCEPais.setSize(anchoTexto, altura);        
        tMinimo.setSize(anchoTexto, altura);
        tMaximo.setSize(anchoTexto, altura);
        bCEquipo.setSize(ancho50, altura);          
        bMM.setSize(ancho50, altura);
        bGDif.setSize(ancho50, altura);
        bSalir.setSize(anchoTexto, altura);
        
        anchoFila = lMinimo.getWidth() + lMaximo.getWidth() + tMinimo.getWidth() + tMaximo.getWidth() + bMM.getWidth() + margen * 5;           
        interfazCE.setSize((int)(anchoFila * 1.25), altura * 15);
        lTitulo.setLocation((interfazCE.getWidth()  - lTitulo.getWidth()) / 2, (int)(interfazCE.getHeight() * 0.1));
        
        lMinimo.setLocation((interfazCE.getWidth() - anchoFila) / 2, lTitulo.getY() * 2 + altura * 3);
        ponerDerecha(lMinimo, tMinimo, margen);
        ponerDerecha(tMinimo, lMaximo, margen);
        ponerDerecha(lMaximo, tMaximo, margen);
        ponerDerecha(tMaximo, bMM, margen);
        
        anchoFila = lCEPais.getWidth() + tCEPais.getWidth() + bCEquipo.getWidth() + margen * 3;                               
        
        lCEPais.setLocation((interfazCE.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 2);
        ponerDerecha(lCEPais, tCEPais, margen);
        ponerDerecha(lCEPais, tCEPais, margen);        
        ponerDerecha(tCEPais, bCEquipo, margen);        
        
        anchoFila = lGDif.getWidth() + bGDif.getWidth() + margen * 2;
        lGDif.setLocation((interfazCE.getWidth() - anchoFila) / 2, lMinimo.getY() + altura * 2);
        ponerDerecha(lGDif, bGDif, margen);
        
        bSalir.setLocation((interfazCE.getWidth()  - bSalir.getWidth()) / 2, bGDif.getY() + bSalir.getHeight() + 25);
        
        interfazCE.add(lTitulo);
        interfazCE.add(lCEPais);
        interfazCE.add(lMinimo);
        interfazCE.add(lMaximo);
        interfazCE.add(lGDif);
        interfazCE.add(tCEPais);
        interfazCE.add(tMinimo);
        interfazCE.add(tMaximo);
        interfazCE.add(bCEquipo);
        interfazCE.add(bMM);
        interfazCE.add(bGDif);
        interfazCE.add(bSalir);        
        interfazCE.setVisible(true);
        bCEquipo.addActionListener((ActionEvent ae) -> {
            consultarEquipo(tCEPais.getText());            
        });
        bMM.addActionListener((ActionEvent ae) -> {
            listarMinMax(tMinimo.getText(), tMaximo.getText()); 
        });
        bGDif.addActionListener((ActionEvent ae) -> {
            listarGDif(); 
        });
        bSalir.addActionListener((ActionEvent ae) -> {
            interfazCE.dispose();
        });
    }
    
    private void consultarEquipo(String pais){
        Seleccion equipo;
        int longitud;
        try{
            if(pais.equals("")) throw new InputException("Debe ingresar el pais del equipo a consultar");
            equipo = (Seleccion)equipos.ubicarNodo(pais);
            if(equipo != null){
                longitud = equipo.getEquiposEnfrentado().longitud();
                logger.info(equipo.toString());
                if(longitud > 0){
                    for(int i = 1; i <= longitud; i++){
                        logger.log(Level.INFO, "Partidos jugados:\n{0}", partidos.obtenerValores(new EquiposParticipantes(equipo, (Seleccion)equipo.getEquiposEnfrentado().recuperar(i))).toString());
                    }                
                }
                else{
                    logger.info("Este equipo todavia no jugo ningun partido");
                }
            }
            else{
                throw new InputException("No se encontro el equipo");
            }
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());            
        }     
    }
    
    private void listarMinMax(String minimo, String maximo){
        String error = "";
        Lista auxLista = null;
        try{
            if(minimo.equals("")) error = "Debe ingresar el minimo a buscar";
            if(maximo.equals("")) error = error + "Debe ingresar el maximo a buscar";
            if((!minimo.equals("") && !maximo.equals("")) && minimo.compareToIgnoreCase(maximo) > 0) error = "El maximo debe ser mayor al minimo";
            if(!error.equals("")) throw new InputException(error);
            auxLista = equipos.minMax(minimo, maximo);
            logger.log(Level.INFO, "Listado de equipos en el rango dado:\n{0}", auxLista.toString());
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());            
        }  
    }
    
    private void listarGDif(){
        Lista auxLista;
        int longitud;        
        Seleccion equipo;
        logger.info("Listado de equipos con diferencia de gol negativa:\n");
        auxLista = equipos.listarDatos();
        longitud = auxLista.longitud();
        for(int i = 0; i < longitud; i++){
            equipo = (Seleccion)auxLista.recuperar(i);
            if(equipo.diferenciaGoles() < 0){
                logger.info(equipo.toString());
            }
        }
    }
    
    private void consultasCiudades(){
        int anchoFila;
        JFrame interfazCE = new JFrame("Altas - Bajas - Modificaciones Equipos");
        interfazCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfazCE.setLayout(null);
        JLabel lTitulo = new JLabel("Consultas Ciudades");
        JLabel lCCNombre = new JLabel("Consultar Ciudad - Nombre:");
        JTextField tCCNombre = new JTextField();
        JButton bCCiudad = new JButton("Ok");        
        JButton bSalir = new JButton("Salir");
        lTitulo.setSize(lTitulo.getPreferredSize());
        lCCNombre.setSize(lCCNombre.getPreferredSize());
        tCCNombre.setSize(anchoTexto, altura);        
        bCCiudad.setSize(ancho50, altura);    
        bSalir.setSize(anchoTexto, altura);
        
        anchoFila = lCCNombre.getWidth() + tCCNombre.getWidth() + bCCiudad.getWidth() + margen * 12;                               
        interfazCE.setSize((int)(anchoFila * 1.25), altura * 10);
        lTitulo.setLocation((interfazCE.getWidth()  - lTitulo.getWidth()) / 2, (int)(interfazCE.getHeight() * 0.1));
        
        lCCNombre.setLocation((interfazCE.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 2);
        ponerDerecha(lCCNombre, tCCNombre, margen);
        ponerDerecha(lCCNombre, tCCNombre, margen);        
        ponerDerecha(tCCNombre, bCCiudad, margen);        
        
        bSalir.setLocation((interfazCE.getWidth()  - bSalir.getWidth()) / 2, bCCiudad.getY() + bSalir.getHeight() + 25);

        interfazCE.add(lTitulo);
        interfazCE.add(lCCNombre);
        interfazCE.add(tCCNombre);
        interfazCE.add(bCCiudad);
        interfazCE.add(bSalir);        
        interfazCE.setVisible(true);
        bCCiudad.addActionListener((ActionEvent ae) -> {
            consultarCiudad(tCCNombre.getText());            
        });
        bSalir.addActionListener((ActionEvent ae) -> {
            interfazCE.dispose();
        });
    }
    
    private void consultarCiudad(String nomCiudad){
        Ciudad ciudad;
        try{
            if(nomCiudad.equals("")) throw new InputException("Debe ingresar el pais del equipo a consultar");
            ciudad = (Ciudad)mapa.recuperarDato(nomCiudad);
            if(ciudad != null){
                logger.info(ciudad.toString());
            }
            else{
                throw new InputException("No se encontro la ciudad");
            }
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());
        }  
    }
    
    private void consultasViajes(){
        int anchoFila;        
        JFrame interfazCV = new JFrame("Consultas Viajes");
        interfazCV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfazCV.setLayout(null);
        JLabel lTitulo = new JLabel("Consultas Viajes");
        JLabel lCVMenorCiudad1 = new JLabel("Camino mas corto - Ciudad 1:");
        JLabel lCVMenorCiudad2 = new JLabel("Ciudad 2:");
        JLabel lCVMinimaCiudad1 = new JLabel("Camino con menos ciudades - Ciudad 1:");
        JLabel lCVMinimaCiudad2 = new JLabel("Ciudad 2:");
        JLabel lCVTodosCiudad1 = new JLabel("Todos los caminos posibles - Ciudad 1:");
        JLabel lCVTodosCiudad2 = new JLabel("Ciudad 2:");
        JLabel lCVMenorSinCiudad1 = new JLabel("Camino mas corto sin pasar por una ciudad dada - Ciudad 1:");
        JLabel lCVMenorSinCiudad2 = new JLabel("Ciudad 2:");
        JLabel lCVMenorSinCiudad3 = new JLabel("Ciudad a evitar:");
        JTextField tCVMenorCiudad1 = new JTextField();
        JTextField tCVMenorCiudad2 = new JTextField();        
        JTextField tCVMinimaCiudad1 = new JTextField();
        JTextField tCVMinimaCiudad2 = new JTextField();        
        JTextField tCVTodosCiudad1 = new JTextField();
        JTextField tCVTodosCiudad2 = new JTextField();
        JTextField tCVMenorSinCiudad1 = new JTextField();
        JTextField tCVMenorSinCiudad2 = new JTextField();   
        JTextField tCVMenorSinCiudad3  = new JTextField();
        JButton bCVMenor = new JButton("Ok");
        JButton bCVMinima = new JButton("Ok");
        JButton bCVTodos = new JButton("Ok");
        JButton bCVMenorSinCiudad = new JButton("Ok");
        JButton bSalir = new JButton("Salir");
        lTitulo.setSize(lTitulo.getPreferredSize());
        lCVMenorCiudad1.setSize(lCVMenorCiudad1.getPreferredSize());
        lCVMenorCiudad2.setSize(lCVMenorCiudad2.getPreferredSize());        
        lCVMinimaCiudad1.setSize(lCVMinimaCiudad1.getPreferredSize());
        lCVMinimaCiudad2.setSize(lCVMinimaCiudad2.getPreferredSize());
        lCVTodosCiudad1.setSize(lCVTodosCiudad1.getPreferredSize());
        lCVTodosCiudad2.setSize(lCVTodosCiudad2.getPreferredSize());
        lCVMenorSinCiudad1.setSize(lCVMenorSinCiudad1.getPreferredSize());
        lCVMenorSinCiudad2.setSize(lCVMenorSinCiudad2.getPreferredSize());
        lCVMenorSinCiudad3.setSize(lCVMenorSinCiudad3.getPreferredSize());
        tCVMenorCiudad1.setSize(anchoTexto, altura);        
        tCVMenorCiudad2.setSize(ancho50, altura);
        tCVMinimaCiudad1.setSize(anchoTexto, altura);
        tCVMinimaCiudad2.setSize(anchoTexto, altura);
        tCVMenorSinCiudad1.setSize(anchoTexto, altura);
        tCVMenorSinCiudad2.setSize(anchoTexto, altura);
        tCVTodosCiudad1.setSize(anchoTexto, altura);
        tCVTodosCiudad2.setSize(anchoTexto, altura);
        tCVMenorSinCiudad3.setSize(anchoTexto, altura);
        bCVMenor.setSize(ancho50, altura);        
        bCVMinima.setSize(ancho50, altura);
        bCVMenorSinCiudad.setSize(ancho50, altura);
        bCVTodos.setSize(ancho50, altura);
        bSalir.setSize(anchoTexto, altura);        
        
        anchoFila = lCVMenorSinCiudad1.getWidth() + lCVMenorSinCiudad2.getWidth() + lCVMenorSinCiudad3.getWidth() + tCVMenorSinCiudad1.getWidth() + tCVMenorSinCiudad2.getWidth() + tCVMenorSinCiudad3.getWidth() + bCVMenorSinCiudad.getWidth() + margen * 7;
        interfazCV.setSize((int)(anchoFila * 1.25), altura * 15 + margen * 10);
        lTitulo.setLocation((interfazCV.getWidth()  - lTitulo.getWidth()) / 2, (int)(interfazCV.getHeight() * 0.1));
        
        lCVMenorSinCiudad1.setLocation((interfazCV.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 8 + margen * 3);
        ponerDerecha(lCVMenorSinCiudad1, tCVMenorSinCiudad1, margen);
        ponerDerecha(tCVMenorSinCiudad1, lCVMenorSinCiudad2, margen);
        ponerDerecha(lCVMenorSinCiudad2, tCVMenorSinCiudad2, margen);
        ponerDerecha(tCVMenorSinCiudad2, lCVMenorSinCiudad3, margen);
        ponerDerecha(lCVMenorSinCiudad3, tCVMenorSinCiudad3, margen);
        ponerDerecha(tCVMenorSinCiudad3, bCVMenorSinCiudad, margen);     
        
        anchoFila = lCVMenorCiudad1.getWidth() + lCVMenorCiudad2.getWidth() + tCVMenorCiudad1.getWidth() + tCVMenorCiudad2.getWidth() + bCVMenor.getWidth() + margen * 5;                    
        
        lCVMenorCiudad1.setLocation((interfazCV.getWidth() - anchoFila) / 2, lTitulo.getY() + altura * 2);
        ponerDerecha(lCVMenorCiudad1, tCVMenorCiudad1, margen);
        ponerDerecha(tCVMenorCiudad1, lCVMenorCiudad2, margen);
        ponerDerecha(lCVMenorCiudad2, tCVMenorCiudad2, margen);
        ponerDerecha(tCVMenorCiudad2, bCVMenor, margen);
        
        anchoFila = lCVMinimaCiudad1.getWidth() + tCVMinimaCiudad1.getWidth() + lCVMinimaCiudad2.getWidth() + tCVMinimaCiudad2.getWidth() + bCVMinima.getWidth() + margen * 5;
        
        lCVMinimaCiudad1.setLocation((interfazCV.getWidth() - anchoFila) / 2, lCVMenorCiudad1.getY() + altura * 2 + margen);
        ponerDerecha(lCVMinimaCiudad1, tCVMinimaCiudad1, margen);
        ponerDerecha(tCVMinimaCiudad1, lCVMinimaCiudad2, margen);     
        ponerDerecha(tCVMinimaCiudad1, lCVMinimaCiudad2, margen);     
        ponerDerecha(lCVMinimaCiudad2, tCVMinimaCiudad2, margen);     
        ponerDerecha(tCVMinimaCiudad2, bCVMinima, margen);     
        
        anchoFila = lCVTodosCiudad1.getWidth() + lCVTodosCiudad2.getWidth() + tCVTodosCiudad1.getWidth() + tCVTodosCiudad2.getWidth() + bCVTodos.getWidth() + margen * 5;
        
        lCVTodosCiudad1.setLocation((interfazCV.getWidth() - anchoFila) / 2, lCVMinimaCiudad1.getY() + altura * 2 + margen);
        ponerDerecha(lCVTodosCiudad1, tCVTodosCiudad1, margen);
        ponerDerecha(tCVTodosCiudad1, lCVTodosCiudad2, margen);
        ponerDerecha(lCVTodosCiudad2, tCVTodosCiudad2, margen);
        ponerDerecha(tCVTodosCiudad2, bCVTodos, margen);
        
        bSalir.setLocation((interfazCV.getWidth()  - bSalir.getWidth()) / 2, bCVMenorSinCiudad.getY() + bSalir.getHeight() + 25);

        interfazCV.add(lTitulo);
        interfazCV.add(lCVMenorCiudad1);
        interfazCV.add(lCVMenorCiudad2);
        interfazCV.add(tCVMinimaCiudad1);
        interfazCV.add(tCVMenorCiudad1);
        interfazCV.add(tCVMenorCiudad2);
        interfazCV.add(lCVMinimaCiudad1);
        interfazCV.add(lCVMinimaCiudad2);
        interfazCV.add(tCVMinimaCiudad2);
        interfazCV.add(lCVTodosCiudad1);
        interfazCV.add(lCVTodosCiudad2);
        interfazCV.add(lCVMenorSinCiudad1);        
        interfazCV.add(lCVMenorSinCiudad2);  
        interfazCV.add(lCVMenorSinCiudad3);      
        interfazCV.add(tCVTodosCiudad1);
        interfazCV.add(tCVTodosCiudad2);
        interfazCV.add(tCVTodosCiudad1);
        interfazCV.add(tCVMenorSinCiudad1);
        interfazCV.add(tCVMenorSinCiudad2);
        interfazCV.add(tCVMenorSinCiudad3);
        interfazCV.add(bCVMenor);
        interfazCV.add(bCVMinima);
        interfazCV.add(bCVMenorSinCiudad);
        interfazCV.add(bCVTodos);
        interfazCV.add(bSalir);        
        interfazCV.setVisible(true);
        bCVMenor.addActionListener((ActionEvent ae) -> {
            caminoMenorDistancia(tCVMenorCiudad1.getText(), tCVMenorCiudad2.getText());          
        });
        bCVMinima.addActionListener((ActionEvent ae) -> {
           caminoMenorCiudades(tCVMinimaCiudad1.getText(), tCVMinimaCiudad2.getText());
        });
        bCVMenorSinCiudad.addActionListener((ActionEvent ae) -> {
            caminoCiudadesEvitar(tCVMenorSinCiudad1.getText(), tCVMenorSinCiudad2.getText(), tCVMenorSinCiudad3.getText());
        });
        bCVTodos.addActionListener((ActionEvent ae) -> {
            caminosCiudades(tCVTodosCiudad1.getText(), tCVTodosCiudad2.getText());
        });        
        bSalir.addActionListener((ActionEvent ae) -> {
            interfazCV.dispose();
        });
    }
    
    private void caminoMenorDistancia(String ciudad1, String ciudad2){
        String error = "";
        try{
            if(ciudad1.equals("")) error = "Debe ingresar la ciudad de origen";
            if(ciudad2.equals("")) error = error + "Debe ingresar la ciudad destino";
            if(!error.equals("")) throw new InputException(error);            
            if(mapa.recuperarDato(ciudad1) == null) error = error + "No se encontro la ciudad de origen";            
            if(mapa.recuperarDato(ciudad2) == null) error = error + "No se encontro la ciudad destino";
            if(!error.equals("")) throw new InputException(error);
            Object[] caminoMasCorto = mapa.caminoMasCorto(ciudad1, ciudad2);
            if(caminoMasCorto[0] != null){
                logger.log(Level.INFO, "La ruta mas corta para llegar es \n{0}Distancia total: {1} kilometros", new Object[]{(caminoMasCorto[0]).toString(), caminoMasCorto[1]});
            }
            else{
                throw new InputException("No se encontro un camino entre las dos ciudades especificadas");
            }            
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    
    private void caminoMenorCiudades(String ciudad1, String ciudad2){
        String error = "";
        try{
            if(ciudad1.equals("")) error = "Debe ingresar la ciudad de origen";
            if(ciudad2.equals("")) error = error + "Debe ingresar la ciudad destino";
            if(!error.equals("")) throw new InputException(error);            
            if(mapa.recuperarDato(ciudad1) == null) error = error + "No se encontro la ciudad de origen";            
            if(mapa.recuperarDato(ciudad2) == null) error = error + "No se encontro la ciudad destino";
            if(!error.equals("")) throw new InputException(error);
            Object[] caminoMasCorto = mapa.caminoMasCortoCiudades(ciudad1, ciudad2);
            if(caminoMasCorto[0] != null){
                logger.log(Level.INFO, "La ruta mas corta para llegar es \n{0}En total, se tendria que pasar por {1} ciudades", new Object[]{(caminoMasCorto[0]).toString(), caminoMasCorto[1]});
            }
            else{
                throw new InputException("No se encontro un camino entre las dos ciudades especificadas");
            }            
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    
    private void caminosCiudades(String ciudad1, String ciudad2){
        String error = "";
        try{
            if(ciudad1.equals("")) error = "Debe ingresar la ciudad de origen";
            if(ciudad2.equals("")) error = error + "Debe ingresar la ciudad destino";
            if(!error.equals("")) throw new InputException(error);            
            if(mapa.recuperarDato(ciudad1) == null) error = error + "No se encontro la ciudad de origen";            
            if(mapa.recuperarDato(ciudad2) == null) error = error + "No se encontro la ciudad destino";            
            if(!error.equals("")) throw new InputException(error);
            Lista caminos = mapa.caminosCiudades(ciudad1, ciudad2);
            if(!caminos.esVacia()){
                logger.log(Level.INFO, "Las rutas para llegar son: \n{0}", caminos.toString());
            }
            else{
                throw new InputException("No se encontro un camino entre las dos ciudades especificadas");
            }            
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    
    private void caminoCiudadesEvitar(String ciudad1, String ciudad2, String ciudad3){
        String error = "";
        try{
            if(ciudad1.equals("")) error = "Debe ingresar la ciudad de origen";
            if(ciudad2.equals("")) error = error + "Debe ingresar la ciudad destino";
            if(ciudad3.equals("")) error = error + "Debe ingresar la ciudad a evitar";
            if(!error.equals("")) throw new InputException(error);            
            if(mapa.recuperarDato(ciudad1) == null) error = error + "No se encontro la ciudad de origen";            
            if(mapa.recuperarDato(ciudad2) == null) error = error + "No se encontro la ciudad destino";            
            if(mapa.recuperarDato(ciudad3) == null) error = error + "No se encontro la ciudad a evitar";            
            if(!error.equals("")) throw new InputException(error);
            Object[] caminoMasCorto = mapa.caminoCiudadesEvitar(ciudad1, ciudad2, ciudad3);
            if(caminoMasCorto[0] != null){
                logger.log(Level.INFO, "La ruta mas corta para llegar es: \n{0} por un total de {1} kilometros", new Object[]{caminoMasCorto[0].toString(), caminoMasCorto[1]});
            }
            else{
                throw new InputException("No se encontro un camino entre las dos ciudades que no pase por la ciudad especificada");
            }            
        }
        catch(InputException e){ 
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    
    private double convertDouble(String doubleVar) throws InputException{
        double auxDouble;
        try{    
            auxDouble = Double.parseDouble(doubleVar);            
        }
        catch(NumberFormatException e){
            throw new InputException(doubleVar + " es un valor invalido para ese campo");
        }
        return auxDouble;
    }
    
    private int convertInt(String intVar) throws InputException{
        int auxInt;
        try{
            auxInt = Integer.parseInt(intVar);
        }
        catch(NumberFormatException e){                    
            throw new InputException(intVar + " es un valor invalido para ese campo");
        }
        return auxInt;
    }    
    
    private boolean convertBoolean(String boolVar) throws InputException{
        boolean auxBool;    
        if(boolVar.equalsIgnoreCase("true") || boolVar.equalsIgnoreCase("verdadero") || boolVar.equalsIgnoreCase("si") || boolVar.equalsIgnoreCase("es sede")){
            auxBool = true;
        }
        else if(boolVar.equalsIgnoreCase("false") || boolVar.equalsIgnoreCase("falso") || boolVar.equalsIgnoreCase("no") || boolVar.equalsIgnoreCase("no es sede")){
            auxBool = false;
        }
        else{
            throw new InputException(boolVar + " es un valor invalido para ese campo (ejemplo de aceptado: si/no");
        }
        return auxBool;
    }
    
    private char convertChar(String charVal) throws InputException{
        char auxChar;
        if(charVal.length() > 1){
            throw new InputException(charVal + " es un valor invalido para ese campo, debe ser no mas largo que un caracter");
        }
        else{
            auxChar = charVal.charAt(0);
        }
        return auxChar;
    }
    
    private void tablaPosiciones(){
        if(!tablaActualizada){
            Lista listaEquipos = equipos.listarDatos();
            for(int i = 1; i <= listaEquipos.longitud(); i++){
                tabla.insertar(listaEquipos.recuperar(i), ((Seleccion)listaEquipos.recuperar(i)).getPuntos());
            }
            tablaActualizada = true;
        }
        logger.log(Level.INFO, "Tabla de posiciones:\n{0}", tabla.toString());
    }
    
    private void mostrarSistema(){
        logger.log(Level.INFO, "Mapa ciudades (Grafo Lista de Adyacencia):\n{0}\nEquipos (Diccionario AVL):\n{1}\nPartidos (Mapeo a muchos, Hash):\n{2}", new Object[]{ mapa.toString(), equipos.toString(), partidos.toString()});
    }    
}