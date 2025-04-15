/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.Controllers;

import java.sql.Date;
import java.util.List;
import servidor.DAO.PrestamoDAO;
import shared.Prestamo;


/**
 *
 * @author Usuario
 */
public class PrestamoController {
 private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    
     public boolean agregar(Prestamo prestamo){
        return prestamoDAO.agregar(prestamo);
                
    }
    
    public List<Prestamo> obtener(){
        return prestamoDAO.obtener();
    }
    
    public List<Prestamo> obtenerPorUsuarioID(int id ){
        return prestamoDAO.obtenerPorUsuarioID(id);

    }
    public int obtenerIDLibroPorIDPrestamo (int id){
        return prestamoDAO.obtenerIDLibroPorIDPrestamo(id);
        
    }
    
    public boolean finalizarPrestamo (int id, Date fechaDevolucion){
        return prestamoDAO.finalizarPrestamo(id, fechaDevolucion);
    }
    
     public Date obtenerFechaFinalizacionPorID(int idPrestamo) {
        return prestamoDAO.obtenerFechaFinalizacionPorID(idPrestamo);
    }

    public boolean tieneMultasActivas(int usuarioID) {
        return prestamoDAO.tieneMultasActivas(usuarioID);
    }

    public double obtenerMultaPorID(int idPrestamo) {
        return prestamoDAO.obtenerMultaPorID(idPrestamo);
    }

    public boolean pagarMultas(int usuarioID) {
        return prestamoDAO.pagarMultas(usuarioID);
    }

    public double obtenerMultaTotal(int usuarioID) {
        return prestamoDAO.obtenerMultaTotal(usuarioID);
    }
    
    
}
