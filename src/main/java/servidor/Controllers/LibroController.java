/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.Controllers;

import java.sql.Date;
import java.util.List;
import servidor.DAO.LibroDAO;
import shared.Libro;

/**
 *
 * @author Usuario
 */
public class LibroController {
    private final LibroDAO libroDAO = new LibroDAO();

    public boolean agregar(Libro libro) {
        return libroDAO.insertar(libro);
    }

    public boolean actualizar(Libro libro) {
        return libroDAO.actualizar(libro);
    }

    public boolean eliminar(int id) {
        return libroDAO.eliminar(id);
    }

    public List<Libro> obtener() {
        return libroDAO.obtener();
    }

    public String obtenerNombrePorID(int id) {
        return libroDAO.obtenerNombrePorID(id);
    }

    public boolean cambiarDisponibilidad(int id) {
        return libroDAO.cambiarDisponibilidad(id);
    }

    public boolean setDisponible(int id) {
        return libroDAO.setDisponible(id);
    }
    
    public List<Libro> buscarPorFiltroGeneral(String filtro){
        return libroDAO.buscarPorFiltroGeneral(filtro);
                
    }
    public Date obtenerFechaFinalizacionPorLibro(int id){
        return libroDAO.obtenerFechaFinalizacionPorLibro(id);
    }

    
     
}
